package org.logika.ui;

import java.util.Stack;
import org.logika.exp.BinaryOperation;
import org.logika.exp.BinaryOperator;
import org.logika.exp.Expression;
import org.logika.exp.Sentence;
import org.logika.exp.UnaryOperation;
import org.logika.exp.UnaryOperator;
import org.logika.grammar.LogikaBaseListener;
import org.logika.grammar.LogikaParser;

/**
 *
 * @author Víctor
 */
public class BaseListener extends LogikaBaseListener {
    private Stack<Object> stack;

    public BaseListener(Stack<Object> stack) {
        this.stack = stack;
    }

    public Stack<Object> getStack() {
        return stack;
    }
    
    @Override
    public void exitSentence_expression(LogikaParser.Sentence_expressionContext ctx) {
        stack.push(new Sentence(ctx.getText().charAt(0)));
        System.out.println("exit sentence " + ctx.getText());
    }

    @Override
    public void exitUnary_operation(LogikaParser.Unary_operationContext ctx) {
        stack.push(new UnaryOperation(UnaryOperator.valueOfSymbol(ctx.getChild(0).getText()), (Expression) stack.pop()));
        System.out.println("unary operation " + ctx.getText());
    }

    @Override
    public void exitBinary_operation(LogikaParser.Binary_operationContext ctx) {
        Expression right = (Expression) stack.pop();
        BinaryOperator operator = BinaryOperator.valueOfSymbol(ctx.getChild(2).getText());
        Expression left = (Expression) stack.pop();
        stack.push(new BinaryOperation(operator, left, right));
        System.out.println("binary operation " + ctx.getText());
    }

}
