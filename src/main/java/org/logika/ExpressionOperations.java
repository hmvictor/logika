package org.logika;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.logika.exp.BinaryOperation;
import org.logika.exp.Expression;
import org.logika.exp.Operation;
import org.logika.exp.Sentence;
import org.logika.exp.UnaryOperation;

/**
 *
 * @author Víctor
 */
public class ExpressionOperations {

    public static Object get(Expression exp, ExpressionOperation... path) {
        Object result = exp;
        for (ExpressionOperation subpath : path) {
            result = subpath.apply((Expression) result);
        }
        return result;
    }
    
    public static Object get(Expression exp, List<ExpressionOperations.ExpressionOperation> path) {
        Object result = exp;
        for (ExpressionOperation subpath : path) {
            result = subpath.apply((Expression) result);
        }
        return result;
    }
    
    public static Set<Character> getAliases(Expression exp) {
        Set<Character> aliases=new HashSet<>();
        if(exp instanceof Sentence) {
            aliases.add(((Sentence)exp).getAlias());
        }else if(exp instanceof BinaryOperation) {
            aliases.addAll(getAliases(((BinaryOperation)exp).getLeft()));
            aliases.addAll(getAliases(((BinaryOperation)exp).getRight()));
        }else if(exp instanceof UnaryOperation){
            aliases.addAll(getAliases(((UnaryOperation)exp).getExpression()));
        }
        return aliases;
    }

    public enum ExpressionOperation {
        LEFT_OPERAND {
            @Override
            public Object apply(Expression expression) {
                if (!(expression instanceof BinaryOperation)) {
                    throw new IllegalArgumentException();
                }
                return ((BinaryOperation) expression).getLeft();
            }

        },
        RIGHT_OPERAND {

            @Override
            public Object apply(Expression expression) {
                if (!(expression instanceof BinaryOperation)) {
                    throw new IllegalArgumentException();
                }
                return ((BinaryOperation) expression).getRight();
            }

        },
        OPERAND {

            @Override
            public Object apply(Expression expression) {
                if (!(expression instanceof UnaryOperation)) {
                    throw new IllegalArgumentException();
                }
                return ((UnaryOperation) expression).getExpression();
            }

        },
        OPERATOR {

            @Override
            public Object apply(Expression expression) {
                if (expression instanceof Operation) {
                    return ((Operation) expression).getOperator();
                }
                throw new IllegalArgumentException();
            }

        };

        public Object apply(Expression expression) {
            throw new UnsupportedOperationException();
        }

    }

}
