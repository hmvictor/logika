package org.logika.ui;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import org.logika.TablaVerdadArgumento;

/**
 *
 * @author Víctor
 */
public class ArgumentThruthValueRender extends DefaultTableCellRenderer{

    @Override
    protected void setValue(Object value) {
        setText(((Boolean)value) ? "V": "F");
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int rowIndex, int column) {
        Component renderer = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, rowIndex, column);
        TablaVerdadArgumento.ArgumentRow row = (TablaVerdadArgumento.ArgumentRow)((ThruthTableModel)table.getModel()).getTablaVerdad().getRow(rowIndex);
        if(row.negatesArgument()) {
            renderer.setForeground(Color.RED);
        }else{
            renderer.setForeground(Color.BLACK);
        }
        return renderer;
    }
    
    
    
}
