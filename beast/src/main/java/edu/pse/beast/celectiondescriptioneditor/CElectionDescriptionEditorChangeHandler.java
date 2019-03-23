package edu.pse.beast.celectiondescriptioneditor;

import javax.swing.JTextPane;

/**
 * Class for checking whether the loaded CElectionDescription object has been
 * modified since it was loaded.
 *
 * @author NikolaiLMS
 */
public class CElectionDescriptionEditorChangeHandler {
    private String preString = "";
    private JTextPane codeArea;

    /**
     * Constructor
     *
     * @param codeArea JTextPane of the CElectionEditorCodeArea
     */
    public CElectionDescriptionEditorChangeHandler(JTextPane codeArea) {
        this.codeArea = codeArea;
        updatePreValue();
    }

    /**
     * adds a new text pane
     *
     * @param codeArea the text pane to add
     */
    public void addNewTextPane(JTextPane codeArea) {
        this.codeArea = codeArea;
        updatePreValue();
    }

    /**
     * Updates the "pre" variables used for comparison. Called after a new
     * PreAndPostConditionsDescription object is loaded or saved.
     */
    public void updatePreValue() {
        preString = codeArea.getText();
    }

    /**
     * @return true if the currently loaded CElectionDescription object differs from
     *         what is currently displayed in CElectionEditorWindow, false otherwise
     */
    public boolean hasChanged() {
        return !(preString.equals(codeArea.getText()));
    }
}
