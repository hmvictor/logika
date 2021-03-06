package org.logika.inference;

import static org.logika.exp.BinaryOperator.CONJUNCTION;
import static org.logika.exp.BinaryOperator.MATERIAL_IMPLICATION;
import org.logika.exp.Expression;
import org.logika.inference.Transformation.Matching;
import org.logika.inference.Transformation.TransformationContext;

/**
 *
 * @author Víctor
 */
public class Absorcion implements InferenceRule {

    @Override
    public Expression apply(Expression... expressions) {
        return Transformation.given(
            new Matching(0, MATERIAL_IMPLICATION.pattern("a", "b"))
        ).then((TransformationContext context) -> 
            MATERIAL_IMPLICATION.of(context.getExpressionMap().get("a"),
                CONJUNCTION.of(context.getExpressionMap().get("a"), context.getExpressionMap().get("b"))
            )
        ).apply(expressions);
    }
    
}
