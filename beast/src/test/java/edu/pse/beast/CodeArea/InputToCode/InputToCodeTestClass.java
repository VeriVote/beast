/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.CodeArea.InputToCode;

import edu.pse.beast.codearea.InputToCode.CodeInputHandler;
import edu.pse.beast.codearea.InputToCode.OpenCloseCharList;
import edu.pse.beast.codearea.InputToCode.ShortcutHandler;
import edu.pse.beast.codearea.InputToCode.UserInputHandler;
import edu.pse.beast.codearea.InputToCode.UserInsertToCode;
import java.awt.EventQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 *
 * @author Holger-Desktop
 */
public class InputToCodeTestClass {
    
    public static void main(String[] args) {
        JTextPaneTestFrame frame = new JTextPaneTestFrame();
        OpenCloseCharList occL = new OpenCloseCharList();        
        UserInsertToCode insertToCode = new UserInsertToCode(frame.getTextPane(), occL);
        UserInputHandler inputHandler = new UserInputHandler(frame.getTextPane(), 
                new CodeInputHandler(insertToCode),
                new ShortcutHandler());
        WindowStarter starter = new WindowStarter(frame);
        starter.show();        
    }  
    
}
