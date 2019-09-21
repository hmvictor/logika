package org.logika.exp;

/**
 *
 * @author Víctor
 * @param <T>
 */
public interface Operation<T extends Operator> extends Expression {

    T getOperator();
    
}
