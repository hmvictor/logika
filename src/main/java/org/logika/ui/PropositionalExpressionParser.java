package org.logika.ui;

import java.util.Stack;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.logika.exp.BinaryOperation;
import org.logika.exp.BinaryOperator;
import org.logika.exp.Expression;
import org.logika.exp.Sentence;
import org.logika.exp.UnaryOperation;
import org.logika.exp.UnaryOperator;
import org.logika.grammar.LogikaBaseListener;
import org.logika.grammar.LogikaLexer;
import org.logika.grammar.LogikaParser;

/**
 *
 * @author Víctor
 */
public class PropositionalExpressionParser {
    
    public Expression parse(String text) {
        LogikaLexer lexer = new LogikaLexer(CharStreams.fromString(text));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        LogikaParser parser = new LogikaParser(tokens);
        ParseTreeWalker parseTreeWalker = new ParseTreeWalker();
        PropositionalExpresionListener listener=new PropositionalExpresionListener();
        parseTreeWalker.walk(listener, parser.propositionalExpression());
        return listener.getExpression();
    }
    
    public static class PropositionalExpresionListener extends LogikaBaseListener {
        private Stack<Object> stack=new Stack<>();
        private Expression expression;

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

//        @Override
//        public void exitPropositionalExpression(LogikaParser.PropositionalExpressionContext ctx) {
//            expression=(Expression) stack.pop();
//        }

        protected Expression getExpression() {
            return (Expression) stack.pop();
        }

        public Stack<Object> getStack() {
            return stack;
        }
        
    }

}
