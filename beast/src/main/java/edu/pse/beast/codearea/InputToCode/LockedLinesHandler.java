/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea.InputToCode;

import edu.pse.beast.toolbox.SortedIntegerList;
import java.util.ArrayList;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.StyledDocument;

/**
 *
 * @author Holger-Desktop
 */
public class LockedLinesHandler implements DocumentListener {
    
    private SortedIntegerList lockedLines = new SortedIntegerList();
    private LineHandler lineHandler;
    
    public LockedLinesHandler(StyledDocument doc, LineHandler lineHandler) {
        doc.addDocumentListener(this);
        this.lineHandler = lineHandler;
    }

    public void lockLine(int line) {
        lockedLines.add(line);
    }
    
    public void unlockLine(int line) {
        lockedLines.remove(line);
    }
    
    public boolean isLineLocked(int line) {
        return lockedLines.contains(line);
    }
    
    @Override
    public void insertUpdate(DocumentEvent de) {   
    }
    
    @Override
    public void removeUpdate(DocumentEvent de) {
    
    }

    @Override
    public void changedUpdate(DocumentEvent de) {            
    }
}
