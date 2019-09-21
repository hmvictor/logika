package org.logika.ui;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Víctor
 */
public class ThruthTableModel extends DefaultTableModel {

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return Boolean.class;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

}
