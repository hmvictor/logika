package org.logika.ui;

import java.util.LinkedList;
import java.util.stream.Stream;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.logika.Argument;
import static org.logika.exp.BinaryOperator.DISYUNCTION;
import org.logika.exp.Expression;
import org.logika.exp.Sentence;
import static org.logika.exp.UnaryOperator.NEGATION;

/**
 *
 * @author Víctor
 */
public class CommandInterpreterTest {
    
    @ParameterizedTest
    @MethodSource("testData")
    public void debeInterpretarComando(String text, Expression expectedResult) {
        Expression result=(Expression) new CommandInterpreter(null, new Argument(), new LinkedList<>()).execute(text);
        assertThat(result, is(expectedResult));
    }
    
    private static Stream<Arguments> testData() {
	return Stream.of(
            Arguments.of("A⊃B, A M.P.", new Sentence('B')),
            Arguments.of("A D.N.", NEGATION.of(NEGATION.of('A'))), 
            Arguments.of("A˅B/right D.N.", DISYUNCTION.of(new Sentence('A'), NEGATION.of(NEGATION.of('B')))),
            Arguments.of("A˅B/right", new Sentence('B')),
            Arguments.of("A˅B", DISYUNCTION.of('A', 'B'))    
        );
    }
    
}
