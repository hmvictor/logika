package org.logika.equivalence;

import static org.logika.exp.BinaryOperator.CONJUNCTION;
import static org.logika.exp.BinaryOperator.DISYUNCTION;
import org.logika.exp.Expression;
import static org.logika.functions.VarPattern.var;

/**
 *
 * @author Víctor
 */
public class Asociation implements EquivalenceFunction {

    @Override
    public Expression apply(int variation, Expression expression) {
        Pairs pairs=
            new Pairs()
                .add(
                    DISYUNCTION.pattern(var("p"), DISYUNCTION.pattern("q", "r")),
                    DISYUNCTION.pattern(DISYUNCTION.pattern("p", "q"), var("r"))
                ).add(
                    CONJUNCTION.pattern(var("p"), CONJUNCTION.pattern("q", "r")),
                    CONJUNCTION.pattern(CONJUNCTION.pattern("p", "q"), var("r"))
                );
        return pairs.matchPairs(variation, expression);
    }
    
}
