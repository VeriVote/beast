/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea.Autocompletion;

import edu.pse.beast.celectiondescriptioneditor.UserActions.SaveBeforeChangeHandler;
import edu.pse.beast.codearea.InputToCode.JTextPaneToolbox;
import edu.pse.beast.codearea.InputToCode.UserInsertToCode;
import edu.pse.beast.codearea.SaveTextBeforeRemove;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.text.BadLocationException;

/**
 *
 * @author Holger-Desktop
 */
public class AutocompletionController implements KeyListener, AncestorListener {
    private JTextPane pane;
    private ArrayList<AutocompletionOption> completionOptions = new ArrayList<>();
    private FindWordsConcurrently conc;
    private Thread t;
    private AutocompletionFrame frame;
    private UserInsertToCode insertToCode;
    
    
    public AutocompletionController(JTextPane pane, UserInsertToCode insertToCode) {
        this.pane = pane;
        pane.addAncestorListener(this);
        pane.addKeyListener(this);
        conc = new FindWordsConcurrently(pane, this);
        t = new Thread(conc);
        t.start();
        frame = new AutocompletionFrame();
        frame.addKeyListener(this);
        this.insertToCode = insertToCode;
        frame.setAlwaysOnTop(true);
        frame.getjList1().addKeyListener(this);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }
    
    public void addAutocompletionString(String s) {
        if(!completionOptions.contains(s)) completionOptions.add(new AutocompletionOption(s, s));
    }

    @Override
    public void keyTyped(KeyEvent ke) {        
       if(ke.getSource() == frame.getjList1()) {
            if(ke.getKeyCode() == KeyEvent.VK_UP ||
                ke.getKeyCode() == KeyEvent.VK_DOWN ||
                ke.getKeyChar() == KeyEvent.VK_ENTER) {                
                return;
            } else {
                for(int i = 0; i < pane.getKeyListeners().length; ++i) {
                if(pane.getKeyListeners()[i] != this) 
                        pane.getKeyListeners()[i].keyTyped(ke);
                }
                giveMenuOptions();
                ke.consume();
            }            
            
       }
    }

    @Override
    public void keyPressed(KeyEvent ke) {      
        if(ke.getSource() == frame.getjList1()) {
            if(ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
                frame.setVisible(false);
            } else if(ke.getKeyCode() == KeyEvent.VK_ENTER) {
                String s = frame.getjList1().getSelectedValue();
                if(s != null) choseCompletion(s);
                frame.setVisible(false);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        if(ke.isControlDown() && ke.getKeyChar()== KeyEvent.VK_SPACE) {            
            try {
                giveMenuOptions();
                Point loc = new Point(
                        pane.modelToView(pane.getCaretPosition()).x,
                        pane.modelToView(pane.getCaretPosition()).y);                  
                frame.setLocation(pane.getLocationOnScreen().x + loc.x, pane.getLocationOnScreen().y + loc.y + 20);
                frame.setVisible(true);
            } catch (BadLocationException ex) {
                Logger.getLogger(AutocompletionController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private String getWordAtCursor() throws BadLocationException {
        int wordAtCursorStartPos = JTextPaneToolbox.getWordBeginningAtCursor(pane);
        String wordAtCursor = 
            pane.getStyledDocument().getText(
                wordAtCursorStartPos + 1, pane.getCaretPosition() - wordAtCursorStartPos);
        return wordAtCursor;
    }

    private void giveMenuOptions() {
        try {
            String word = getWordAtCursor();
            completionOptions.sort(new WordsMoreEqualToComparator(word));
            String opts[] = new String[completionOptions.size()];
            for (int i = 0; i < opts.length; i++) {
                opts[i] = completionOptions.get(i).getInsertString();                
            }
            frame.showUserOptions(opts);
        } catch (BadLocationException ex) {
            Logger.getLogger(AutocompletionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void choseCompletion(String chosen) {
        for(AutocompletionOption opt : completionOptions) {
            if(opt.getInsertString().equals(chosen)) {   
                opt.insertInto(pane, pane.getCaretPosition(), insertToCode);                
                return;                
            }
        }
    }

    @Override
    public void ancestorAdded(AncestorEvent ae) {
        if(!ae.getAncestor().isVisible()) frame.setVisible(false);
    }

    @Override
    public void ancestorRemoved(AncestorEvent ae) {
        if(!ae.getAncestor().isVisible()) frame.setVisible(false);
    }

    @Override
    public void ancestorMoved(AncestorEvent ae) {
        if(!ae.getAncestor().isVisible()) frame.setVisible(false);
    }

    public void add(AutocompletionOption opt) {
        completionOptions.add(opt);
    }
}
