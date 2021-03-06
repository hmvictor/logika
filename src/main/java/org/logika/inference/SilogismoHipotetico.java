package org.logika.inference;

import static org.logika.exp.BinaryOperator.MATERIAL_IMPLICATION;
import org.logika.exp.Expression;
import org.logika.exp.Sentence;
import org.logika.inference.Transformation.Matching;

/**
 *
 * @author Víctor
 */
public class SilogismoHipotetico implements InferenceRule {

    @Override
    public Expression apply(Expression... expressions) {
        return Transformation.given(
            new Matching(0, MATERIAL_IMPLICATION.pattern("a", "b")),
            new Matching(1, MATERIAL_IMPLICATION.pattern("b", "c"))
        ).then((Transformation.TransformationContext context) -> {
            return MATERIAL_IMPLICATION.of(context.getExpressionMap().get("a"), context.getExpressionMap().get("c"));
        }).apply(expressions);
    }
    
    public static void main(String[] args) {
        Expression exp = new SilogismoHipotetico().apply(
                MATERIAL_IMPLICATION.of(new Sentence('A'), new Sentence('B')),
                MATERIAL_IMPLICATION.of(new Sentence('B'), new Sentence('C')));
        System.out.println(exp);
    }

}
