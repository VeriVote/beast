/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pse.beast.codearea.InputToCode;

import java.util.ArrayList;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.StyledDocument;

/**
 *
 * @author Holger-Desktop
 */
public class LockedLinesHandler implements DocumentListener {
    
    private ArrayList<Integer> lockedLines = new ArrayList();
   
    public LockedLinesHandler(StyledDocument doc) {
        doc.addDocumentListener(this);
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
