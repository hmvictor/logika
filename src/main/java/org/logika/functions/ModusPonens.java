package org.logika.functions;

import static org.logika.exp.BinaryOperator.MATERIAL_IMPLICATION;
import org.logika.exp.Expression;
import org.logika.functions.Transformation.Matching;
import org.logika.functions.Transformation.TransformationContext;
import static org.logika.functions.VarPattern.var;

/**
 *
 * @author Víctor
 */
public class ModusPonens implements ExpressionFunction {

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
