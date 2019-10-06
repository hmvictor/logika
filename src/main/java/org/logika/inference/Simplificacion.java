package org.logika.inference;

import static org.logika.exp.BinaryOperator.CONJUNCTION;
import org.logika.exp.Expression;
import org.logika.inference.Transformation.Matching;
import org.logika.inference.Transformation.TransformationContext;

/**
 *
 * @author Víctor
 */
public class Simplificacion implements InferenceRule {

    @Override
    public Expression apply(Expression... expressions) {
        return Transformation.given(
            new Matching(0, CONJUNCTION.pattern("a", "b"))
        ).then((TransformationContext context) -> 
            context.getExpressionMap().get("a")
        ).apply(expressions);
    }
    
}
