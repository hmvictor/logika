package org.logika.inference;

import org.logika.exp.Expression;

/**
 *
 * @author Víctor
 */
public interface InferenceRule {
    
    Expression apply(Expression... expressions);
    
}
