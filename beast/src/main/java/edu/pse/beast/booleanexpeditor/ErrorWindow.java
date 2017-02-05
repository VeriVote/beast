package edu.pse.beast.booleanexpeditor;

import edu.pse.beast.booleanexpeditor.booleanExpCodeArea.errorFinder.BooleanExpErrorDisplayer;
import edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea.ErrorHandling.CErrorDisplayer;
import edu.pse.beast.codearea.ErrorHandling.CodeError;
import edu.pse.beast.stringresource.StringLoaderInterface;
import javax.swing.JTextPane;
import java.util.ArrayList;

/**
 * Controller of the JTextPane for displayng errors in a BooleanExpEditorWindow.
 * @author Nikolai
 */
public class ErrorWindow {
    private final JTextPane textPane;
    private String errorString;
    private String lineString;
    private String prePropsString;
    private String postPropsString;

    /**
     * Constructor
     * @param textPane JTextPane for ErrorWindow
     * @param stringLoaderInterface stringLoaderInterface to load needed Strings
     */
    ErrorWindow(JTextPane textPane, StringLoaderInterface stringLoaderInterface) {
        this.textPane = textPane;
        updateStringRes(stringLoaderInterface);
    }

    /**
     * Method to display ArrayList of Errors in the ErrorWindow.
     * @param prePropErrors An ArrayList<CodeError> instance with all the CodeErrors from the preProp textpane
     * @param postPropErrors An ArrayList<CodeError> instance with all the CodeErrors from the postProp textpane
     * @param booleanExpErrorDisplayer the ErrorDisplayer that creates the messages given CodeError objects
     */
    void displayErrors(ArrayList<CodeError> prePropErrors, ArrayList<CodeError> postPropErrors,
                       BooleanExpErrorDisplayer booleanExpErrorDisplayer) {
        int numberOfErrors = postPropErrors.size() + prePropErrors.size();
        String errorsAsString = errorString + ": " + numberOfErrors + "\n";

        for (int i = 0; i < prePropErrors.size(); i++) {
            errorsAsString += i+1 + ": " + booleanExpErrorDisplayer.createMsg(prePropErrors.get(i)) + " (" +
                    lineString + " " +prePropErrors.get(i).getLine() + ", " + prePropsString + ")" + "\n";
        }

        for (int i = 0; i < postPropErrors.size(); i++) {
            errorsAsString += i+1 + ": " + booleanExpErrorDisplayer.createMsg(postPropErrors.get(i)) + " (" +
                    lineString + " " + postPropErrors.get(i).getLine() + ", " + postPropsString + ")" + "\n";
        }


        textPane.setText(errorsAsString);
    }

    /**
     * Update the language dependent displayed Strings in this class.
     * @param stringLoaderInterface the new stringLoaderInterface
     */
    public void updateStringRes(StringLoaderInterface stringLoaderInterface) {
        errorString = stringLoaderInterface.getBooleanExpEditorStringResProvider().
                getBooleanExpErrorStringRes().getStringFromID("error");
        lineString = stringLoaderInterface.getBooleanExpEditorStringResProvider().
                getBooleanExpErrorStringRes().getStringFromID("line");
        prePropsString = stringLoaderInterface.getBooleanExpEditorStringResProvider().
                getBooleanExpErrorStringRes().getStringFromID("preProps");
        postPropsString = stringLoaderInterface.getBooleanExpEditorStringResProvider().
                getBooleanExpErrorStringRes().getStringFromID("postProps");
    }
}
