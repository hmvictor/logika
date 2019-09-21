package org.logika.ui;

import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Víctor
 */
public class ThruthValueRender extends DefaultTableCellRenderer{

    @Override
    protected void setValue(Object value) {
        setText(((Boolean)value) ? "V": "F");
    }
    
}
