package org.logika.exp;

import java.util.Map;

/**
 *
 * @author Víctor
 */
public interface Expression {

    boolean evaluate(Map<Character, Boolean> values);

    String toUserString(Map<Character, String> descriptionByAlias);
    
}
