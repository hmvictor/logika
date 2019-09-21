package org.logika.functions;

import java.util.Map;
import org.logika.exp.Expression;

/**
 *
 * @author Víctor
 */
public class VarPattern implements Pattern {
    private String name;

    private VarPattern(String name) {
        this.name = name;
    }

    @Override
    public Matcher createMatcher(Expression exp) {
        Matcher matcher=new Matcher();
        matcher.setResult(true);
         if (matcher.get(name) != null) {
            if (!matcher.get(name).equals(exp)) {
                matcher.setResult(false);
                matcher.addMessage(String.format("Mismatch var %s. Found: , expected:  ", name, exp, matcher.get(name)));
            }
        }
        matcher.set(name, exp);
        return matcher;
    }

    @Override
    public Expression buildExpression(Map<String, Expression> expressionMap) {
        return expressionMap.get(name);
    }
    
    public static Pattern var(String name) {
        return new VarPattern(name);
    }
    
}
