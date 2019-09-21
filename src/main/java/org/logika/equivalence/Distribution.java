package org.logika.equivalence;

import static org.logika.exp.BinaryOperator.CONJUNCTION;
import static org.logika.exp.BinaryOperator.DISYUNCTION;
import org.logika.exp.Expression;
import static org.logika.functions.VarPattern.var;

/**
 *
 * @author Víctor
 */
public class Distribution implements EquivalenceFunction {

    @Override
    public Expression apply(int variation, Expression expression) {
        Pairs pairs=new Pairs();
        pairs.add(
            CONJUNCTION.pattern(var("p"), DISYUNCTION.pattern("q", "r")),
            DISYUNCTION.pattern(CONJUNCTION.pattern("p", "q"), CONJUNCTION.pattern("p", "r"))
        ).add(
            DISYUNCTION.pattern(var("p"), CONJUNCTION.pattern("q", "r")),
            CONJUNCTION.pattern(DISYUNCTION.pattern("p", "q"), DISYUNCTION.pattern("p", "r"))
        );
        return pairs.matchPairs(variation, expression);
    }
    
}
