/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.celectiondescriptioneditor;

import edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea.CElectionCodeArea;
import edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea.CElectionCodeAreaBuilder;
import edu.pse.beast.celectiondescriptioneditor.GUI.CCodeEditorGUI;
import edu.pse.beast.datatypes.descofvoting.ElectionDescription;
import edu.pse.beast.highlevel.ElectionDescriptionSource;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;


/**
 *  
 * @author Holger Klein
 */
public class CElectionDescriptionEditor implements ElectionDescriptionSource{
    private CElectionCodeArea codeArea;
    private ElectionDescription currentDescription;
    private CCodeEditorGUI gui;
    private CElectionCodeAreaBuilder builder;
    public CElectionDescriptionEditor(
            CElectionCodeArea codeArea,
            CCodeEditorGUI gui,            
            CElectionCodeAreaBuilder builder) {
        this.codeArea = codeArea;
        this.gui = gui;
        this.builder = builder;
    }

    @Override
    public ElectionDescription getElectionDescription() {
        updateCurrentDescription();
        return currentDescription;
    }

    private void updateCurrentDescription() {
        
    }

    @Override
    public boolean isCorrect() {
        return true;
    }

    @Override
    public void stopReacting() {
        
    }

    @Override
    public void resumeReacting() {
        
    }

    public void letUserEditElectionDescription(ElectionDescription description) throws BadLocationException {    
        gui.setNewCodeArea();
        codeArea = builder.createCElectionCodeArea(gui.getCodeArea(), gui.getCodeAreaScrollPane());
        
        codeArea.letUserEditCode(description.getCode());
        codeArea.lockLine(description.getVotingDeclLine());     
        codeArea.lockLine(description.getCode().size() - 1);
        this.currentDescription = description;        
    }

}
