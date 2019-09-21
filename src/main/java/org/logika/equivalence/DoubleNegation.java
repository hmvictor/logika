package org.logika.equivalence;

import org.logika.exp.Expression;
import static org.logika.exp.UnaryOperator.NEGATION;
import static org.logika.functions.VarPattern.var;

/**
 *
 * @author Víctor
 */
public class DoubleNegation implements EquivalenceFunction{

    @Override
    public Expression apply(int variation, Expression expression) {
        Pairs pairs=new Pairs();
        pairs.add(
            NEGATION.pattern(NEGATION.pattern("p")),
            var("p")
        );
        return pairs.matchPairs(variation, expression);
    }
    
}
