package org.logika.replacement;

import static org.logika.exp.BinaryOperator.MATERIAL_IMPLICATION;
import org.logika.exp.Expression;
import static org.logika.exp.UnaryOperator.NEGATION;

/**
 *
 * @author Víctor
 */
public class Transposition implements ReplacementRule {

    @Override
    public Expression apply(int discriminator, Expression expression) {
        Pairs pairs=new Pairs();
        pairs.add(
            MATERIAL_IMPLICATION.pattern("p", "q"),
            MATERIAL_IMPLICATION.pattern(NEGATION.pattern("q"), NEGATION.pattern("p"))
        );
        return pairs.matchPairs(discriminator, expression);
    }
    
}
