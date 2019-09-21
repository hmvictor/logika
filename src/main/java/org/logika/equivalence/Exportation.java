package org.logika.equivalence;

import static org.logika.exp.BinaryOperator.CONJUNCTION;
import static org.logika.exp.BinaryOperator.MATERIAL_IMPLICATION;
import org.logika.exp.Expression;
import static org.logika.functions.VarPattern.var;

/**
 *
 * @author Víctor
 */
public class Exportation implements EquivalenceFunction{

    @Override
    public Expression apply(int variation, Expression expression) {
        Pairs pairs=new Pairs();
        pairs.add(
            MATERIAL_IMPLICATION.pattern(CONJUNCTION.pattern("p", "q"), var("r")),
            MATERIAL_IMPLICATION.pattern(var("p"), MATERIAL_IMPLICATION.pattern("q", "r"))

        );
        return pairs.matchPairs(variation, expression);
    }
    
}
