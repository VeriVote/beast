package edu.pse.beast.highlevel;

/**
 * Interface for displaying the Status of the ongoing Analysis.
 * Currently implemented by ParameterEditorWindow.
 * @author NikolaiLMS
 */
public interface CheckStatusDisplay {

    /**
     * Method that displays the given Ressources/String
     * @param stringIdForResources the Id of the main message that can be retrieved from a StringResourceLoader
     * @param showLoader true if a loader animation should be displayed, false otherwise
     * @param additionalText additional text to be placed after the main message that isn't loaded from a
     *                       StringResourceLoader (e.g. elapsed time)
     */
    void displayText(String stringIdForResources, boolean showLoader, String additionalText);
    /**
     * signals that a repaint should be done
     */
    void signalThatAnalysisEnded();
}
