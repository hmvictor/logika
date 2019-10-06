package org.logika.parsing;

import java.awt.Frame;
import java.util.List;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.logika.Argument;
import org.logika.exp.Expression;
import org.logika.grammar.LogikaLexer;
import org.logika.grammar.LogikaParser;

/**
 *
 * @author Víctor
 */
public class CommandInterpreter {
    private Frame owner;
    private Argument argument;
    private List<Expression> demostrations;
    
    public CommandInterpreter(Frame owner, Argument argument, List<Expression> demostrations) {
        this.owner=owner;
        this.argument=argument;
        this.demostrations=demostrations;
    }
    
    public Object execute(String text) {
        LogikaLexer lexer = new LogikaLexer(CharStreams.fromString(text.trim()));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        LogikaParser parser = new LogikaParser(tokens);
        ParseTreeWalker parseTreeWalker = new ParseTreeWalker();
        CommandListener commandInterpreter = new CommandListener(owner, argument, demostrations);
        parseTreeWalker.walk(commandInterpreter, parser.command());
        return commandInterpreter.getResult();
    }
    
}
