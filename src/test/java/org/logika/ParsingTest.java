package org.logika;

import org.logika.ui.PropositionalExpressionParser;
import java.util.stream.Stream;
import static org.hamcrest.CoreMatchers.is;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.logika.exp.Expression;
import static org.hamcrest.MatcherAssert.assertThat;
import org.logika.exp.Sentence;
import static org.logika.exp.UnaryOperator.*;
import static org.logika.exp.BinaryOperator.*;

/**
 *
 * @author Víctor
 */
public class ParsingTest {
    
    @ParameterizedTest
    @MethodSource("testData")
    public void deberiaHacerParsing(String text, Expression exp) {
        assertThat(new PropositionalExpressionParser().parse(text), is(exp));
    }
    
    private static Stream<Arguments> testData() {
	return Stream.of(
            Arguments.of("A", new Sentence('A')),
            Arguments.of("~A", NEGATION.of('A')),
            Arguments.of("A˅A", DISYUNCTION.of('A', 'A')),
            Arguments.of("~(A˅A)", NEGATION.of(DISYUNCTION.of('A', 'A'))),
            Arguments.of("~A˅~A", DISYUNCTION.of(NEGATION.of('A'), NEGATION.of('A'))),
            Arguments.of("A˅(A˅A)", DISYUNCTION.of(new Sentence('A'), DISYUNCTION.of('A', 'A'))),
            Arguments.of("A˅~(A˅A)", DISYUNCTION.of(new Sentence('A'), NEGATION.of(DISYUNCTION.of('A', 'A')))),
            Arguments.of("A˅(A˅(A˅A))", DISYUNCTION.of(new Sentence('A'), DISYUNCTION.of(new Sentence('A'), DISYUNCTION.of('A', 'A')))),
            Arguments.of("(((A˅A)˅(A˅A))˅((A˅A)˅(A˅A)))˅A", DISYUNCTION.of(DISYUNCTION.of(DISYUNCTION.of(DISYUNCTION.of('A', 'A'), DISYUNCTION.of('A', 'A')), DISYUNCTION.of(DISYUNCTION.of('A', 'A'), DISYUNCTION.of('A', 'A'))), new Sentence('A')))
        );
    }
    
}

