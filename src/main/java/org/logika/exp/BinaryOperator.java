package org.logika.exp;

import java.util.Map;
import org.logika.inference.BinaryOperationPattern;
import org.logika.inference.Pattern;
import static org.logika.inference.VarPattern.var;

/**
 *
 * @author Víctor
 */
public enum BinaryOperator implements Operator {
    
    CONJUNCTION("•"){
        
        @Override
        public boolean apply(Expression left, Expression right, Map<Character, Boolean> value) {
            return left.evaluate(value) && right.evaluate(value);
        }
        
        @Override
        public String toUserString(Map<Character, String> descriptionByAlias, Expression left, Expression right) {
            return String.format("%s y %s", left.toUserString(descriptionByAlias), right.toUserString(descriptionByAlias));
        }
        
    },
    DISYUNCTION("˅") {
        
        @Override
        public boolean apply(Expression left, Expression right, Map<Character, Boolean> value) {
            return left.evaluate(value) || right.evaluate(value);
        }
        
        @Override
        public String toUserString(Map<Character, String> descriptionByAlias, Expression left, Expression right) {
            return String.format("%s o %s", left.toUserString(descriptionByAlias), right.toUserString(descriptionByAlias));
        }
        
    },
    MATERIAL_IMPLICATION("⊃") {
        
        @Override
        public boolean apply(Expression left, Expression right, Map<Character, Boolean> value) {
            return !left.evaluate(value) || right.evaluate(value);
        }
        
        @Override
        public String toUserString(Map<Character, String> descriptionByAlias, Expression left, Expression right) {
            return String.format("si %s, entonces %s", left.toUserString(descriptionByAlias), right.toUserString(descriptionByAlias));
        }
        
    },
    MATERIAL_EQUALITY("≡") {
        
        @Override
        public boolean apply(Expression left, Expression right, Map<Character, Boolean> value) {
            return left.evaluate(value) == right.evaluate(value);
        }
        
        @Override
        public String toUserString(Map<Character, String> descriptionByAlias, Expression left, Expression right) {
            return String.format("%s igual a %s", left.toUserString(descriptionByAlias), right.toUserString(descriptionByAlias));
        }
        
    };

    private String symbol;

    private BinaryOperator(String symbol) {
        this.symbol = symbol;
    }
    
    public boolean apply(Expression left, Expression right, Map<Character, Boolean> value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getSymbol() {
        return symbol;
    }
    
    public String toUserString(Map<Character, String> descriptionByAlias, Expression left, Expression right) {
        throw new UnsupportedOperationException();
    }
    
    public Expression of(Expression left, Expression right) {
        return new BinaryOperation(this, left, right);
    }
    
    public Expression of(char left, char right) {
        return new BinaryOperation(this, new Sentence(left), new Sentence(right));
    }
    
    public BinaryOperationPattern pattern(Pattern left, Pattern right) {
        return new BinaryOperationPattern(this, left, right);
    }
    
    public BinaryOperationPattern pattern(String left, String right) {
        return new BinaryOperationPattern(this, var(left), var(right));
    }
    
    public static BinaryOperator of(String text) {
        for (BinaryOperator value : values()) {
            if(value.getSymbol().equals(text)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Unknown symbol:"+text);
    }
    
}
