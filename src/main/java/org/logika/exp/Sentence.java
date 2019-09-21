package org.logika.exp;

import java.util.Map;

/**
 *
 * @author Víctor
 */
public class Sentence  implements Expression {
    private char alias;

    public Sentence(char alias) {
        this.alias = alias;
    }

    public char getAlias() {
        return alias;
    }
    
    @Override
    public boolean evaluate(Map<Character, Boolean> values) {
        return values.get(alias);
    }

    @Override
    public String toString() {
        return String.valueOf(alias);
    }

    @Override
    public String toUserString(Map<Character, String> descriptionByAlias) {
        return descriptionByAlias.get(alias);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.alias;
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
        final Sentence other = (Sentence) obj;
        if (this.alias != other.alias) {
            return false;
        }
        return true;
    }
    
}
