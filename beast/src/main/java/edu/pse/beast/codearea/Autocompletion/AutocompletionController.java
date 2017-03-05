/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea.Autocompletion;

import edu.pse.beast.codearea.InputToCode.JTextPaneToolbox;
import edu.pse.beast.codearea.InputToCode.UserInsertToCode;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class communicates user input with the autcompletion functionallity.
 * If the user presse ctrl + space, it asks the AutocompletionFrame
 * to display the given autocompletion positibillities. It also continues
 * to route keyevents from the frame to all keylisteners of the given JTextPane
 * so that the user can continue typing code while autocompletion is displayed.
 * It uses the FindWordsConcurrently class to concurrently search the code
 * for words not yet contained in the autocompletion possibillities. The reasoning
 * is that the user, expecially when writing code, might wanna retype the
 * same thing many times
 * @author Holger-Desktop
 */
public class AutocompletionController implements KeyListener, AncestorListener, MouseListener {
    private final JTextPane pane;
    private final ArrayList<AutocompletionOption> completionOptions = new ArrayList<>();
    private final FindWordsConcurrently conc;
    private final Thread t;
    private final AutocompletionFrame frame;
    private final UserInsertToCode insertToCode;    
    
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
        frame.getjList1().addMouseListener(this);
        
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        
    }
    
    public void stopThread() {
        conc.stop();
    }
    
     public void addAutocompletionString(String s) {
        for(AutocompletionOption opt : completionOptions) {
            if(opt.getInsertString().equals(s)) return;        
        }
        completionOptions.add(new AutocompletionOption(s, s));
    }

    @Override
    public void keyTyped(KeyEvent ke) {             
       if(ke.getSource() == frame.getjList1()) {
            if(ke.getKeyCode() == KeyEvent.VK_UP ||
                ke.getKeyCode() == KeyEvent.VK_DOWN ||
                ke.getKeyCode() == KeyEvent.VK_ENTER ||
                ke.getKeyCode() == KeyEvent.VK_CONTROL) {                
                return;
            } else {
                int selection = frame.getjList1().getSelectedIndex();
                for(int i = 0; i < pane.getKeyListeners().length; ++i) {
                if(pane.getKeyListeners()[i] != this) 
                        pane.getKeyListeners()[i].keyTyped(ke);
                }
                giveMenuOptions();
                ke.consume();
                frame.getjList1().setSelectedIndex(selection);
            }            
            
       }
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        if(ke.getSource() == frame.getjList1()) {
            if(ke.getKeyCode() == KeyEvent.VK_UP ||
                ke.getKeyCode() == KeyEvent.VK_DOWN) {                
                return;
            } else if(ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
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
        if(ke.getSource() == frame.getjList1()) {
            if(ke.getKeyCode() == KeyEvent.VK_UP ||
                ke.getKeyCode() == KeyEvent.VK_DOWN ||
                ke.getKeyCode() == KeyEvent.VK_ENTER || 
                ke.getKeyCode() == KeyEvent.VK_CONTROL || 
                ke.getKeyCode() == KeyEvent.VK_SPACE) {                
                return;
            } 
        }
        if(ke.isControlDown() && ke.getKeyChar()== KeyEvent.VK_SPACE) {            
            try {
                giveMenuOptions();
                Point loc = new Point(
                        pane.modelToView(pane.getCaretPosition()).x,
                        pane.modelToView(pane.getCaretPosition()).y);                  
                frame.setLocation(pane.getLocationOnScreen().x + loc.x, pane.getLocationOnScreen().y + loc.y + 20);
                frame.setVisible(true);
                frame.getjList1().setSelectedIndex(0);
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
        try {
            String word = getWordAtCursor().trim();
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

     public void choseCompletion(String chosen) {
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

    @Override
    public void mouseClicked(MouseEvent me) {
        String s = frame.getjList1().getSelectedValue();
        if(s != null) choseCompletion(s);
        frame.setVisible(false);
    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }
}
