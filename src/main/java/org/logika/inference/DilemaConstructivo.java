package org.logika.inference;

import static org.logika.exp.BinaryOperator.CONJUNCTION;
import static org.logika.exp.BinaryOperator.DISYUNCTION;
import static org.logika.exp.BinaryOperator.MATERIAL_IMPLICATION;
import org.logika.exp.Expression;
import org.logika.inference.Transformation.Matching;
import org.logika.inference.Transformation.TransformationContext;

/**
 *
 * @author Víctor
 */
public class DilemaConstructivo implements InferenceRule {

    @Override
    public Expression apply(Expression... expressions) {
        return Transformation.given(
            new Matching(0, CONJUNCTION.pattern(
                MATERIAL_IMPLICATION.pattern("a", "b"),
                MATERIAL_IMPLICATION.pattern("c", "d")
            )),
            new Matching(1, DISYUNCTION.pattern("a", "c"))
        ).then((TransformationContext context) -> 
            DISYUNCTION.of(context.getExpressionMap().get("b"), context.getExpressionMap().get("d"))
        ).apply(expressions);
    }
    
}
