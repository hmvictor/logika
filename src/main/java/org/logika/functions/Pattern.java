package org.logika.functions;

import java.util.Map;
import org.logika.exp.Expression;

/**
 *
 * @author Víctor
 */
public interface Pattern {
    
//    void match(Map<String, Expression> expressionMap, Expression expresion);
    
    Matcher createMatcher(Expression exp);
    
    Expression buildExpression(Map<String, Expression> expressionMap);
    
}
