//package edu.pse.beast.codearea.useractions;
//
//import java.awt.Toolkit;
//import java.awt.datatransfer.Clipboard;
//import java.awt.datatransfer.DataFlavor;
//import java.awt.datatransfer.UnsupportedFlavorException;
//import java.io.IOException;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//import javax.swing.text.BadLocationException;
//
//import edu.pse.beast.codearea.CodeArea;
//import edu.pse.beast.toolbox.UserAction;
//
///**
// * this useraction asks the given codearea to insert the currently copied string
// * at the current position
// * @author Holger Klein
// */
//public class PasteUserAction extends UserAction {
//    private CodeArea codeArea;
//    private Clipboard clipboard;
//
//    public PasteUserAction(CodeArea area) {
//        super("paste");
//        this.codeArea = area;
//        this.clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
//    }
//
//    @Override
//    public void perform() {
//        try {
//            codeArea.getInsertToCode().getSaveBeforeRemove().save();
//        } catch (BadLocationException ex) {
//            Logger.getLogger(CutUserAction.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        try {
//            String clipboardString = (String) clipboard.getData(DataFlavor.stringFlavor);
//            codeArea.insertString(clipboardString);
//        } catch (UnsupportedFlavorException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
