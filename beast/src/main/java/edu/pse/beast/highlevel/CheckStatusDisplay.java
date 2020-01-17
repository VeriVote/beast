package edu.pse.beast.highlevel;

/**
 * Interface for displaying the Status of the ongoing Analysis. Currently
 * implemented by ParameterEditorWindow.
 *
 * @author Nikolai Schnell
 */
public interface CheckStatusDisplay {

    /**
     * Method that displays the given resources/String.
     *
     * @param stringIdForResources
     *            the Id of the main message that can be retrieved from a
     *            StringResourceLoader
     * @param showLoader
     *            true if a loader animation should be displayed, false
     *            otherwise
     * @param additionalText
     *            additional text to be placed after the main message that is
     *            not loaded from a StringResourceLoader (e.g. elapsed time)
     */
    void displayText(String stringIdForResources, boolean showLoader,
                     String additionalText);

    /**
     * Signals that a repaint should be done.
     */
    void signalThatAnalysisEnded();
}
