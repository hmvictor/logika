/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.logika.ui;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.logika.Argument;
import org.logika.exp.BinaryOperator;
import org.logika.exp.Expression;
import org.logika.exp.UnaryOperator;
import org.logika.grammar.LogikaLexer;
import org.logika.grammar.LogikaParser;

/**
 *
 * @author Víctor
 */
public class ArgumentFrame extends javax.swing.JFrame {
    private Path filePath;
    
    private List<Expression> demostrations=new LinkedList<>();

    /**
     * Creates new form ArgumentFrame
     */
    public ArgumentFrame() {
        initComponents();
        jButton2.addActionListener(actionListener);
        jButton3.addActionListener(actionListener);
        jButton4.addActionListener(actionListener);
        jButton5.addActionListener(actionListener);
        jButton6.addActionListener(actionListener);
        jButton7.addActionListener(actionListener);
        argumentText.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("alt N"), "insertNegation");
        argumentText.getActionMap().put("insertNegation", new InsertSymbolAction(UnaryOperator.NEGATION.getSymbol()));
        
        argumentText.getInputMap().put(KeyStroke.getKeyStroke("alt D"), "insertDisyunction");
        argumentText.getActionMap().put("insertDisyunction", new InsertSymbolAction(BinaryOperator.DISYUNCTION.getSymbol()));
        
        argumentText.getInputMap().put(KeyStroke.getKeyStroke("alt C"), "insertConjunction");
        argumentText.getActionMap().put("insertConjunction", new InsertSymbolAction(BinaryOperator.CONJUNCTION.getSymbol()));
        
        argumentText.getInputMap().put(KeyStroke.getKeyStroke("alt I"), "insertImplication");
        argumentText.getActionMap().put("insertImplication", new InsertSymbolAction(BinaryOperator.MATERIAL_IMPLICATION.getSymbol()));
        
        argumentText.getInputMap().put(KeyStroke.getKeyStroke("alt E"), "insertEquality");
        argumentText.getActionMap().put("insertEquality", new InsertSymbolAction(BinaryOperator.MATERIAL_EQUALITY.getSymbol()));
        
        argumentText.getInputMap().put(KeyStroke.getKeyStroke("alt T"), "insertThereof");
        argumentText.getActionMap().put("insertThereof", new InsertSymbolAction("∴"));
        
        commandText.getInputMap().put(KeyStroke.getKeyStroke("alt N"), "insertNegation");
        commandText.getActionMap().put("insertNegation", new InsertSymbolAction(UnaryOperator.NEGATION.getSymbol()));

        commandText.getInputMap().put(KeyStroke.getKeyStroke("alt D"), "insertDisyunction");
        commandText.getActionMap().put("insertDisyunction", new InsertSymbolAction(BinaryOperator.DISYUNCTION.getSymbol()));
        
        commandText.getInputMap().put(KeyStroke.getKeyStroke("alt C"), "insertConjunction");
        commandText.getActionMap().put("insertConjunction", new InsertSymbolAction(BinaryOperator.CONJUNCTION.getSymbol()));
        
        commandText.getInputMap().put(KeyStroke.getKeyStroke("alt I"), "insertImplication");
        commandText.getActionMap().put("insertImplication", new InsertSymbolAction(BinaryOperator.MATERIAL_IMPLICATION.getSymbol()));
        
        commandText.getInputMap().put(KeyStroke.getKeyStroke("alt E"), "insertEquality");
        commandText.getActionMap().put("insertEquality", new InsertSymbolAction(BinaryOperator.MATERIAL_EQUALITY.getSymbol()));
        
