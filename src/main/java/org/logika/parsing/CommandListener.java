package org.logika.parsing;

import java.awt.Frame;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.logika.Argument;
import org.logika.ExpressionOperations;
import org.logika.replacement.Asociation;
import org.logika.replacement.Conmutation;
import org.logika.replacement.DeMorgan;
import org.logika.replacement.Distribution;
import org.logika.replacement.DoubleNegation;
import org.logika.replacement.Equivalence;
import org.logika.replacement.Exportation;
import org.logika.replacement.Implication;
import org.logika.replacement.Tautology;
import org.logika.replacement.Transposition;
import org.logika.exp.BinaryOperation;
import org.logika.exp.Expression;
import org.logika.exp.Sentence;
import org.logika.exp.UnaryOperation;
import org.logika.inference.Absorcion;
import org.logika.inference.Adicion;
import org.logika.inference.Conjuncion;
import org.logika.inference.DilemaConstructivo;
import org.logika.inference.ModusPonens;
import org.logika.inference.ModusTollen;
import org.logika.inference.SilogismoDisyuntivo;
import org.logika.inference.SilogismoHipotetico;
import org.logika.inference.Simplificacion;
import org.logika.grammar.LogikaParser;
import org.logika.replacement.ReplacementRule;
import org.logika.inference.InferenceRule;

/**
 *
 * @author Víctor
 */
class CommandListener extends PropositionalExpresionListener {
    private Frame owner;
    private Argument argument;
    private List<Expression> demostrations;
    private Object result;
    
    private Map<String, InferenceRule> functionsByName=new HashMap<>();
    private Map<String, ReplacementRule> equivalencesByName=new HashMap<>();

    public CommandListener(Frame owner, Argument argument, List<Expression> demostrations) {
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
    
//    @Override
//    public void exitPrint_command(LogikaParser.Print_commandContext ctx) {
//        if(ctx.getChild(1).getText().equals("argument")) {
//            StringWriter stringWriter = new StringWriter();
//            argument.print(new PrintWriter(stringWriter));
//            new TextDialog(null, stringWriter.toString()).setVisible(true);
//        }else{
//            Expression exp=(Expression) getStack().pop();
//            new TextDialog(null, exp.toUserString(argument.getDescriptionByAlias())).setVisible(true);
//        }
//    }
//
//    @Override
//    public void exitThruth_table_command(LogikaParser.Thruth_table_commandContext ctx) {
//        if(ctx.getChild(1).getText().equals("argument")) {
//            TablaVerdadArgumento tablaVerdad = argument.createTablaVerdad();
//            TableModel tableModel=new ThruthTableModel(tablaVerdad);
//            new ThruthTableDialog(owner, tableModel).setVisible(true);
//        }else{
//            Expression exp=(Expression) getStack().pop();
//            TablaVerdadExpresion tablaVerdad = TablaVerdadExpresion.build(exp);
//            TableModel tableModel=new ThruthTableModel(tablaVerdad);
//            new ThruthTableDialog(owner, tableModel).setVisible(true);
//        }
//        
//    }
    
    @Override
    public void exitExpressionPath(LogikaParser.ExpressionPathContext ctx) {
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
    public void exitPropExpOperand(LogikaParser.PropExpOperandContext ctx) {
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
    public void exitDeductionCommand(LogikaParser.DeductionCommandContext ctx) {
        List<Expression> expressions=new LinkedList<>();
        for (LogikaParser.PropExpOperandContext exprContext : ctx.propExpOperand()) {
            expressions.add(0, getExpression());
        }
        String functionName=ctx.getChild(ctx.getChildCount()-1).getText();
        if(!functionsByName.containsKey(functionName)) {
            throw new IllegalArgumentException("Undefined function: "+functionName);
        }
        result=functionsByName.get(functionName).apply(expressions.toArray(new Expression[0]));
    }

    @Override
    public void exitEquivalenceCommand(LogikaParser.EquivalenceCommandContext ctx) {
        Object lastParam=getStack().pop();
        List<ExpressionOperations.ExpressionOperation> operations=null;
        Expression expression;
        if(lastParam instanceof List) {
            operations=(List<ExpressionOperations.ExpressionOperation>) lastParam;
            expression = getExpression();
        }else{
            operations=new LinkedList<>();
            expression = (Expression) lastParam;
        }
        LogikaParser.DiscriminatorContext discriminatorCtx = ctx.discriminator();
        String equivFunctionName=ctx.getChild(discriminatorCtx == null ? ctx.getChildCount()-1: ctx.getChildCount()-2).getText();
        result=copiar(equivFunctionName, discriminatorCtx == null || discriminatorCtx.getChildCount() == 0 ? -1: Integer.parseInt(discriminatorCtx.getChild(1).getText()), expression, new LinkedList<>(), operations);
    }

    @Override
    public void exitPathCommand(LogikaParser.PathCommandContext ctx) {
        List<ExpressionOperations.ExpressionOperation> operations=(List<ExpressionOperations.ExpressionOperation>) getStack().pop();
        result=ExpressionOperations.get((Expression) getStack().pop(), operations);
    }

    @Override
    public void exitExpressionCommand(LogikaParser.ExpressionCommandContext ctx) {
        result=getStack().pop();
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
