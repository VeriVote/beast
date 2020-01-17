package edu.pse.beast.booleanexpeditor.booleanexpcodearea.errorfinder;

import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.booleanexpeditor.booleanexpcodearea.BooleanExpANTLRHandler;
import edu.pse.beast.codearea.errorhandling.CodeError;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.highlevel.javafx.GUIController;
import edu.pse.beast.highlevel.javafx.ParentTreeItem;

/**
 * The Class BooleanExpEditorGeneralErrorFinder.
 *
 * @author Lukas Stapelbroek
 */
public final class BooleanExpEditorGeneralErrorFinder {
    /**
     * Instantiates a new boolean exp editor general error finder.
     */
    private BooleanExpEditorGeneralErrorFinder() { }

    /**
     * Checks for errors.
     *
     * @param parentTreeItem
     *            parent tree item
     * @return true, if there are errors, else false
     */
    public static boolean hasErrors(final ParentTreeItem parentTreeItem) {
        List<CodeError> combinedErrors = getErrors(parentTreeItem);
        if (combinedErrors.size() > 0) {
            parentTreeItem.addErrors(combinedErrors);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gets the errors.
     *
     * @param parentTreeItem
     *            the parent tree item
     * @return the errors
     */
    public static List<CodeError> getErrors(final ParentTreeItem parentTreeItem) {
        List<CodeError> combinedErrors = new ArrayList<CodeError>();
        final PreAndPostConditionsDescription property =
                parentTreeItem.getPreAndPostProperties();
        // precondition error finder
        final BooleanExpANTLRHandler preAntlrHandler = new BooleanExpANTLRHandler(
                property.getPreConditionsDescription().getCode());
        combinedErrors.addAll(BooleanExpEditorGrammarErrorFinder.getErrors(preAntlrHandler));
        combinedErrors.addAll(
            BooleanExpEditorVariableErrorFinder.getErrors(
                    preAntlrHandler,
                    property.getSymVarList(),
                    GUIController.getController().getCodeArea()
            )
        );

        // postcondition error finder
        final BooleanExpANTLRHandler postAntlrHandler = new BooleanExpANTLRHandler(
                property.getPostConditionsDescription().getCode());
        combinedErrors.addAll(BooleanExpEditorGrammarErrorFinder.getErrors(postAntlrHandler));
        combinedErrors.addAll(
            BooleanExpEditorVariableErrorFinder.getErrors(
                    postAntlrHandler,
                    property.getSymVarList(),
                    GUIController.getController().getCodeArea()
            )
        );
        // bounded variable error finder
        return combinedErrors;
    }
}
