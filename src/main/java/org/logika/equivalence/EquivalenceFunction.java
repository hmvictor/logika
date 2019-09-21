package org.logika.equivalence;

import org.logika.exp.Expression;

/**
 *
 * @author Víctor
 */
public interface EquivalenceFunction {
    
    Expression apply(int variation, Expression expression);
    
}
