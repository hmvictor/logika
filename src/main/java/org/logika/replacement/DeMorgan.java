package org.logika.replacement;

import static org.logika.exp.BinaryOperator.CONJUNCTION;
import static org.logika.exp.BinaryOperator.DISYUNCTION;
import org.logika.exp.Expression;
import static org.logika.exp.UnaryOperator.NEGATION;

/**
 *
 * @author Víctor
 */
public class DeMorgan implements ReplacementRule{

    @Override
    public Expression apply(int discriminator, Expression expression) {
        Pairs pairs=new Pairs();
        pairs.add(
            NEGATION.pattern(CONJUNCTION.pattern("a", "b")),
            DISYUNCTION.pattern(NEGATION.pattern("a"), NEGATION.pattern("b"))
        ).add(
            NEGATION.pattern(DISYUNCTION.pattern("a", "b")),
            CONJUNCTION.pattern(NEGATION.pattern("a"), NEGATION.pattern("b"))
        );
        return pairs.matchPairs(discriminator, expression);
    }
    
}
