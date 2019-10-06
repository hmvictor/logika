package org.logika;

import org.logika.exp.Sentence;
import org.logika.exp.Expression;
import java.util.HashMap;
import java.util.Map;
import org.logika.TablaVerdadExpresion.SimpleRow;
import static org.logika.exp.BinaryOperator.CONJUNCTION;
import static org.logika.exp.BinaryOperator.DISYUNCTION;
import static org.logika.exp.BinaryOperator.MATERIAL_EQUALITY;
import static org.logika.exp.BinaryOperator.MATERIAL_IMPLICATION;
import static org.logika.exp.UnaryOperator.NEGATION;

/**
 *
 * @author Víctor
 */
public class Main {
    
    public static void main(String[] args) {
        Sentence a=new Sentence('A');
        Sentence b=new Sentence('B');
        Sentence c=new Sentence('C');
        
        Map<Character, Boolean> values=new HashMap<>();
        values.put('A', true);
        values.put('B', true);
        System.out.printf("Result: %s%n", CONJUNCTION.of(a, b).evaluate(values));
        
        System.out.printf("Result: %s%n", CONJUNCTION.of(a, NEGATION.of(a)).evaluate(values));
        System.out.printf("Result: %s%n", DISYUNCTION.of(a, NEGATION.of(a)).evaluate(values));
        
        values.put('A', true);
        values.put('B', true);
        values.put('C', false);
        System.out.printf("Result: %s%n", MATERIAL_IMPLICATION.of(a, CONJUNCTION.of(b, c)).evaluate(values));
        
        System.out.println(ExpressionOperations.getAliases(MATERIAL_IMPLICATION.of(a, CONJUNCTION.of(b, c))));
        
        Expression exp=MATERIAL_IMPLICATION.of(a, MATERIAL_EQUALITY.of(b, c));
        exp=NEGATION.of(CONJUNCTION.of(a, a));
        
        TablaVerdadExpresion tablaVerdad=TablaVerdadExpresion.build(exp);
        
        for (Character alias : tablaVerdad.getAliases()) {
            System.out.printf("%s ", alias);
        }
        System.out.printf(" %s%n", exp);
        for (Character alias : tablaVerdad.getAliases()) {
            System.out.printf("--");
        }
        System.out.println("");
        for(SimpleRow row: tablaVerdad.getRows()) {
            for (int k=0; k < tablaVerdad.getAliases().length; k++) {
                System.out.printf("%s ", row.getInputValue(k)? 'V': 'F');
            }
            System.out.printf(" = %s%n", row.getConclusionValue()? 'V': 'F');
        }
    }
    
}
