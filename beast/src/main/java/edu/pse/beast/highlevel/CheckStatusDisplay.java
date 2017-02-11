package edu.pse.beast.highlevel;

/**
 * Interface for displaying the Status of the ongoing Analysis.
 * Currently implemented by ParameterEditorWindow.
 * @author NikolaiLMS
 */
public interface CheckStatusDisplay {

    /**
     * Method that displays the given Ressources/String
     * @param stringIdForResources the Id the main message
     * @param showLoader
     * @param additionalText
     */
    void displayText(String stringIdForResources, boolean showLoader, String additionalText);
}
