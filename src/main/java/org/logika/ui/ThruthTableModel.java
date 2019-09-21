package org.logika.ui;

import javax.swing.table.AbstractTableModel;
import org.logika.TablaVerdad;

/**
 *
 * @author Víctor
 */
public class ThruthTableModel extends AbstractTableModel {
    private TablaVerdad tablaVerdad;

    public ThruthTableModel(TablaVerdad tablaVerdad) {
        this.tablaVerdad = tablaVerdad;
    }

    @Override
    public String getColumnName(int column) {
        return tablaVerdad.getColumnName(column);
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return Boolean.class;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    @Override
    public int getRowCount() {
        return tablaVerdad.getRowCount();
    }

    @Override
    public int getColumnCount() {
        return tablaVerdad.getColumnCount();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return tablaVerdad.getRow(rowIndex).getValue(columnIndex);
    }

}
