package org.logika.parsing;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.logika.Argument;
import org.logika.grammar.LogikaLexer;
import org.logika.grammar.LogikaParser;

/**
 *
 * @author Víctor
 */
public class ArgumentParser {
    
    public Argument parseArgument(String text) throws RecognitionException {
        LogikaLexer lexer = new LogikaLexer(CharStreams.fromString(text.trim()));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        LogikaParser parser = new LogikaParser(tokens);
        ParseTreeWalker parseTreeWalker = new ParseTreeWalker();
        ArgumentListener listener = new ArgumentListener();
        parseTreeWalker.walk(listener, parser.argument());
        return listener.getArgument();
    }
    
}
