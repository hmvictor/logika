package org.logika.inference;

import static org.logika.exp.BinaryOperator.CONJUNCTION;
import org.logika.exp.Expression;

/**
 *
 * @author Víctor
 */
public class Conjuncion implements InferenceRule {

    @Override
    public Expression apply(Expression... expressions) {
        return CONJUNCTION.of(expressions[0], expressions[1]);
    }
    
}
