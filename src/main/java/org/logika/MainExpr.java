package org.logika;

import org.logika.exp.Sentence;
import org.logika.exp.Expression;
import static org.logika.ExpressionOperations.ExpressionOperation.*;
import static org.logika.exp.BinaryOperator.CONJUNCTION;
import static org.logika.exp.BinaryOperator.DISYUNCTION;
import org.logika.exp.Operator;
import static org.logika.exp.UnaryOperator.NEGATION;

/**
 *
 * @author Víctor
 */
public class MainExpr {
    
    public static void main(String[] args) {
        Expression expr=CONJUNCTION.expression(new Sentence('A'), NEGATION.expression(DISYUNCTION.expression(new Sentence('B'), new Sentence('C'))));
        
        System.out.println(((Operator)OPERATOR.apply(expr)).getSymbol());
        System.out.println(LEFT_OPERAND.apply(expr));
        System.out.println(RIGHT_OPERAND.apply(expr));

        System.out.println(ExpressionOperations.get(expr, RIGHT_OPERAND, OPERAND, LEFT_OPERAND));
    }
    
}
