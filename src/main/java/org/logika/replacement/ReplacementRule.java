package org.logika.replacement;

import org.logika.exp.Expression;

/**
 *
 * @author Víctor
 */
public interface ReplacementRule {
    
    Expression apply(int discriminator, Expression expression);
    
}
