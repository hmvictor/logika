package org.logika.ui;

import java.awt.Frame;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import javax.swing.table.TableModel;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.logika.Argument;
import org.logika.ExpressionOperations;
import org.logika.TablaVerdadExpresion;
import org.logika.TablaVerdadArgumento;
import org.logika.equivalence.Asociation;
import org.logika.equivalence.Conmutation;
import org.logika.equivalence.DeMorgan;
import org.logika.equivalence.Distribution;
import org.logika.equivalence.DoubleNegation;
import org.logika.equivalence.Equivalence;
import org.logika.equivalence.Exportation;
import org.logika.equivalence.Implication;
import org.logika.equivalence.Tautology;
import org.logika.equivalence.Transposition;
import org.logika.exp.BinaryOperation;
import org.logika.exp.Expression;
import org.logika.exp.Sentence;
import org.logika.exp.UnaryOperation;
import org.logika.functions.Absorcion;
import org.logika.functions.Adicion;
import org.logika.functions.Conjuncion;
import org.logika.functions.DilemaConstructivo;
import org.logika.functions.ExpressionFunction;
import org.logika.functions.ModusPonens;
import org.logika.functions.ModusTollen;
import org.logika.functions.SilogismoDisyuntivo;
import org.logika.functions.SilogismoHipotetico;
import org.logika.functions.Simplificacion;
import org.logika.grammar.LogikaParser;
import org.logika.equivalence.EquivalenceFunction;

/**
 *
 * @author Víctor
 */
public class CommandInterpreter extends BaseListener {
    private Frame owner;
    private Argument argument;
    private List<Expression> demostrations;
    private Object result;
    
    private Map<String, ExpressionFunction> functionsByName=new HashMap<>();
    private Map<String, EquivalenceFunction> equivalencesByName=new HashMap<>();

    public CommandInterpreter(Frame owner, Argument argument, List<Expression> demostrations) {
        super(new Stack<>());
        this.owner=owner;
        this.argument=argument;
        this.demostrations=demostrations;
        functionsByName.put("M.P.", new ModusPonens());
        functionsByName.put("M.T.", new ModusTollen());
        functionsByName.put("S.H.", new SilogismoHipotetico());
        functionsByName.put("S.D.", new SilogismoDisyuntivo());
        functionsByName.put("Abs.", new Absorcion());
        functionsByName.put("D.C.", new DilemaConstructivo());
        functionsByName.put("Conj.", new Conjuncion());
        functionsByName.put("Simp.", new Simplificacion());
        functionsByName.put("Ad.", new Adicion());
        equivalencesByName.put("De.M.", new DeMorgan());
        equivalencesByName.put("Conm.", new Conmutation());
        equivalencesByName.put("Asoc.", new Asociation());
        equivalencesByName.put("Dist.", new Distribution());
        equivalencesByName.put("D.N.", new DoubleNegation());
        equivalencesByName.put("Trans.", new Transposition());
        equivalencesByName.put("Impl.", new Implication());
        equivalencesByName.put("Equiv.", new Equivalence());
        equivalencesByName.put("Exp.", new Exportation());
        equivalencesByName.put("Taut.", new Tautology());
    }

    public Object getResult() {
        return result;
    }

    @Override
    public void exitPrint_command(LogikaParser.Print_commandContext ctx) {
        if(ctx.getChild(1).getText().equals("argument")) {
            StringWriter stringWriter = new StringWriter();
            argument.print(new PrintWriter(stringWriter));
            new TextDialog(null, stringWriter.toString()).setVisible(true);
        }else{
            Expression exp=(Expression) getStack().pop();
            new TextDialog(null, exp.toUserString(argument.getDescriptionByAlias())).setVisible(true);
        }
    }

    @Override
    public void exitThruth_table_command(LogikaParser.Thruth_table_commandContext ctx) {
        if(ctx.getChild(1).getText().equals("argument")) {
            TablaVerdadArgumento tablaVerdad = argument.createTablaVerdad();
            TableModel tableModel=new ThruthTableModel(tablaVerdad);
            new ThruthTableDialog(owner, tableModel).setVisible(true);
        }else{
            Expression exp=(Expression) getStack().pop();
            TablaVerdadExpresion tablaVerdad = TablaVerdadExpresion.build(exp);
            TableModel tableModel=new ThruthTableModel(tablaVerdad);
            new ThruthTableDialog(owner, tableModel).setVisible(true);
        }
        
    }
    
    @Override
    public void exitPath_command(LogikaParser.Path_commandContext ctx) {
        List<ExpressionOperations.ExpressionOperation> operations=(List<ExpressionOperations.ExpressionOperation>) getStack().pop();
        Expression expression=(Expression) getStack().pop();
        result=ExpressionOperations.get(expression, operations);
    }

