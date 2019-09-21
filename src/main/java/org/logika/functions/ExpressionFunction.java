package org.logika.functions;

import org.logika.exp.Expression;

/**
 *
 * @author Víctor
 */
public interface ExpressionFunction {
    
    Expression apply(Expression... expressions);
    
}
