package org.logika.functions;

import static org.logika.exp.BinaryOperator.DISYUNCTION;
import org.logika.exp.Expression;

/**
 *
 * @author Víctor
 */
public class Adicion implements ExpressionFunction {

    @Override
    public Expression apply(Expression... expressions) {
        return DISYUNCTION.expression(expressions[0], expressions[1]);
    }
    
}
