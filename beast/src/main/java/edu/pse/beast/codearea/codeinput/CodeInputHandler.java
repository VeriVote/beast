//package edu.pse.beast.codearea.codeinput;
//
//import java.awt.event.KeyEvent;
//
//import javax.swing.text.BadLocationException;
//
///**
// * This class is used by the userinputhandler to pass user input to the 
// * right methods of userinserttocode
// * @author Holger-Desktop
// */
//public class CodeInputHandler {
//
//    private UserInsertToCode insertToCode;
//    
//    public CodeInputHandler(UserInsertToCode insertToCode) {
//        this.insertToCode = insertToCode;
//    }
//    
//    public void handleSpecialKey(KeyEvent ke) {
//        if(ke.getKeyCode() == KeyEvent.VK_END) {
//            insertToCode.moveToEndOfCurrentLine();
//        } else if(ke.getKeyCode() == KeyEvent.VK_HOME) {
//            insertToCode.moveToStartOfCurrentLine();
//        }  
//    }
//    
//    /**
//     * takes the given key event and passes it on to the right method in 
//     * userinserttocode. Ex: Enter key -> it calls enterNewline()
//     * @param ke the keyevent to be processed by userinserttocode
//     */
//    public void handleCodeKey(KeyEvent ke) {        
//        try{
//            if(ke.getKeyCode()== KeyEvent.VK_ENTER || ke.getKeyChar() == '\n') {
//                insertToCode.insertNewline();
//            } else if(ke.getKeyCode() == KeyEvent.VK_TAB || ke.getKeyChar() == '\t') {
//                if(ke.isShiftDown()) {
//                    insertToCode.removeTab();
//                } else {                    
//                    insertToCode.insertTab();
//                }
//            } else if(ke.getKeyCode() == KeyEvent.VK_BACK_SPACE || ke.getKeyChar() == '\b') {
//                insertToCode.removeToTheLeft();
//            } else if(ke.getKeyCode() == KeyEvent.VK_SPACE || ke.getKeyChar() != ''){            
//                insertToCode.insertChar(ke.getKeyChar());                
//            }
//        } catch(BadLocationException ex) {
//            ex.printStackTrace();
//        }        
//    }
//
//    void delete() {
//        insertToCode.removeToTheRight();
//    }
//}
