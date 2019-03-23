//package edu.pse.beast.codearea.codeinput;
//
//import java.awt.event.KeyEvent;
//import java.awt.event.KeyListener;
//
//import javax.swing.JTextPane;
//
///**
// * This class takes input from the user and forwards it to either 
// * CodeInputHandler (if it is code to insert into the pane) or Shortcuthandler 
// * (if left ctrl is down). 
// * If the input is best handled by the JTextPane, however, such as the arrow keys,
// * it simply ignores it.
// * @author Holger-Desktop
// */
//public class UserInputHandler implements KeyListener {
//    private JTextPane pane;
//    private CodeInputHandler codeInputHandler;
//    private ShortcutHandler shortcutHandler;
//    private boolean del = false;
//    private boolean sc = false;
//    
//    public UserInputHandler(JTextPane pane, 
//            CodeInputHandler codeInputHandler, 
//            ShortcutHandler shortcutHandler) {
//        this.pane = pane;
//        this.codeInputHandler = codeInputHandler;
//        this.shortcutHandler = shortcutHandler;
//        this.pane.addKeyListener(this);
//    }
//
//    @Override
//    public void keyTyped(KeyEvent ke) {    
//        if(letTextPaneHandleKey(ke)) {
//            return;
//        } else if(isShortcut(ke)) {
//            ke.consume();
//        } else {
//            codeInputHandler.handleCodeKey(ke);   
//            ke.consume();         
//        }
//    }
//
//    @Override
//    public void keyPressed(KeyEvent ke) {  
//        if(letTextPaneHandleKey(ke)) {
//            return;
//        } else if(isShortcut(ke)) {
//            try {
//                shortcutHandler.handleKey(ke); 
//            } catch(NullPointerException ex) {
//                //key is not mapped to any action
//            }  
//            sc = true;
//        }
//        ke.consume();
//    }
//
//    @Override
//    public void keyReleased(KeyEvent ke) {
//        if(letTextPaneHandleKey(ke)) {
//            return;
//        }  else if(sc) {
//            sc = false;
//        } else if(ke.getKeyCode() == KeyEvent.VK_DELETE) {
//            codeInputHandler.delete();
//        } else {
//            codeInputHandler.handleSpecialKey(ke);
//        }
//        ke.consume();
//    }
//    
//    private boolean isShortcut(KeyEvent ke) {
//        return ke.isControlDown();
//    }
//    
//    private boolean letTextPaneHandleKey(KeyEvent ke) {
//        return ke.getKeyCode() == KeyEvent.VK_LEFT ||
//                ke.getKeyCode() == KeyEvent.VK_RIGHT ||
//                ke.getKeyCode() == KeyEvent.VK_UP ||
//                ke.getKeyCode() == KeyEvent.VK_DOWN ||
//                ke.getKeyChar()== KeyEvent.VK_ESCAPE;                
//    }
//
//    public ShortcutHandler getShortcutHandler() {
//        return shortcutHandler;
//    }
//}
