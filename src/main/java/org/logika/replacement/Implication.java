package org.logika.replacement;

import static org.logika.exp.BinaryOperator.DISYUNCTION;
import static org.logika.exp.BinaryOperator.MATERIAL_IMPLICATION;
import org.logika.exp.Expression;
import static org.logika.exp.UnaryOperator.NEGATION;
import static org.logika.inference.VarPattern.var;

/**
 *
 * @author Víctor
 */
public class Implication implements ReplacementRule{

    @Override
    public Expression apply(int discriminator, Expression expression) {
        Pairs pairs=new Pairs();
        pairs.add(
            MATERIAL_IMPLICATION.pattern("p", "q"),
            DISYUNCTION.pattern(NEGATION.pattern("p"), var("q"))
        );
        return pairs.matchPairs(discriminator, expression);
    }
    
}
