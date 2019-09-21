package org.logika;

import org.logika.exp.Expression;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.logika.TablaVerdadArgumento.Row;

/**
 *
 * @author Víctor
 */
public class Argument {
    private List<Expression> premises=new LinkedList<>();
    private Expression conclusion;
    private Map<Character, String> descriptionByAlias=new HashMap<>();
    
    public Argument given(char alias, String description) {
        descriptionByAlias.put(alias, description);
        return this;
    }

    public Map<Character, String> getDescriptionByAlias() {
        return descriptionByAlias;
    }
    
    public Argument then(Expression conclusion) {
        this.conclusion=conclusion;
        return this;
    }
    
    public Argument premise(Expression expr) {
        premises.add(expr);
        return this;
    }

    public void print(PrintWriter writer) {
        StringBuilder builder=new StringBuilder();
        for (Expression premise : premises) {
            builder.append(premise.toUserString(descriptionByAlias));
            builder.append(".");
        }
        builder.append(" Por lo tanto ").append(conclusion.toUserString(descriptionByAlias));
        builder.append('.');
        writer.print(builder.toString());
    }

    public int getPremisesCount() {
        return premises.size();
    }
    
    public TablaVerdadArgumento createTablaVerdad() {
        Set<Character> aliases=new HashSet<>();
        for (Expression premise : premises) {
            aliases.addAll(ExpressionOperations.getAliases(premise));
        }
        
        boolean[] allowedValues={true, false};
        List<Row> rows=new LinkedList<>();
        List<Character> tmpAliases=new LinkedList<>(aliases);
        for(int r=0; r < (int)Math.pow(2, aliases.size()); r++) {
            Row row=new Row(aliases.size(), premises.size());
            for(int c=0; c < aliases.size(); c++) {
                row.setInputValue(aliases.size()-c-1, allowedValues[(r/(int)Math.pow(2, c))%2]);
            }
            HashMap<Character, Boolean> hashMap = new HashMap<>();
            for(int c=0; c < tmpAliases.size(); c++) {
                hashMap.put(tmpAliases.get(c), row.getInputValue(c));
            }
            for (int c=0; c < premises.size(); c++) {
                row.setPremiseValue(c, premises.get(c).evaluate(hashMap));
            }
            row.setConclusionValue(conclusion.evaluate(hashMap));
            rows.add(row);
        }
        
        /**
         * 
         * \ 0 1 2 3 4 5 6 7
         * 1 0 1 0 1 0 1 0 1 / 1
         * 2 0 0 1 1 0 0 1 1 / 2
         * 3 0 0 0 0 1 1 1 1 / 4
         * 
         */
        return new TablaVerdadArgumento(aliases, rows);
    }

    public Expression getPremise(int index) {
        return premises.get(index);
    }

    
}
