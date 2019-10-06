package org.logika.replacement;

import static org.logika.exp.BinaryOperator.CONJUNCTION;
import static org.logika.exp.BinaryOperator.DISYUNCTION;
import org.logika.exp.Expression;
import static org.logika.inference.VarPattern.var;

/**
 *
 * @author Víctor
 */
public class Asociation implements ReplacementRule {

    @Override
    public Expression apply(int discriminator, Expression expression) {
        Pairs pairs=
            new Pairs()
                .add(
                    DISYUNCTION.pattern(var("p"), DISYUNCTION.pattern("q", "r")),
                    DISYUNCTION.pattern(DISYUNCTION.pattern("p", "q"), var("r"))
                ).add(
                    CONJUNCTION.pattern(var("p"), CONJUNCTION.pattern("q", "r")),
                    CONJUNCTION.pattern(CONJUNCTION.pattern("p", "q"), var("r"))
                );
        return pairs.matchPairs(discriminator, expression);
    }
    
}
