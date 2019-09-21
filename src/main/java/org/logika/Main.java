package org.logika;

import org.logika.exp.Sentence;
import org.logika.exp.Expression;
import java.util.HashMap;
import java.util.Map;
import org.logika.TablaVerdadExpresion.Row;
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
//        Expresion expr=new BinaryOperation(Operator.CONJUNCTION, new Sentence("A"), new Sentence("B"));
//        
//        conjuction(sentence("A"), negate(sentence("B")));
//        conjunction(disyunction(sentence("A"), negate(sentence("A"))), sentence("B")).evaluate(value("A", false).value("B", true));
        
        Sentence a=new Sentence('A');
        Sentence b=new Sentence('B');
        Sentence c=new Sentence('C');
        
        Map<Character, Boolean> values=new HashMap<>();
        values.put('A', true);
        values.put('B', true);
        System.out.printf("Result: %s%n", CONJUNCTION.expression(a, b).evaluate(values));
        
        System.out.printf("Result: %s%n", CONJUNCTION.expression(a, NEGATION.expression(a)).evaluate(values));
        System.out.printf("Result: %s%n", DISYUNCTION.expression(a, NEGATION.expression(a)).evaluate(values));
        
        values.put('A', true);
        values.put('B', true);
        values.put('C', false);
        System.out.printf("Result: %s%n", MATERIAL_IMPLICATION.expression(a, CONJUNCTION.expression(b, c)).evaluate(values));
        
        System.out.println(ExpressionOperations.getAliases(MATERIAL_IMPLICATION.expression(a, CONJUNCTION.expression(b, c))));
        
        Expression exp=MATERIAL_IMPLICATION.expression(a, MATERIAL_EQUALITY.expression(b, c));
        exp=NEGATION.expression(CONJUNCTION.expression(a, a));
        
        TablaVerdadExpresion tablaVerdad=TablaVerdadExpresion.build(exp);
        
        for (Character alias : tablaVerdad.getAliases()) {
            System.out.printf("%s ", alias);
        }
        System.out.printf(" %s%n", exp);
        for (Character alias : tablaVerdad.getAliases()) {
            System.out.printf("--");
        }
        System.out.println("");
        for(Row row: tablaVerdad.getRows()) {
            for (int k=0; k < tablaVerdad.getAliases().length; k++) {
                System.out.printf("%s ", row.getInputValue(k)? 'V': 'F');
            }
            System.out.printf(" = %s%n", row.getConclusionValue()? 'V': 'F');
        }
        
//        for(TablaVerdad.Row row:tablaVerdad.getRows()) {
//            for (Boolean value : row.getVariableValues()) {
//                System.out.printf("%s ", value? 'V': 'F');
//            }
//            System.out.printf(" = %s%n", row.getResult());
//        }
    }
    
}
