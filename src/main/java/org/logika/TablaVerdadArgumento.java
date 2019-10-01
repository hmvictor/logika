package org.logika;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Víctor
 */
public class TablaVerdadArgumento implements TablaVerdad {
    
    private Set<Character> aliases;
    private List<ArgumentRow> rows;

    public TablaVerdadArgumento(Set<Character> aliases, List<ArgumentRow> rows) {
        this.aliases = aliases;
        this.rows = rows;
    }

    public List<ArgumentRow> getRows() {
        return rows;
    }

    public Set<Character> getAliases() {
        return aliases;
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
        ArgumentRow row = rows.get(0);
        if(column < aliases.size()) {
            return String.valueOf(new LinkedList<>(aliases).get(column));
        }else if(column >= row.inputValues.length && column < (row.inputValues.length  + row.premisesValues.length)) {
            return String.valueOf(column-aliases.size()+1);
        }else if(column == (row.inputValues.length  + row.premisesValues.length)){
            return "C";
        }else{
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public int getColumnCount() {
        return rows.get(0).inputValues.length + rows.get(0).premisesValues.length + 1;
    }

    public static class ArgumentRow implements Row{

        private boolean[] inputValues;
        private boolean[] premisesValues;
        private boolean conclusionValue;

        public ArgumentRow(int inputLength, int premisesLength) {
            inputValues = new boolean[inputLength];
            premisesValues = new boolean[premisesLength];
        }

        @Override
        public boolean getValue(int columnIndex) {
            if(columnIndex < inputValues.length) {
                return inputValues[columnIndex];
            }else if(columnIndex >= inputValues.length && columnIndex < (inputValues.length  + premisesValues.length)) {
                return premisesValues[columnIndex-inputValues.length];
            }else if(columnIndex == (inputValues.length  + premisesValues.length)){
                return conclusionValue;
            } else{
                throw new IndexOutOfBoundsException();
            }
        }
        
        public boolean getInputValue(int i) {
            return inputValues[i];
        }

        public void setInputValue(int i, boolean value) {
            inputValues[i] = value;
        }

        public boolean getPremiseValue(int i) {
            return premisesValues[i];
        }

        public void setPremiseValue(int i, boolean value) {
            premisesValues[i] = value;
        }

        public boolean getConclusionValue() {
            return conclusionValue;
        }

        public void setConclusionValue(boolean value) {
            conclusionValue = value;
        }

        public boolean negatesArgument() {
            boolean a=true;
            for (boolean value : premisesValues) {
                a=a&&value;
            }
            return a && !conclusionValue;
        }
        
    }
    
}
