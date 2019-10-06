package org.logika.replacement;

import java.util.LinkedList;
import java.util.List;
import static org.logika.replacement.Pair.pair;
import org.logika.exp.Expression;
import org.logika.inference.Pattern;

/**
 *
 * @author Víctor
 */
public class Pairs {
    private List<Pair> pairs=new LinkedList<>();
    
    public Pairs add(Pattern side1, Pattern side2) {
        pairs.add(pair(side1, side2));
        return this;
    }
    
    public Expression matchPairs(int discriminator, Expression expression) {
        List<Pair> matchedPairs=new LinkedList<>();
        for (Pair pair : pairs) {
            if(pair.getSide1().createMatcher(expression).getResult()) {
                matchedPairs.add(pair(pair.getSide1(), pair.getSide2()));
                continue;
            }
            if(pair.getSide2().createMatcher(expression).getResult()) {
                matchedPairs.add(pair(pair.getSide2(), pair.getSide1()));
            }
        }
        if(matchedPairs.isEmpty()) {
            throw new IllegalArgumentException("No match");
        }
        if(matchedPairs.size() == 1) {
            return matchedPairs.get(0).getSide2().buildExpression(matchedPairs.get(0).getSide1().createMatcher(expression).getExpressionMap());
        } else {
            if(discriminator == -1) {
                throw new IllegalArgumentException("Need variation");
            }
            return matchedPairs.get(discriminator-1).getSide2().buildExpression(matchedPairs.get(discriminator-1).getSide1().createMatcher(expression).getExpressionMap());
        }
    }
    
}
