package org.logika.ui;

import org.logika.parsing.ArgumentParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import org.logika.Argument;
import org.logika.TablaVerdadArgumento;
import org.logika.TablaVerdadExpresion;
import org.logika.exp.BinaryOperator;
import org.logika.exp.Expression;
import org.logika.exp.UnaryOperator;
import org.logika.parsing.CommandInterpreter;
import org.logika.parsing.PropositionalExpressionParser;

/**
 *
 * @author Víctor
 */
public class ArgumentFrame extends javax.swing.JFrame {
    private Path filePath;
    
    private List<Expression> demostrations=new LinkedList<>();
    
    private List<Section> sections=new LinkedList<>();
    private Section currentSection;
    
    private CustomDocumentFilter customDocumentFilter=new CustomDocumentFilter();

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
        argumentText.getInputMap().put(KeyStroke.getKeyStroke("alt N"), "insertNegation");
        argumentText.getInputMap().put(KeyStroke.getKeyStroke("alt MINUS"), "insertNegation");
        argumentText.getActionMap().put("insertNegation", new InsertSymbolAction(UnaryOperator.NEGATION.getSymbol()));
        
        argumentText.getInputMap().put(KeyStroke.getKeyStroke("alt D"), "insertDisyunction");
        argumentText.getInputMap().put(KeyStroke.getKeyStroke("alt V"), "insertDisyunction");
        argumentText.getActionMap().put("insertDisyunction", new InsertSymbolAction(BinaryOperator.DISYUNCTION.getSymbol()));
        
        argumentText.getInputMap().put(KeyStroke.getKeyStroke("alt C"), "insertConjunction");
        argumentText.getInputMap().put(KeyStroke.getKeyStroke("alt PERIOD"), "insertConjunction");
        argumentText.getActionMap().put("insertConjunction", new InsertSymbolAction(BinaryOperator.CONJUNCTION.getSymbol()));
        
        argumentText.getInputMap().put(KeyStroke.getKeyStroke("alt I"), "insertImplication");
        argumentText.getInputMap().put(KeyStroke.getKeyStroke("alt D"), "insertImplication");
        argumentText.getActionMap().put("insertImplication", new InsertSymbolAction(BinaryOperator.MATERIAL_IMPLICATION.getSymbol()));
        
        argumentText.getInputMap().put(KeyStroke.getKeyStroke("alt E"), "insertEquality");
        argumentText.getActionMap().put("insertEquality", new InsertSymbolAction(BinaryOperator.MATERIAL_EQUALITY.getSymbol()));
        
        argumentText.getInputMap().put(KeyStroke.getKeyStroke("alt T"), "insertThereof");
        argumentText.getActionMap().put("insertThereof", new InsertSymbolAction("∴"));
        
        commandText.getInputMap().put(KeyStroke.getKeyStroke("alt N"), "insertNegation");
        commandText.getInputMap().put(KeyStroke.getKeyStroke("alt MINUS"), "insertNegation");
        commandText.getActionMap().put("insertNegation", new InsertSymbolAction(UnaryOperator.NEGATION.getSymbol()));

        commandText.getInputMap().put(KeyStroke.getKeyStroke("alt D"), "insertDisyunction");
        commandText.getInputMap().put(KeyStroke.getKeyStroke("alt V"), "insertDisyunction");
        commandText.getActionMap().put("insertDisyunction", new InsertSymbolAction(BinaryOperator.DISYUNCTION.getSymbol()));
        
        commandText.getInputMap().put(KeyStroke.getKeyStroke("alt C"), "insertConjunction");
        commandText.getInputMap().put(KeyStroke.getKeyStroke("alt PERIOD"), "insertConjunction");
        commandText.getActionMap().put("insertConjunction", new InsertSymbolAction(BinaryOperator.CONJUNCTION.getSymbol()));
        
