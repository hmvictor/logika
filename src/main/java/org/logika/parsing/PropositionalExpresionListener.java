package org.logika.parsing;

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
class PropositionalExpresionListener extends LogikaBaseListener {
    private Stack<Object> stack = new Stack<>();

    @Override
    public void exitSentenceExpression(LogikaParser.SentenceExpressionContext ctx) {
        stack.push(new Sentence(ctx.getText().charAt(0)));
    }

    @Override
    public void exitUnaryOperation(LogikaParser.UnaryOperationContext ctx) {
        stack.push(new UnaryOperation(UnaryOperator.of(ctx.getChild(0).getText()), (Expression) stack.pop()));
    }

    @Override
    public void exitBinaryOperation(LogikaParser.BinaryOperationContext ctx) {
        Expression right = (Expression) stack.pop();
        BinaryOperator operator = BinaryOperator.of(ctx.getChild(1).getText());
        Expression left = (Expression) stack.pop();
        stack.push(new BinaryOperation(operator, left, right));
    }

    protected Expression getExpression() {
        return (Expression) stack.pop();
    }

    public Stack<Object> getStack() {
        return stack;
    }
    
}
