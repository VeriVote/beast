/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.CodeArea.InputToCode;

import com.pse.beast.codearea.InputToCode.CodeInputHandler;
import com.pse.beast.codearea.InputToCode.OpenCloseCharList;
import com.pse.beast.codearea.InputToCode.ShortcutHandler;
import com.pse.beast.codearea.InputToCode.UserInputHandler;
import com.pse.beast.codearea.InputToCode.UserInsertToCode;
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
    private JTextPaneTestFrame frame;
    private UserInsertToCode insertToCode;
    private OpenCloseCharList occL;
    private UserInputHandler inputHandler;
    
    public void setUp() {
        frame = new JTextPaneTestFrame();
        occL = new OpenCloseCharList();        
        insertToCode = new UserInsertToCode(frame.getTextPane(), occL);
        inputHandler = new UserInputHandler(frame.getTextPane(), 
                new CodeInputHandler(insertToCode),
                new ShortcutHandler());
    }   
    
    @Test
    public void testCodeAreaInteractive() {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JTextPaneTestFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JTextPaneTestFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JTextPaneTestFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JTextPaneTestFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
            
        
        frame.setVisible(true);        
    }
    
}
