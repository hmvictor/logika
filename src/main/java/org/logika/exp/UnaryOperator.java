package org.logika.exp;

import java.util.Map;
import org.logika.inference.Pattern;
import org.logika.inference.UnaryOperationPattern;
import static org.logika.inference.VarPattern.var;

/**
 *
 * @author Víctor
 */
public enum UnaryOperator implements Operator{
    NEGATION("~") {
        
        @Override
        public boolean apply(Expression exp, Map<Character, Boolean> value) {
            return !exp.evaluate(value);
        }
        
    };

    private final String symbol;

    private UnaryOperator(String symbol) {
        this.symbol = symbol;
    }
    
    public boolean apply(Expression exp, Map<Character, Boolean> value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getSymbol() {
        return symbol;
    }
    
    public Expression of(Expression exp) {
        return new UnaryOperation(this, exp);
    }
    
    public Expression of(char sentence) {
        return new UnaryOperation(this, new Sentence(sentence));
    }
    
    public UnaryOperationPattern pattern(Pattern exp) {
        return new UnaryOperationPattern(this, exp);
    }
    
    public UnaryOperationPattern pattern(String alias) {
        return new UnaryOperationPattern(this, var(alias));
    }
    
    public static UnaryOperator of(String text) {
        for (UnaryOperator value : values()) {
            if(value.getSymbol().equals(text)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Unknown symbol: "+text);
    }
    
}
