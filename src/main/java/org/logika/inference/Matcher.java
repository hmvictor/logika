package org.logika.inference;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.logika.exp.Expression;

/**
 *
 * @author Víctor
 */
public class Matcher {
    private boolean result;
    
    private Map<String, Expression> expressionMap=new HashMap<>();
    private List<String> messages=new LinkedList<>();

    public void setResult(boolean result) {
        this.result = result;
    }
    
    public boolean getResult() {
        return result;
    }
    
    public void addMessage(String message) {
        messages.add(message);
    }
    
    public void set(String name, Expression exp) {
        expressionMap.put(name, exp);
    }
    
    public Expression get(String name) {
        return expressionMap.get(name);
    }

    public Matcher merge(Matcher secondMatcher) {
        Matcher tmp=new Matcher();
        tmp.setResult(result && secondMatcher.getResult());
        tmp.expressionMap.putAll(expressionMap);
        tmp.expressionMap.putAll(secondMatcher.expressionMap);
        return tmp;
    }

    public Map<String, Expression> getExpressionMap() {
        return expressionMap;
    }
    
}