        commandText.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.CTRL_DOWN_MASK), "executeCommand");
        commandText.getActionMap().put("executeCommand", new AbstractAction() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                new CommandWorker().execute();
            }
            
        });
        
        commandText.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.ALT_DOWN_MASK), "executeBatch");
        commandText.getActionMap().put("executeBatch", new AbstractAction() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                new BatchWorker().execute();
            }
            
        });
    }
    
    private static class InsertSymbolAction extends AbstractAction {
        private String symbol;

        public InsertSymbolAction(String symbol) {
            this.symbol = symbol;
        }
        
        @Override
        public void actionPerformed(ActionEvent event) {
            JTextArea textArea = (JTextArea)event.getSource();
            textArea.insert(symbol, textArea.getCaretPosition());
        }
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileChooser = new javax.swing.JFileChooser();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        argumentText = new javax.swing.JTextArea();
        jSplitPane2 = new javax.swing.JSplitPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        demostrationText = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        commandText = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        argumentText.setColumns(20);
        argumentText.setRows(5);
        argumentText.addFocusListener(focusListener);
        jScrollPane1.setViewportView(argumentText);

        jSplitPane1.setTopComponent(jScrollPane1);

        demostrationText.setEditable(false);
        demostrationText.setColumns(20);
        demostrationText.setRows(5);
        jScrollPane2.setViewportView(demostrationText);

        jSplitPane2.setLeftComponent(jScrollPane2);

        commandText.setColumns(20);
        commandText.setRows(5);
        commandText.addFocusListener(focusListener);
        jScrollPane3.setViewportView(commandText);

        jSplitPane2.setRightComponent(jScrollPane3);

        jSplitPane1.setRightComponent(jSplitPane2);

        jButton1.setText("Execute Line");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("~");

        jButton3.setText("˅");

        jButton4.setText("•");

        jButton5.setText("⊃");

        jButton6.setText("≡");

        jButton7.setText("∴");

        jButton8.setText("Execute Batch");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setText("Load");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setText("Save");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setText("New");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jSplitPane1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 190, Short.MAX_VALUE)
                        .addComponent(jButton8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton7)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton8)
                    .addComponent(jButton9)
                    .addComponent(jButton10)
                    .addComponent(jButton11))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        new CommandWorker().execute();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        if(filePath == null) {
            if (fileChooser.showSaveDialog(ArgumentFrame.this) == JFileChooser.APPROVE_OPTION) {
                filePath=fileChooser.getSelectedFile().toPath();
            }else{
                return;
            }
        }
        new SaveFileWorker(filePath).execute();
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        if (fileChooser.showOpenDialog(ArgumentFrame.this) == JFileChooser.APPROVE_OPTION) {
            loadFile(fileChooser.getSelectedFile().toPath());
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
//        argumentText.setText(null);
//        commandText.setText(null);
//        setTitle(null);
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        new BatchWorker().execute();
    }//GEN-LAST:event_jButton8ActionPerformed

    private JTextArea textArea;

    private FocusListener focusListener = new FocusListener() {

        @Override
        public void focusGained(FocusEvent e) {

        }

        @Override
        public void focusLost(FocusEvent e) {
            if (e.getComponent() instanceof JTextArea) {
                textArea = (JTextArea) e.getComponent();
            } else {
                textArea = null;
            }
        }

    };

    private ActionListener actionListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (textArea instanceof JTextArea) {
                textArea.insert(e.getActionCommand(), textArea.getCaretPosition());
                textArea.requestFocus();
            }
        }

    };

    private void loadFile(Path filePath) {
        this.filePath=filePath;
        new LoadFileWorker(filePath).execute();
    }

    private class CommandWorker extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            LogikaLexer lexer = new LogikaLexer(CharStreams.fromString(argumentText.getText().trim()));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            LogikaParser parser = new LogikaParser(tokens);
            ParseTreeWalker parseTreeWalker = new ParseTreeWalker();
            LogikaBaseListenerImpl logikaBaseListenerImpl = new LogikaBaseListenerImpl();
            parseTreeWalker.walk(logikaBaseListenerImpl, parser.argument());
            Argument argument = logikaBaseListenerImpl.getArgument();
            
            int caretpos = commandText.getCaretPosition();
            int linenum = commandText.getLineOfOffset(caretpos);
            lexer = new LogikaLexer(CharStreams.fromString(commandText.getText().split("\n")[linenum].trim()));
            tokens = new CommonTokenStream(lexer);
            parser = new LogikaParser(tokens);
            parseTreeWalker = new ParseTreeWalker();
            CommandInterpreter commandInterpreter = new CommandInterpreter(ArgumentFrame.this, argument, demostrations);
            parseTreeWalker.walk(commandInterpreter, parser.command());
            Object result=commandInterpreter.getResult();
            if(result instanceof Expression) {
                if(!demostrationText.getText().trim().isEmpty()) {
                    demostrationText.append("\n");
                }
                demostrations.add((Expression) result);
                demostrationText.append(result.toString());
            }
            return null;
        }

        @Override
        protected void done() {
            try {
                Void result = get();
            } catch (InterruptedException | ExecutionException ex) {
                ex.printStackTrace();
            }
        }

    }
    
    private class BatchWorker extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            LogikaLexer lexer = new LogikaLexer(CharStreams.fromString(argumentText.getText().trim()));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            LogikaParser parser = new LogikaParser(tokens);
            ParseTreeWalker parseTreeWalker = new ParseTreeWalker();
            LogikaBaseListenerImpl logikaBaseListenerImpl = new LogikaBaseListenerImpl();
            parseTreeWalker.walk(logikaBaseListenerImpl, parser.argument());
            Argument argument = logikaBaseListenerImpl.getArgument();
            
            demostrations.clear();
            demostrationText.setText(null);
            for (String command : commandText.getText().split("\n")) {
                lexer = new LogikaLexer(CharStreams.fromString(command.trim()));
                tokens = new CommonTokenStream(lexer);
                parser = new LogikaParser(tokens);
                parseTreeWalker = new ParseTreeWalker();
                CommandInterpreter commandInterpreter = new CommandInterpreter(ArgumentFrame.this, argument, demostrations);
                parseTreeWalker.walk(commandInterpreter, parser.command());
                Object result=commandInterpreter.getResult();
                if(result instanceof Expression) {
                    if(!demostrationText.getText().trim().isEmpty()) {
                        demostrationText.append("\n");
                    }
                    demostrations.add((Expression) result);
                    demostrationText.append(result.toString());
                }
            }
            return null;
        }

        @Override
        protected void done() {
            try {
                Void result = get();
            } catch (InterruptedException | ExecutionException ex) {
                ex.printStackTrace();
            }
        }

    }

    private class SaveFileWorker extends SwingWorker<Void, Void> {

        private Path filePath;

        public SaveFileWorker(Path filePath) {
            this.filePath = filePath;
        }

        @Override
        protected Void doInBackground() throws Exception {
            ObjectNode objectNode = new ObjectNode(JsonNodeFactory.instance);
            objectNode.set("argument", new TextNode(argumentText.getText()));
            objectNode.set("commands", new TextNode(commandText.getText()));
            Files.writeString(filePath, new ObjectMapper().writeValueAsString(objectNode), StandardCharsets.UTF_8);
            return null;
        }

        @Override
        protected void done() {
            try {
                Void result = get();
                setTitle(filePath.toAbsolutePath().toString());
            } catch (InterruptedException | ExecutionException ex) {
                ex.printStackTrace();
            }
        }

    }

    private class LoadFileWorker extends SwingWorker<JsonNode, Void> {

        private Path filePath;

        public LoadFileWorker(Path filePath) {
            this.filePath = filePath;
        }

        @Override
        protected JsonNode doInBackground() throws Exception {
            return new ObjectMapper().readTree(Files.readString(filePath, StandardCharsets.UTF_8));
        }

        @Override
        protected void done() {
            try {
                JsonNode node = get();
                if (node != null) {
                    argumentText.setText(node.get("argument").asText());
                    commandText.setText(node.get("commands").asText());
                }
                setTitle(filePath.toAbsolutePath().toString());
            } catch (InterruptedException | ExecutionException ex) {
                ex.printStackTrace();
            }
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ArgumentFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ArgumentFrame argumentFrame = new ArgumentFrame();
                argumentFrame.setVisible(true);
                if(args.length > 0) {
                    argumentFrame.loadFile(Path.of(args[0]));
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea argumentText;
    private javax.swing.JTextArea commandText;
    private javax.swing.JTextArea demostrationText;
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    // End of variables declaration//GEN-END:variables
}
