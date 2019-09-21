package org.logika.exp;

import java.util.Map;
import java.util.Objects;

/**
 *
 * @author Víctor
 */
public class UnaryOperation implements Operation<UnaryOperator> {
    private final UnaryOperator operator;
    private final Expression expression;

    public UnaryOperation(UnaryOperator operator, Expression expression) {
        this.operator = operator;
        this.expression = expression;
    }

    @Override
    public UnaryOperator getOperator() {
        return operator;
    }
    
    public Expression getExpression() {
        return expression;
    }
    
    @Override
    public boolean evaluate(Map<Character, Boolean> values) {
        return operator.apply(expression, values);
    }
    
    @Override
    public String toString() {
        return operator.getSymbol()+expression;
    }

    @Override
    public String toUserString(Map<Character, String> descriptionByAlias) {
        return String.format("no %s", expression.toUserString(descriptionByAlias));
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + Objects.hashCode(this.operator);
        hash = 43 * hash + Objects.hashCode(this.expression);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UnaryOperation other = (UnaryOperation) obj;
        if (this.operator != other.operator) {
            return false;
        }
        if (!Objects.equals(this.expression, other.expression)) {
            return false;
        }
        return true;
    }
    
}
