package org.logika.inference;

import static org.logika.exp.BinaryOperator.MATERIAL_IMPLICATION;
import org.logika.exp.Expression;
import org.logika.inference.Transformation.Matching;
import org.logika.inference.Transformation.TransformationContext;
import static org.logika.inference.VarPattern.var;

/**
 *
 * @author Víctor
 */
public class ModusPonens implements InferenceRule {

    @Override
    public Expression apply(Expression... expressions) {
        return Transformation.given(
            new Matching(0, MATERIAL_IMPLICATION.pattern("a", "b")),
            new Matching(1, var("a"))
        ).then((TransformationContext context) -> 
            context.getExpressionMap().get("b")
        ).apply(expressions);
    }
    
}
