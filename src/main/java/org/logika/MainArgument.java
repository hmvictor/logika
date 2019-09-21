package org.logika;

import java.io.PrintWriter;
import static org.logika.exp.BinaryOperator.DISYUNCTION;
import org.logika.exp.Sentence;

/**
 *
 * @author Víctor
 */
public class MainArgument {
    
    public static void main(String[] args) {
        Argument argument=
            new Argument()
                .given('C', "los coches vuelan")
                .given('A', "las aves vuelan")
                .premise(DISYUNCTION.expression(new Sentence('C'), new Sentence('A')))
                .then(DISYUNCTION.expression(new Sentence('A'), new Sentence('C')));
        argument.print(new PrintWriter(System.out));
        
        System.out.println();
        TablaVerdadArgumento tablaVerdad=argument.createTablaVerdad();
        
        for (Character alias : tablaVerdad.getAliases()) {
            System.out.printf("%s ", alias);
        }
        System.out.printf("| ");
        for (int k=0; k < argument.getPremisesCount(); k++) {
            System.out.printf("%d ", k);
        }
        System.out.printf("| ");
        System.out.printf("C ");
        System.out.println();
        
        for (Character alias : tablaVerdad.getAliases()) {
            System.out.printf("--");
        }
        System.out.printf("--");
        for (int k=0; k < argument.getPremisesCount(); k++) {
            System.out.printf("--");
        }
        System.out.println("----");
        
        for (TablaVerdadArgumento.Row row : tablaVerdad.getRows()) {
            for (int k=0; k < tablaVerdad.getAliases().size(); k++) {
                System.out.printf("%s ", row.getInputValue(k)? 'V': 'F');
            }
            System.out.print("| ");
            for (int k=0; k < argument.getPremisesCount(); k++) {
                System.out.printf("%s ", row.getPremiseValue(k)? 'V': 'F');
            }
            System.out.printf("| ");
            System.out.printf("%s%n", row.getConclusionValue()? 'V': 'F');
        }
        
        
    }
    
}
