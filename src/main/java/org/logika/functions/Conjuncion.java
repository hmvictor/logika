package org.logika.functions;

import static org.logika.exp.BinaryOperator.CONJUNCTION;
import org.logika.exp.Expression;

/**
 *
 * @author Víctor
 */
public class Conjuncion implements ExpressionFunction {

    @Override
    public Expression apply(Expression... expressions) {
        return CONJUNCTION.of(expressions[0], expressions[1]);
    }
    
}
