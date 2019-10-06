package org.logika.parsing;

import org.logika.parsing.ArgumentParser;
import java.util.Map;
import java.util.stream.Stream;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.logika.Argument;
import org.logika.exp.Sentence;

/**
 *
 * @author Víctor
 */
public class ArgumentParserTest {
    
    @ParameterizedTest
    @MethodSource("testData")
    public void debeParsearArgument(String text, Argument expectedArgument) {
        Argument parsedArgument = new ArgumentParser().parseArgument(text);
        assertThat(parsedArgument.getPremisesCount(), is(expectedArgument.getPremisesCount()));
        for(int i=0; i < expectedArgument.getPremisesCount(); i++) {
            assertThat(parsedArgument.getPremise(i), is(expectedArgument.getPremise(i)));
        }
        assertThat(parsedArgument.getConclusion(), is(expectedArgument.getConclusion()));
        assertThat(parsedArgument.getDescriptionByAlias().size(), is(expectedArgument.getDescriptionByAlias().size()));
        for (Map.Entry<Character, String> entry : expectedArgument.getDescriptionByAlias().entrySet()) {
            assertThat(parsedArgument.getDescriptionByAlias().get(entry.getKey()), is(entry.getValue()));
        }
    }
    
    private static Stream<Arguments> testData() {
	return Stream.of(
            Arguments.of("A\n∴A", new Argument().premise(new Sentence('A')).then(new Sentence('A'))),
            Arguments.of("A\n∴A\nA)\"Description A\"", new Argument().premise(new Sentence('A')).then(new Sentence('A')).given('A', "Description A")),
            Arguments.of("A\nA\n∴A", new Argument().premise(new Sentence('A')).premise(new Sentence('A')).then(new Sentence('A')))    
        );
    }
    
}