    @Override
    public void exitPath_expr(LogikaParser.Path_exprContext ctx) {
        List<TerminalNode> paths = ctx.PATH();
        List<ExpressionOperations.ExpressionOperation> operations=new LinkedList<>();
        for (TerminalNode path : paths) {
            if(path.getText().equals("left")) {
                operations.add(ExpressionOperations.ExpressionOperation.LEFT_OPERAND);
            }else if(path.getText().equals("right")) {
                operations.add(ExpressionOperations.ExpressionOperation.RIGHT_OPERAND);
            }else if(path.getText().equals("operand")) {
                operations.add(ExpressionOperations.ExpressionOperation.OPERAND);
            }
        }
        getStack().push(operations);
    }

    @Override
    public void exitInteger(LogikaParser.IntegerContext ctx) {
        getStack().push(Integer.parseInt(ctx.getText()));
    }

    @Override
    public void exitProposition_expr(LogikaParser.Proposition_exprContext ctx) {
        Object value = getStack().pop();
        if(value instanceof Integer) {
            int index=(int) value;
            if((index-1) < argument.getPremisesCount()) {
                getStack().push(argument.getPremise(index-1));
            }else {
                getStack().push(demostrations.get((index-1)-argument.getPremisesCount()));
            }
        }else if(value instanceof Expression) {
            getStack().push(value);
        }
    }
    
    @Override
    public void exitFunction_command(LogikaParser.Function_commandContext ctx) {
        List<Expression> expressions=new LinkedList<>();
        for (LogikaParser.Proposition_exprContext exprContext : ctx.proposition_expr()) {
            expressions.add(0, (Expression) getStack().pop());
        }
        String functionName=ctx.getChild(0).getText();
        if(!functionsByName.containsKey(functionName)) {
            throw new IllegalArgumentException("Undefined function: "+functionName);
        }
        result=functionsByName.get(functionName).apply(expressions.toArray(new Expression[0]));
    }

    @Override
    public void exitEquiv_command(LogikaParser.Equiv_commandContext ctx) {
        Object lastParam=getStack().pop();
        List<ExpressionOperations.ExpressionOperation> operations=null;
        Expression expression;
        if(lastParam instanceof List) {
            operations=(List<ExpressionOperations.ExpressionOperation>) lastParam;
            expression = (Expression) getStack().pop();
        }else{
            operations=new LinkedList<>();
            expression = (Expression) lastParam;
        }
        LogikaParser.VariationContext variationCtx = ctx.variation();
        String equivFunctionName=ctx.getChild(0).getText();
        result=copiar(equivFunctionName, variationCtx == null ? -1: Integer.parseInt(variationCtx.getChild(1).getText()), expression, new LinkedList<>(), operations);
    }
    
    
    private Expression copiar(String name, int variation, Expression expression, List<ExpressionOperations.ExpressionOperation> path, List<ExpressionOperations.ExpressionOperation> operations) {
        boolean match=true;
        match=path.size() == operations.size();
        if(match) {
            for (int i = 0; i < path.size(); i++) {
                if(!(path.get(i).equals(operations.get(i)))) {
                    match=false;
                    break;
                }
            }
        }
        if(match) {
            return applyEquivFunction(name, variation, expression);
        }
        if(expression instanceof Sentence) {
            return new Sentence(((Sentence)expression).getAlias());
        }else if(expression instanceof UnaryOperation) {
            UnaryOperation unaryOperation=(UnaryOperation) expression;
            List<ExpressionOperations.ExpressionOperation> linkedList = new LinkedList<>(path);
            linkedList.add(ExpressionOperations.ExpressionOperation.OPERAND);
            return new UnaryOperation(unaryOperation.getOperator(), copiar(name, variation, unaryOperation.getExpression(), linkedList, operations));
        }else if(expression instanceof BinaryOperation) {
            BinaryOperation binaryOperation=(BinaryOperation) expression;
            List<ExpressionOperations.ExpressionOperation> leftLinkedList = new LinkedList<>(path);
            leftLinkedList.add(ExpressionOperations.ExpressionOperation.LEFT_OPERAND);
            List<ExpressionOperations.ExpressionOperation> rightLinkedList = new LinkedList<>(path);
            rightLinkedList.add(ExpressionOperations.ExpressionOperation.RIGHT_OPERAND);
            return new BinaryOperation(binaryOperation.getOperator(), copiar(name, variation, binaryOperation.getLeft(), leftLinkedList, operations), copiar(name, variation, binaryOperation.getRight(), rightLinkedList, operations));
        } else {
            throw new IllegalArgumentException();
        }
    }
    
    private Expression applyEquivFunction(String functionName, int variation, Expression expression) {
        if(!equivalencesByName.containsKey(functionName)) {
            throw new IllegalArgumentException("Unkown equivalence: "+functionName);
        }
        return equivalencesByName.get(functionName).apply(variation, expression);
    }
    
}
