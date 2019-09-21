package org.logika.functions;

import java.util.Map;
import org.logika.exp.Expression;
import org.logika.exp.UnaryOperation;
import org.logika.exp.UnaryOperator;

/**
 *
 * @author Víctor
 */
public class UnaryOperationPattern implements Pattern{
    private UnaryOperator operator;
    private Pattern patternExpression;

    public UnaryOperationPattern(UnaryOperator operator, Pattern patternExpression) {
        this.operator = operator;
        this.patternExpression = patternExpression;
    }
    
    @Override
    public Expression buildExpression(Map<String, Expression> expressionMap) {
        return new UnaryOperation(operator, patternExpression.buildExpression(expressionMap));
    }

    @Override
    public Matcher createMatcher(Expression exp) {
        Matcher matcher=new Matcher();
        matcher.setResult(true);
        if(!(exp instanceof UnaryOperation)) {
            matcher.setResult(false);
            matcher.addMessage("Mismatch operation");
        }
        if(exp instanceof UnaryOperation) {
            UnaryOperation operation=(UnaryOperation) exp;
            if(!operation.getOperator().equals(operator)) {
                matcher.setResult(false);
                matcher.addMessage("Mismatch operator");
            }
            matcher=matcher.merge(patternExpression.createMatcher(operation.getExpression()));
        }
        return matcher;
    }
    
}


