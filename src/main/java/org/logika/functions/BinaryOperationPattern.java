package org.logika.functions;

import java.util.Map;
import org.logika.exp.BinaryOperation;
import org.logika.exp.BinaryOperator;
import org.logika.exp.Expression;

/**
 *
 * @author Víctor
 */
public class BinaryOperationPattern implements Pattern{
    private BinaryOperator operator;
    private Pattern left;
    private Pattern right;

    public BinaryOperationPattern(BinaryOperator operator, Pattern left, Pattern right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }
    
    @Override
    public Matcher createMatcher(Expression exp) {
        Matcher matcher=new Matcher();
        matcher.setResult(true);
        if(exp instanceof BinaryOperation) {
            BinaryOperation operation=(BinaryOperation) exp;
            if(operation.getOperator().equals(operator)) {
                matcher=matcher.merge(left.createMatcher(operation.getLeft()));
                matcher=matcher.merge(right.createMatcher(operation.getRight()));
            }else{
                matcher.setResult(false);
                matcher.addMessage("Mismatch operator");
            }
        }else{
            matcher.setResult(false);
            matcher.addMessage("Mismatch operation");
        }
        return matcher;
    }

    
    @Override
    public BinaryOperation buildExpression(Map<String, Expression> map) {
        return new BinaryOperation(operator, left.buildExpression(map), right.buildExpression(map));
    }
    
}


