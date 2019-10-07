package org.logika.ui;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.SimpleAttributeSet;

/**
 *
 * @author Víctor
 */
class InsertSymbolAction extends AbstractAction {
    
    private final String symbol;

    public InsertSymbolAction(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        try {
            JTextComponent textComponent = (JTextComponent) event.getSource();
            textComponent.getDocument().insertString(textComponent.getCaretPosition(), symbol, new SimpleAttributeSet());
        } catch (BadLocationException ex) {
            throw new RuntimeException(ex);
        }
    }
    
}
