package org.logika.equivalence;

import static org.logika.exp.BinaryOperator.DISYUNCTION;
import static org.logika.exp.BinaryOperator.MATERIAL_IMPLICATION;
import org.logika.exp.Expression;
import static org.logika.exp.UnaryOperator.NEGATION;
import static org.logika.functions.VarPattern.var;

/**
 *
 * @author Víctor
 */
public class Implication implements EquivalenceFunction{

    @Override
    public Expression apply(int variation, Expression expression) {
        Pairs pairs=new Pairs();
        pairs.add(
            MATERIAL_IMPLICATION.pattern("p", "q"),
            DISYUNCTION.pattern(NEGATION.pattern("p"), var("q"))
        );
        return pairs.matchPairs(variation, expression);
    }
    
}
