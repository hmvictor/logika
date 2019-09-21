package org.logika.equivalence;

import static org.logika.exp.BinaryOperator.CONJUNCTION;
import static org.logika.exp.BinaryOperator.DISYUNCTION;
import org.logika.exp.Expression;
import static org.logika.functions.VarPattern.var;

/**
 *
 * @author Víctor
 */
public class Tautology implements EquivalenceFunction {

    @Override
    public Expression apply(int variation, Expression expression) {
        Pairs pairs=new Pairs();
        pairs.add(
            DISYUNCTION.pattern("p", "p"),
            var("p")
        ).add(
            CONJUNCTION.pattern("p", "p"),
            var("p")
        );
        return pairs.matchPairs(variation, expression);
    }
    
}
