package org.logika.parsing;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.logika.exp.Expression;
import org.logika.grammar.LogikaLexer;
import org.logika.grammar.LogikaParser;

/**
 *
 * @author Víctor
 */
public class PropositionalExpressionParser {
    
    public Expression parse(String text) {
        LogikaLexer lexer = new LogikaLexer(CharStreams.fromString(text));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        LogikaParser parser = new LogikaParser(tokens);
        ParseTreeWalker parseTreeWalker = new ParseTreeWalker();
        PropositionalExpresionListener listener=new PropositionalExpresionListener();
        parseTreeWalker.walk(listener, parser.propositionalExpression());
        return listener.getExpression();
    }

}
