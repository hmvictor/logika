package org.logika.functions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import org.logika.exp.Expression;

/**
 *
 * @author Víctor
 */
public class Transformation {
    private List<Matching> matchings;
    private Function<TransformationContext, Expression> function;

    private Transformation(List<Matching> matchings) {
        this.matchings = matchings;
    }
    
    public Transformation then(Function<TransformationContext, Expression> function) {
        this.function=function;
        return this;
    }
    
    public Expression apply(Expression... expressions) {
        Matcher matcher = new Matcher();
        matcher.setResult(true);
        for (Matching matching : matchings) {
            matcher = matcher.merge(matching.pattern.createMatcher(expressions[matching.index]));
        }
        return function.apply(new TransformationContext(matcher.getExpressionMap(), expressions));
    }
    
    public static Transformation given(Matching... matchings) {
        return new Transformation(Arrays.asList(matchings));
    }
    
    public static class Matching {
        private int index;
        private Pattern pattern;

        public Matching(int index, Pattern pattern) {
            this.index = index;
            this.pattern = pattern;
        }
        
    }
    
    public static class TransformationContext {
        private Map<String, Expression> expressionMap;
        private Expression[] expressions;

        public TransformationContext(Map<String, Expression> expressionMap, Expression... expressions) {
            this.expressionMap = expressionMap;
            this.expressions = expressions;
        }
        
        public Map<String, Expression> getExpressionMap() {
            return expressionMap;
        }

        public Expression[] getExpressions() {
            return expressions;
        }
        
    }

}
