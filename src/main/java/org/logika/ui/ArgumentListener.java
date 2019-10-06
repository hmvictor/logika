package org.logika.ui;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.logika.Argument;
import org.logika.exp.Expression;
import org.logika.grammar.LogikaParser;
import org.logika.ui.PropositionalExpressionParser.PropositionalExpresionListener;

/**
 *
 * @author Víctor
 */
public class ArgumentListener extends PropositionalExpresionListener{

    public Argument getArgument() {
        return (Argument) getStack().pop();
    }

    @Override
    public void exitPremises(LogikaParser.PremisesContext ctx) {
        List<Expression> premises = new LinkedList<>();
        for (LogikaParser.PropositionalExpressionContext propositionContext : ctx.propositionalExpression()) {
            premises.add(0, getExpression());
        }
        getStack().push(premises);
    }

    @Override
    public void exitConclusion(LogikaParser.ConclusionContext ctx) {
        getStack().push(getStack().pop());
    }
    
//    @Override
//    public void exitTextCharacters(LogikaParser.TextCharactersContext ctx) {
//        getStack().push(ctx.getText());
//    }

    @Override
    public void exitDefinition(LogikaParser.DefinitionContext ctx) {
        Map<Character, String> map = new HashMap<>();
        map.put(ctx.SENTENCE().getText().charAt(0), ctx.getChild(2).getText().replace("\"", ""));
        getStack().push(map);
    }
    
    @Override
    public void exitDefinitions(LogikaParser.DefinitionsContext ctx) {
        Map<Character, String> map = new HashMap<>();
        for (LogikaParser.DefinitionContext definitionContext : ctx.definition()) {
            map.putAll((Map<? extends Character, ? extends String>) getStack().pop());
        }
        getStack().push(map);
    }
    
    @Override
    public void exitArgument(LogikaParser.ArgumentContext ctx) {
        Map<Character, String> map = (Map<Character, String>) getStack().pop();
        Expression conclusion = getExpression();
        List<Expression> premises = (List<Expression>) getStack().pop();
        Argument argument = new Argument().then(conclusion);
        for (Expression premise : premises) {
            argument.premise(premise);
        }
        for (Map.Entry<Character, String> entry : map.entrySet()) {
            argument.given(entry.getKey(), entry.getValue());
        }
        getStack().push(argument);
    }

}
