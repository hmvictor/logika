package org.logika.inference;

import static org.logika.exp.BinaryOperator.DISYUNCTION;
import org.logika.exp.Expression;

/**
 *
 * @author Víctor
 */
public class Adicion implements InferenceRule {

    @Override
    public Expression apply(Expression... expressions) {
        return DISYUNCTION.of(expressions[0], expressions[1]);
    }
    
}
