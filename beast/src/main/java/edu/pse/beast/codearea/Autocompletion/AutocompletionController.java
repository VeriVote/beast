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
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;

/**
 *
 * @author Holger-Desktop
 */
public class AutocompletionController implements KeyListener {
    private JTextPane pane;
    private ArrayList<AutocompletionOption> completionOptions = new ArrayList<>();
    private FindWordsConcurrently conc;
    private Thread t;
    private AutocompletionPopupMenu menu;
    private UserInsertToCode insertToCode;
    
    public AutocompletionController(JTextPane pane, UserInsertToCode insertToCode) {
        this.pane = pane;
        pane.addKeyListener(this);
        conc = new FindWordsConcurrently(pane, this);
        t = new Thread(conc);
        t.start();
        menu = new AutocompletionPopupMenu(this);
        completionOptions.add(new AutocompletionOption("1", "first"));
        completionOptions.add(new AutocompletionOption("2", "secound"));
        completionOptions.add(new AutocompletionOption("3", "third"));
        this.insertToCode = insertToCode;
    }
    
    public void addAutocompletionString(String s) {
        if(!completionOptions.contains(s)) completionOptions.add(new AutocompletionOption(s, s));
    }

    @Override
    public void keyTyped(KeyEvent ke) {        
        if(!menu.isVisible()) return;
    }

    @Override
    public void keyPressed(KeyEvent ke) {
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        if(ke.isControlDown() && ke.getKeyChar()== KeyEvent.VK_SPACE) {            
            try {
                giveMenuOptions();
                Point loc = new Point(
                        pane.modelToView(pane.getCaretPosition()).x,
                        pane.modelToView(pane.getCaretPosition()).y);                  
                menu.show(pane, loc.x, loc.y);
            } catch (BadLocationException ex) {
                Logger.getLogger(AutocompletionController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private String getWordAtCursor() throws BadLocationException {
        int wordAtCursorStartPos = JTextPaneToolbox.getWordBeginningAtCursor(pane);
        String wordAtCursor = 
            pane.getStyledDocument().getText(
                wordAtCursorStartPos, pane.getCaretPosition() - wordAtCursorStartPos);
        return wordAtCursor;
    }

    private void giveMenuOptions() {
        String opts[] = {"first", "secound", "third"};
        menu.showUserOptions(opts);
    }

    void choseCompletion(String chosen) {
        for(AutocompletionOption opt : completionOptions) {
            if(opt.getInsertString().equals(chosen)) {   
                opt.insertInto(pane, pane.getCaretPosition(), insertToCode);                
                return;                
            }
        }
    }
}
