package org.logika.functions;

import static org.logika.exp.BinaryOperator.MATERIAL_IMPLICATION;
import org.logika.exp.Expression;
import static org.logika.exp.UnaryOperator.NEGATION;
import org.logika.functions.Transformation.Matching;

/**
 *
 * @author Víctor
 */
public class ModusTollen implements ExpressionFunction {

    @Override
    public Expression apply(Expression... expressions) {
        return Transformation.given(
            new Matching(0, MATERIAL_IMPLICATION.pattern("a", "b")),
            new Matching(1, NEGATION.pattern("b"))
        ).then((Transformation.TransformationContext context) -> 
            NEGATION.of(context.getExpressionMap().get("a"))
        ).apply(expressions);
    }
    
}
