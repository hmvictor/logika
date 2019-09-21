package org.logika;

import java.util.List;
import java.util.Set;

/**
 *
 * @author Víctor
 */
public class TablaVerdadArgumento {
    
    private Set<Character> aliases;
    private List<Row> rows;

    public TablaVerdadArgumento(Set<Character> aliases, List<Row> rows) {
        this.aliases = aliases;
        this.rows = rows;
    }

    public List<Row> getRows() {
        return rows;
    }

    public Set<Character> getAliases() {
        return aliases;
    }

    public static class Row {

        private boolean[] inputValues;
        private boolean[] premisesValues;
        private boolean conclusionValue;

        public Row(int inputLength, int premisesLength) {
            inputValues = new boolean[inputLength];
            premisesValues = new boolean[premisesLength];
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
    }
    
}
