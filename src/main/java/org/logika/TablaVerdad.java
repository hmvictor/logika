package org.logika;


/**
 *
 * @author Víctor
 */
public interface TablaVerdad {
    
    int getRowCount();
    
    Row getRow(int rowIndex);
    
    int getColumnCount();

    String getColumnName(int column);
    
}
