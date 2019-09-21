package org.logika.functions;

import static org.logika.exp.BinaryOperator.DISYUNCTION;
import org.logika.exp.Expression;
import static org.logika.exp.UnaryOperator.NEGATION;
import org.logika.functions.Transformation.Matching;
import org.logika.functions.Transformation.TransformationContext;

/**
 *
 * @author Víctor
 */
public class SilogismoDisyuntivo implements ExpressionFunction {

    @Override
    public Expression apply(Expression... expressions) {
        return Transformation.given(
            new Matching(0, DISYUNCTION.pattern("a", "b")),
            new Matching(1, NEGATION.pattern("a"))
        ).then((TransformationContext context) -> 
            context.getExpressionMap().get("b")
        ).apply(expressions);
    }

}
