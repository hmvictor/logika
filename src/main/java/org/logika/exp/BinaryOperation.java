package org.logika.exp;

import java.util.Map;
import java.util.Objects;

/**
 *
 * @author Víctor
 */
public class BinaryOperation implements Operation<BinaryOperator> {
    private BinaryOperator operator;
    private Expression left;
    private Expression right;

    public BinaryOperation(BinaryOperator operator, Expression left, Expression right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    @Override
    public BinaryOperator getOperator() {
        return operator;
    }

    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
        return right;
    }
    
    @Override
    public boolean evaluate(Map<Character, Boolean> values) {
        return operator.apply(left, right, values);
    }

    @Override
    public String toString() {
        return String.format("(%s%s%s)", left.toString(), operator.getSymbol(), right.toString());
    }

    @Override
    public String toUserString(Map<Character, String> descriptionByAlias) {
        return String.format("(%s)", operator.toUserString(descriptionByAlias, left, right));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.operator);
        hash = 71 * hash + Objects.hashCode(this.left);
        hash = 71 * hash + Objects.hashCode(this.right);
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
        final BinaryOperation other = (BinaryOperation) obj;
        if (this.operator != other.operator) {
            return false;
        }
        if (!Objects.equals(this.left, other.left)) {
            return false;
        }
        if (!Objects.equals(this.right, other.right)) {
            return false;
        }
        return true;
    }
    
}
