package org.logika.replacement;

import static org.logika.exp.BinaryOperator.CONJUNCTION;
import static org.logika.exp.BinaryOperator.DISYUNCTION;
import org.logika.exp.Expression;

/**
 *
 * @author Víctor
 */
public class Conmutation implements ReplacementRule{

    @Override
    public Expression apply(int discriminator, Expression expression) {
        Pairs pairs=new Pairs();
        pairs.add(
            DISYUNCTION.pattern("a", "b"),
            DISYUNCTION.pattern("b", "a")
        ).add(
            CONJUNCTION.pattern("a", "b"),
            CONJUNCTION.pattern("b", "a")
        );
        return pairs.matchPairs(discriminator, expression);
    }
    
}
