package org.logika.inference;

import java.util.Map;
import org.logika.exp.Expression;

/**
 *
 * @author Víctor
 */
public interface Pattern {
    
    Matcher createMatcher(Expression exp);
    
    Expression buildExpression(Map<String, Expression> expressionMap);
    
}
