package edu.pse.beast.codeareajavafx;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.IntFunction;

import org.fxmisc.richtext.LineNumberFactory;

import edu.pse.beast.codearea.errorhandling.CodeError;
import edu.pse.beast.datatypes.electiondescription.ElectionDescriptionChangeListener;
import edu.pse.beast.datatypes.propertydescription.FormalPropertiesDescription;
import edu.pse.beast.highlevel.javafx.GUIController;
import edu.pse.beast.highlevel.javafx.MenuBarInterface;
import edu.pse.beast.types.InputType;
import edu.pse.beast.types.OutputType;
import javafx.scene.Node;

public class BoundedVarCodeArea extends AutoCompletionCodeArea implements MenuBarInterface {
    private List<ElectionDescriptionChangeListener> listeners = new ArrayList<ElectionDescriptionChangeListener>();
    private FormalPropertiesDescription description;

    public BoundedVarCodeArea() {
        List<String> code = new ArrayList<String>();
        code.add("");
        String sampleCode = "";
        String stylesheet = this.getClass().getResource("codeAreaSyntaxHighlight.css").toExternalForm();
        this.getStylesheets().add(stylesheet);
        IntFunction<Node> lineNumbers = LineNumberFactory.get(this);
        this.setParagraphGraphicFactory(lineNumbers);
        this.replaceText(0, 0, sampleCode);
    }

    public void displayErrors(List<CodeError> codeErrors) {
        String toDisplay = "";
        for (Iterator<CodeError> iterator = codeErrors.iterator(); iterator.hasNext();) {
            CodeError codeError = (CodeError) iterator.next();
            toDisplay = toDisplay + "line: " + codeError.getLine() + "| Message: " + codeError.getMsg() + "\n";
        }

        GUIController.setErrorText(toDisplay);
    }

    public void createNew(InputType newIn, OutputType newOut) {
        for (Iterator<ElectionDescriptionChangeListener> iterator = listeners.iterator(); iterator.hasNext();) {
            ElectionDescriptionChangeListener listener = (ElectionDescriptionChangeListener) iterator.next();
            listener.inputChanged(newIn);
            listener.outputChanged(newOut);
        }
    }

    public void addListener(ElectionDescriptionChangeListener listener) {
        listeners.add(listener);
    }

    /**
     * sets the description for this property code are (either pre or post prop
     * description)
     *
     * @param description
     */
    public void setDescription(FormalPropertiesDescription description) {
        saveDescription(description);
        this.description = description;
        this.replaceText(0, this.getLength(), description.getCode());
    }

    public void saveDescription(FormalPropertiesDescription newDescription) {
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
    public void insertAutoCompletion(int start, int end, String toInsert) {
        replaceText(start, end, toInsert);
    }
}