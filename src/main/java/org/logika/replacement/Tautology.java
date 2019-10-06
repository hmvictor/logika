package org.logika.replacement;

import static org.logika.exp.BinaryOperator.CONJUNCTION;
import static org.logika.exp.BinaryOperator.DISYUNCTION;
import org.logika.exp.Expression;
import static org.logika.inference.VarPattern.var;

/**
 *
 * @author Víctor
 */
public class Tautology implements ReplacementRule {

    @Override
    public Expression apply(int discriminator, Expression expression) {
        Pairs pairs=new Pairs();
        pairs.add(
            DISYUNCTION.pattern("p", "p"),
            var("p")
        ).add(
            CONJUNCTION.pattern("p", "p"),
            var("p")
        );
        return pairs.matchPairs(discriminator, expression);
    }
    
}
