package org.logika;

import org.logika.exp.Expression;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Víctor
 */
public class TablaVerdadExpresion implements TablaVerdad{
    private Character[] aliases;
    private List<SimpleRow> rows;
    
    public TablaVerdadExpresion(Character[] aliases, List<SimpleRow> rows) {
        this.aliases = aliases;
        this.rows=rows;
    }

    public Character[] getAliases() {
        return aliases;
    }

    public List<SimpleRow> getRows() {
        return rows;
    }
    
    public static TablaVerdadExpresion build(Expression expr) {
        Character[] aliases=ExpressionOperations.getAliases(expr).toArray(new Character[0]);
        int rowCount=(int)Math.pow(2, aliases.length);
        boolean[] allowedValues={true, false};
        List<SimpleRow> rows=new LinkedList<>();
        for(int r=0; r < rowCount; r++) {
            SimpleRow row=new SimpleRow(aliases.length);
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

    @Override
    public int getRowCount() {
        return rows.size();
    }

    @Override
    public Row getRow(int rowIndex) {
        return rows.get(rowIndex);
    }

    @Override
    public String getColumnName(int column) {
        if(column < aliases.length) {
            return String.valueOf(aliases[column]);
        }else if(column == aliases.length) {
            return "R";
        }else{
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public int getColumnCount() {
        return aliases.length + 1;
    }
    
    public static class SimpleRow implements Row{
        private boolean[] inputValues;
        private boolean conclusionValue;
        
        public SimpleRow(int inputLength) {
            inputValues=new boolean[inputLength];
        }
        
        @Override
        public boolean getValue(int columnIndex) {
            if(columnIndex < inputValues.length) {
                return inputValues[columnIndex];
            }else if(columnIndex == inputValues.length) {
                return conclusionValue;
            }else{
                throw new IndexOutOfBoundsException();
            }
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
