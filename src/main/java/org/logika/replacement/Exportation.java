package org.logika.replacement;

import static org.logika.exp.BinaryOperator.CONJUNCTION;
import static org.logika.exp.BinaryOperator.MATERIAL_IMPLICATION;
import org.logika.exp.Expression;
import static org.logika.inference.VarPattern.var;

/**
 *
 * @author Víctor
 */
public class Exportation implements ReplacementRule{

    @Override
    public Expression apply(int discriminator, Expression expression) {
        Pairs pairs=new Pairs();
        pairs.add(
            MATERIAL_IMPLICATION.pattern(CONJUNCTION.pattern("p", "q"), var("r")),
            MATERIAL_IMPLICATION.pattern(var("p"), MATERIAL_IMPLICATION.pattern("q", "r"))

        );
        return pairs.matchPairs(discriminator, expression);
    }
    
}
