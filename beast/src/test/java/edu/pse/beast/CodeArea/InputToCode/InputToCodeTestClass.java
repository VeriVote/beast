/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.CodeArea.InputToCode;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.text.BadLocationException;

import edu.pse.beast.codearea.SaveTextBeforeRemove;
import edu.pse.beast.codearea.Actionlist.Actionlist;
import edu.pse.beast.codearea.InputToCode.CodeInputHandler;
import edu.pse.beast.codearea.InputToCode.OpenCloseCharList;
import edu.pse.beast.codearea.InputToCode.ShortcutHandler;
import edu.pse.beast.codearea.InputToCode.UserInputHandler;
import edu.pse.beast.codearea.InputToCode.UserInsertToCode;
/**
 *
 * @author Holger-Desktop
 */
public class InputToCodeTestClass {
    
    public static void main(String[] args) {
        try {
            JTextPaneTestFrame frame = new JTextPaneTestFrame();
            OpenCloseCharList occL = new OpenCloseCharList();
            frame.getTextPane().getStyledDocument().insertString(0, "locked", null);
            UserInsertToCode insertToCode = new UserInsertToCode(frame.getTextPane(), occL, 
                    new SaveTextBeforeRemove(frame.getTextPane(), new Actionlist()));
            
            insertToCode.lockLine(0);
            UserInputHandler inputHandler = new UserInputHandler(frame.getTextPane(),
                    new CodeInputHandler(insertToCode),
                    new ShortcutHandler());
            WindowStarter starter = new WindowStarter(frame);        
            starter.show();
        } catch (BadLocationException ex) {
            Logger.getLogger(InputToCodeTestClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
    
}
