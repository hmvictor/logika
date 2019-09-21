package org.logika.equivalence;

import static org.logika.exp.BinaryOperator.CONJUNCTION;
import static org.logika.exp.BinaryOperator.DISYUNCTION;
import org.logika.exp.Expression;

/**
 *
 * @author Víctor
 */
public class Conmutation implements EquivalenceFunction{

    @Override
    public Expression apply(int variation, Expression expression) {
        Pairs pairs=new Pairs();
        pairs.add(
            DISYUNCTION.pattern("a", "b"),
            DISYUNCTION.pattern("b", "a")
        ).add(
            CONJUNCTION.pattern("a", "b"),
            CONJUNCTION.pattern("b", "a")
        );
        return pairs.matchPairs(variation, expression);
    }
    
}
