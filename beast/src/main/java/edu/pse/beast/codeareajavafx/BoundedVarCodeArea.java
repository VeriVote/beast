package edu.pse.beast.codeareajavafx;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.IntFunction;

import org.fxmisc.richtext.LineNumberFactory;

import edu.pse.beast.codearea.errorhandling.CodeError;
import edu.pse.beast.datatypes.propertydescription.FormalPropertiesDescription;
import edu.pse.beast.highlevel.javafx.GUIController;
import edu.pse.beast.highlevel.javafx.MenuBarInterface;
import javafx.scene.Node;

/**
 * The Class BoundedVarCodeArea.
 *
 * @author Lukas Stapelbroek
 */
public final class BoundedVarCodeArea extends AutoCompletionCodeArea
        implements MenuBarInterface {

    /** The Constant RESOURCE. */
    private static final String RESOURCE = "codeAreaSyntaxHighlight.css";

    /** The description. */
    private FormalPropertiesDescription description;

    /**
     * The constructor.
     */
    public BoundedVarCodeArea() {
        List<String> code = new ArrayList<String>();
        code.add("");
        String sampleCode = "";
        String stylesheet =
                this.getClass().getResource(RESOURCE).toExternalForm();
        this.getStylesheets().add(stylesheet);
        IntFunction<Node> lineNumbers = LineNumberFactory.get(this);
        this.setParagraphGraphicFactory(lineNumbers);
        this.replaceText(0, 0, sampleCode);
    }

    /**
     * Display errors.
     *
     * @param codeErrors
     *            the code errors
     */
    public void displayErrors(final List<CodeError> codeErrors) {
        String toDisplay = "";
        for (Iterator<CodeError> iterator = codeErrors.iterator();
                iterator.hasNext();) {
            CodeError codeError = (CodeError) iterator.next();
            toDisplay += "line: " + codeError.getLine()
                        + "| Message: " + codeError.getMsg() + "\n";
        }
        GUIController.setErrorText(toDisplay);
    }

    /**
     * Sets the description for this property code area (either pre or post prop
     * description).
     *
     * @param descr
     *            the description
     */
    public void setDescription(final FormalPropertiesDescription descr) {
        saveDescription(descr);
        this.description = descr;
        this.replaceText(0, this.getLength(), descr.getCode());
    }

    /**
     * Save description.
     *
     * @param newDescription
     *            the new description
     */
    public void saveDescription(final FormalPropertiesDescription newDescription) {
        if (this.description != null) {
            this.description.setCode(this.textProperty().getValue());
        }
    }

    @Override
    public void undo() {
        // TODO Auto-generated method stub
    }

    @Override
    public void redo() {
        // TODO Auto-generated method stub
    }

    @Override
    public void cut() {
        // TODO Auto-generated method stub
    }

    @Override
    public void copy() {
        // TODO Auto-generated method stub
    }

    @Override
    public void paste() {
        // TODO Auto-generated method stub
    }

    @Override
    public void delete() {
        // TODO Auto-generated method stub
    }

    @Override
    public void open() {
        // TODO Auto-generated method stub
    }

    @Override
    public void save() {
        // TODO Auto-generated method stub
    }

    @Override
    public void saveAs() {
        // TODO Auto-generated method stub
    }

    @Override
    public void autoComplete() {
        System.out.println("wird das hier gebraucht?");
    }

    @Override
    public void insertAutoCompletion(final int start, final int end,
                                     final String toInsert) {
        replaceText(start, end, toInsert);
    }
}
