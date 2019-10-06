package org.logika.replacement;

import org.logika.inference.Pattern;

/**
 *
 * @author Víctor
 */
public class Pair {

    private Pattern side1;
    private Pattern side2;

    public Pair(Pattern side1, Pattern side2) {
        this.side1 = side1;
        this.side2 = side2;
    }

    public Pattern getSide1() {
        return side1;
    }

    public Pattern getSide2() {
        return side2;
    }
    
    public static Pair pair(Pattern a, Pattern b) {
        return new Pair(a, b);
    }

}
