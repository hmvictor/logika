package org.logika.replacement;

import org.logika.exp.Expression;
import static org.logika.exp.UnaryOperator.NEGATION;
import static org.logika.inference.VarPattern.var;

/**
 *
 * @author Víctor
 */
public class DoubleNegation implements ReplacementRule{

    @Override
    public Expression apply(int discriminator, Expression expression) {
        Pairs pairs=new Pairs();
        pairs.add(
            NEGATION.pattern(NEGATION.pattern("p")),
            var("p")
        );
        return pairs.matchPairs(discriminator, expression);
    }
    
}
