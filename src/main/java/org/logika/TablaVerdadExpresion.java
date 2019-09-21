package org.logika;

import org.logika.exp.Expression;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Víctor
 */
public class TablaVerdadExpresion {
    private Character[] aliases;
    private List<Row> rows;
    
    public TablaVerdadExpresion(Character[] aliases, List<Row> rows) {
        this.aliases = aliases;
        this.rows=rows;
    }

    public Character[] getAliases() {
        return aliases;
    }

    public List<Row> getRows() {
        return rows;
    }
    
    public static TablaVerdadExpresion build(Expression expr) {
        Character[] aliases=ExpressionOperations.getAliases(expr).toArray(new Character[0]);
        int rowCount=(int)Math.pow(2, aliases.length);
        boolean[] allowedValues={true, false};
        List<Row> rows=new LinkedList<>();
        for(int r=0; r < rowCount; r++) {
            Row row=new Row(aliases.length);
            for(int c=0; c < aliases.length; c++) {
                row.setInputValue(aliases.length-c-1, allowedValues[(r/(int)Math.pow(2, c))%2]);
            }
            HashMap<Character, Boolean> hashMap = new HashMap<>();
            for(int c=0; c < aliases.length; c++) {
                hashMap.put(aliases[c], row.getInputValue(c));
            }
            row.setConclusionValue(expr.evaluate(hashMap));
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
        return new TablaVerdadExpresion(aliases, rows);
    }
    
    public static class Row {
        private boolean[] inputValues;
        private boolean conclusionValue;

        public Row(int inputLength) {
            inputValues=new boolean[inputLength];
        }
        
        public boolean getInputValue(int i) {
            return inputValues[i];
        }
        
        public void setInputValue(int i, boolean value) {
            inputValues[i]=value;
        }
        
        public boolean getConclusionValue() {
            return conclusionValue;
        }
        
        public void setConclusionValue(boolean value) {
            conclusionValue=value;
        }
        
    }

}
