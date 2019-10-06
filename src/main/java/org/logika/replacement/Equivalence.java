package org.logika.replacement;

import static org.logika.exp.BinaryOperator.CONJUNCTION;
import static org.logika.exp.BinaryOperator.DISYUNCTION;
import static org.logika.exp.BinaryOperator.MATERIAL_EQUALITY;
import static org.logika.exp.BinaryOperator.MATERIAL_IMPLICATION;
import org.logika.exp.Expression;
import static org.logika.exp.UnaryOperator.NEGATION;

/**
 *
 * @author Víctor
 */
public class Equivalence implements ReplacementRule {

    @Override
    public Expression apply(int discriminator, Expression expression) {
        Pairs pairs=new Pairs();
        pairs.add(
            MATERIAL_EQUALITY.pattern("a", "b"), 
            DISYUNCTION.pattern(
                CONJUNCTION.pattern("a", "b"), 
                CONJUNCTION.pattern(
                    NEGATION.pattern("a"), NEGATION.pattern("b")
                )
            )
        ).add(
            MATERIAL_EQUALITY.pattern("a", "b"),
            CONJUNCTION.pattern(
                MATERIAL_IMPLICATION.pattern("a", "b"),
                MATERIAL_IMPLICATION.pattern("b", "a")
            )
        );
        return pairs.matchPairs(discriminator, expression);
    }
    
}