        commandText.getInputMap().put(KeyStroke.getKeyStroke("alt I"), "insertImplication");
        commandText.getInputMap().put(KeyStroke.getKeyStroke("alt D"), "insertImplication");
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
        ((AbstractDocument)argumentText.getDocument()).setDocumentFilter(customDocumentFilter);
        ((AbstractDocument)commandText.getDocument()).setDocumentFilter(customDocumentFilter);
        fileChooser.setFileFilter(new FileNameExtensionFilter("JSON", "json"));
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
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        autoUppercase = new javax.swing.JCheckBox();

        fileChooser.setFileFilter(null);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        argumentText.setColumns(20);
        argumentText.setRows(5);
        argumentText.addFocusListener(focusListener);
        argumentText.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                argumentTextFocusGained(evt);
            }
        });
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
        commandText.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                commandTextFocusGained(evt);
            }
        });
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

        jButton12.setText("Print ...");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jButton13.setText(" Thruth Table ...");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jButton14.setText("Reset");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        jButton16.setText("+");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        jButton17.setText("...");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        autoUppercase.setMnemonic('u');
        autoUppercase.setText("Uppercase Auto");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 224, Short.MAX_VALUE)
                        .addComponent(jButton14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton16))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(autoUppercase))
                            .addComponent(jSplitPane1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton7, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton6, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton12)
                    .addComponent(jButton13)
                    .addComponent(autoUppercase))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton17))
                    .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 465, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton8)
                    .addComponent(jButton9)
                    .addComponent(jButton10)
                    .addComponent(jButton11)
                    .addComponent(jButton14)
                    .addComponent(jButton16))
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
                if(!filePath.toString().endsWith(".json")) {
                    filePath=Path.of(filePath.toString()+".json");
                }
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
        argumentText.setText("");
        commandText.setText("");
        filePath=null;
        setTitle(null);
        reset();
        currentSection=new Section();
        sections=new LinkedList<>();
        sections.add(currentSection);
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        new BatchWorker().execute();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        String input = JOptionPane.showInputDialog(this, "Enter a expression, number or leave null for argument");
        if(input != null) {
            Argument argumento = new ArgumentParser().parseArgument(argumentText.getText().trim());
            StringWriter stringWriter = new StringWriter();
            if(input.isBlank()) {
                argumento.print(new PrintWriter(stringWriter));
            }else{
                Expression expression=null;
                try{
                    int index=Integer.parseInt(input);
                    expression = getExpression(argumento, index);
                }catch(NumberFormatException ex) {
                    expression=new PropositionalExpressionParser().parse(input);
                }
                if(expression != null) {
                    stringWriter.append(expression.toUserString(argumento.getDescriptionByAlias()));
                }
            }
            new TextDialog(this, stringWriter.toString()).setVisible(true);
        }
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        String input = JOptionPane.showInputDialog(this, "Enter a expression, number or leave null for argument");
        if(input != null) {
            Argument argumento = new ArgumentParser().parseArgument(argumentText.getText().trim());
            if(input.isBlank()) {
                TablaVerdadArgumento tablaVerdad = argumento.createTablaVerdad();
                TableModel tableModel=new ThruthTableModel(tablaVerdad);
                new ThruthTableDialog(this, tableModel).setVisible(true);
            }else{
                Expression expression=null;
                try{
                    int index=Integer.parseInt(input);
                    expression = getExpression(argumento, index);
                }catch(NumberFormatException ex) {
                    expression=new PropositionalExpressionParser().parse(input);
                }
                if(expression != null) {
                    TablaVerdadExpresion tablaVerdad = TablaVerdadExpresion.build(expression);
                    TableModel tableModel=new ThruthTableModel(tablaVerdad);
                    new ThruthTableDialog(this, tableModel).setVisible(true);
                }
            }
        }
    }//GEN-LAST:event_jButton13ActionPerformed

    private Expression getExpression(Argument argumento, int index) {
        Expression expression;
        if((index-1) < argumento.getPremisesCount()) {
            expression=argumento.getPremise(index-1);
        }else {
            expression=demostrations.get((index-1)-argumento.getPremisesCount());
        }
        return expression;
    }

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        reset();
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        currentSection.setArgument(argumentText.getText());
        currentSection.setCommands(commandText.getText());
        currentSection=new Section();
        sections.add(currentSection);
        argumentText.setText("");
        commandText.setText("");
        reset();
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        Section selectedSection=new SearchDialog(this).open(sections);
        if(selectedSection != null) {
            currentSection=selectedSection;
            argumentText.setText(currentSection.argument);
            commandText.setText(currentSection.commands);
        }
    }//GEN-LAST:event_jButton17ActionPerformed

    private void commandTextFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_commandTextFocusGained
        autoUppercase.setSelected(false);
    }//GEN-LAST:event_commandTextFocusGained

    private void argumentTextFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_argumentTextFocusGained
        autoUppercase.setSelected(true);
    }//GEN-LAST:event_argumentTextFocusGained

    private void reset() {
        demostrations.clear();
        demostrationText.setText(null);
    }

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
            Argument argument = new ArgumentParser().parseArgument(argumentText.getText().trim());
            int caretpos = commandText.getCaretPosition();
            int linenum = commandText.getLineOfOffset(caretpos);
            CommandInterpreter commandInterpreter = new CommandInterpreter(ArgumentFrame.this, argument, demostrations);
            Object result=commandInterpreter.execute(commandText.getText().split("\n")[linenum].trim());
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
            Argument argument = new ArgumentParser().parseArgument(argumentText.getText().trim());
            
            demostrations.clear();
            demostrationText.setText(null);
            CommandInterpreter commandInterpreter = new CommandInterpreter(ArgumentFrame.this, argument, demostrations);
            for (String command : commandText.getText().split("\n")) {
                Object result=commandInterpreter.execute(command);
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
            currentSection.setArgument(argumentText.getText());
            currentSection.setCommands(commandText.getText());
            Files.writeString(filePath, new ObjectMapper().writeValueAsString(sections.toArray(new Section[sections.size()])), StandardCharsets.UTF_8);
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

    private class LoadFileWorker extends SwingWorker<Section[], Void> {

        private Path filePath;

        public LoadFileWorker(Path filePath) {
            this.filePath = filePath;
        }

        @Override
        protected Section[] doInBackground() throws Exception {
            return new ObjectMapper().readValue(Files.readString(filePath, StandardCharsets.UTF_8), Section[].class);
        }

        @Override
        protected void done() {
            try {
                sections=new LinkedList<>(Arrays.asList(get()));
                if (sections != null) {
                    currentSection=sections.get(0);
                    boolean filterEnabled = customDocumentFilter.isEnabled();
                    customDocumentFilter.setEnabled(false);
                    argumentText.setText(currentSection.argument);
                    commandText.setText(currentSection.commands);
                    customDocumentFilter.setEnabled(filterEnabled);
                }
                setTitle(filePath.toAbsolutePath().toString());
            } catch (InterruptedException | ExecutionException ex) {
                ex.printStackTrace();
            }
        }

    }
    
    public static class Section {
        private String argument;
        private String commands;

        public Section(String argument, String commands) {
            this.argument = argument;
            this.commands = commands;
        }

        public Section() {
        }

        public String getArgument() {
            return argument;
        }

        public void setArgument(String argument) {
            this.argument = argument;
        }

        public String getCommands() {
            return commands;
        }

        public void setCommands(String commands) {
            this.commands = commands;
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
    private javax.swing.JCheckBox autoUppercase;
    private javax.swing.JTextArea commandText;
    private javax.swing.JTextArea demostrationText;
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
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

    private class CustomDocumentFilter extends DocumentFilter {
        private boolean enabled=true;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
        
        @Override
        public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            super.replace(fb, offset, length, enabled && autoUppercase.isSelected()? text.toUpperCase(): text, attrs);
        }

        @Override
        public void insertString(DocumentFilter.FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            super.insertString(fb, offset, enabled && autoUppercase.isSelected() ? string.toUpperCase(): string, attr);
        }

        @Override
        public void remove(DocumentFilter.FilterBypass fb, int offset, int length) throws BadLocationException {
            super.remove(fb, offset, length);
        }
        
    }
    
}
