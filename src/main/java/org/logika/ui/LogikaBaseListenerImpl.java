package org.logika.ui;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import org.logika.Argument;
import org.logika.exp.Expression;
import org.logika.grammar.LogikaParser;

/**
 *
 * @author Víctor
 */
public class LogikaBaseListenerImpl extends BaseListener {

    public LogikaBaseListenerImpl() {
        super(new Stack<>());
    }
    
    public Argument getArgument() {
        return (Argument) getStack().pop();
    }

    @Override
    public void exitPremises(LogikaParser.PremisesContext ctx) {
        List<Expression> premises = new LinkedList<>();
        for (LogikaParser.PropositionContext propositionContext : ctx.proposition()) {
            premises.add((Expression) getStack().pop());
        }
        getStack().push(premises);
        System.out.println("premises " + ctx.getText());
    }

    @Override
    public void exitProposition(LogikaParser.PropositionContext ctx) {
        System.out.println("exit proposition" + ctx.getText());
    }

    @Override
    public void exitConclusion(LogikaParser.ConclusionContext ctx) {
        getStack().push(getStack().pop());
        System.out.println("conclusion " + ctx.getText());
    }

    @Override
    public void exitDefinition(LogikaParser.DefinitionContext ctx) {
        Map<Character, String> map = new HashMap<>();
        map.put(ctx.SENTENCE().getText().charAt(0), ctx.TEXT().getText());
        getStack().push(map);
        System.out.println("definition " + ctx.getText());
    }

    @Override
    public void exitDefinitions(LogikaParser.DefinitionsContext ctx) {
        Map<Character, String> map = new HashMap<>();
        for (LogikaParser.DefinitionContext definitionContext : ctx.definition()) {
            map.putAll((Map<? extends Character, ? extends String>) getStack().pop());
        }
        getStack().push(map);
        System.out.println("definitions " + ctx.getText());
    }

    @Override
    public void exitArgument(LogikaParser.ArgumentContext ctx) {
        Map<Character, String> map = (Map<Character, String>) getStack().pop();
        Expression conclusion = (Expression) getStack().pop();
        List<Expression> premises = (List<Expression>) getStack().pop();
        Argument argument = new Argument().then(conclusion);
        for (Expression premise : premises) {
            argument.premise(premise);
        }
        for (Map.Entry<Character, String> entry : map.entrySet()) {
            argument.given(entry.getKey(), entry.getValue());
        }
        getStack().push(argument);
        System.out.println("argument " + ctx.getText());
    }
    
}
