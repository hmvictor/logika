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
import org.logika.grammar.PropositionsBaseListener;
import org.logika.grammar.PropositionsLexer;
import org.logika.grammar.PropositionsParser;

/**
 *
 * @author Víctor
 */
public class PropositionalExpressionParser {
    
    public Expression parse(String text) {
        PropositionsLexer lexer = new PropositionsLexer(CharStreams.fromString(text));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        PropositionsParser parser = new PropositionsParser(tokens);
        ParseTreeWalker parseTreeWalker = new ParseTreeWalker();
        PropositionalExpresionListener listener=new PropositionalExpresionListener();
        parseTreeWalker.walk(listener, parser.propositionalExpression());
        return listener.getExpression();
    }
    
    private static class PropositionalExpresionListener extends PropositionsBaseListener {
        private Stack<Object> stack=new Stack<>();
        private Expression expression;

        @Override
        public void exitSentenceExpression(PropositionsParser.SentenceExpressionContext ctx) {
            stack.push(new Sentence(ctx.getText().charAt(0)));
        }

        @Override
        public void exitUnaryOperation(PropositionsParser.UnaryOperationContext ctx) {
            stack.push(new UnaryOperation(UnaryOperator.of(ctx.getChild(0).getText()), (Expression) stack.pop()));
        }

        @Override
        public void exitBinaryOperation(PropositionsParser.BinaryOperationContext ctx) {
            Expression right = (Expression) stack.pop();
            BinaryOperator operator;
//            if(ctx.getChildCount() == 3) {
                 operator= BinaryOperator.of(ctx.getChild(1).getText());
//            }else if(ctx.getChild(1).getText().equals("(")){
//                operator= BinaryOperator.of(ctx.getChild(3).getText());
//            }else{
//                operator= BinaryOperator.of(ctx.getChild(1).getText());
//            }
            Expression left = (Expression) stack.pop();
            stack.push(new BinaryOperation(operator, left, right));
        }

        @Override
        public void exitPropositionalExpression(PropositionsParser.PropositionalExpressionContext ctx) {
            expression=(Expression) stack.pop();
        }

        private Expression getExpression() {
            return expression;
        }
        
    }

}
