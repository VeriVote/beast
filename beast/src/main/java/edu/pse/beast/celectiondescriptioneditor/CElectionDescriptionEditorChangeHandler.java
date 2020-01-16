package edu.pse.beast.celectiondescriptioneditor;

import javax.swing.JTextPane;

/**
 * Class for checking whether the loaded CElectionDescription object has been
 * modified since it was loaded.
 *
 * @author Nikolai Schnell
 */
public class CElectionDescriptionEditorChangeHandler {

    /** The pre string. */
    private String preString = "";

    /** The code area. */
    private JTextPane codeArea;

    /**
     * Constructor.
     *
     * @param cArea JTextPane of the CElectionEditorCodeArea
     */
    public CElectionDescriptionEditorChangeHandler(final JTextPane cArea) {
        this.codeArea = cArea;
        updatePreValue();
    }

    /**
     * Adds a new text pane.
     *
     * @param cArea the text pane to add
     */
    public void addNewTextPane(final JTextPane cArea) {
        this.codeArea = cArea;
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
     * Checks for changed.
     *
     * @return true if the currently loaded CElectionDescription object differs from
     *         what is currently displayed in CElectionEditorWindow, false otherwise
     */
    public boolean hasChanged() {
        return !(preString.equals(codeArea.getText()));
    }
}
