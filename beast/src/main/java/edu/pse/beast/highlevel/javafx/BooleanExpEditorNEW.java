package edu.pse.beast.highlevel.javafx;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.JsonSyntaxException;

import edu.pse.beast.booleanexpeditor.booleanexpcodearea.errorfinder.BooleanExpEditorGeneralErrorFinder;
import edu.pse.beast.codeareajavafx.BoundedVarCodeArea;
import edu.pse.beast.codeareajavafx.NewPropertyCodeArea;
import edu.pse.beast.codeareajavafx.SaverLoader;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariableList;
import edu.pse.beast.saverloader.PropertyDescriptionSaverLoader;
import edu.pse.beast.toolbox.CCodeHelper;
import edu.pse.beast.types.InternalTypeContainer;
import edu.pse.beast.types.InternalTypeRep;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

public class BooleanExpEditorNEW implements MenuBarInterface {
    private NewPropertyCodeArea preArea;
    private NewPropertyCodeArea postArea;

    private BoundedVarCodeArea boundedVarArea;

    private PreAndPostConditionsDescription currentPropertyDescription;
    private ParentTreeItem currentItem;

    private final SaverLoader saverLoader;

    private final PropertyDescriptionSaverLoader propSaverLoader =
            new PropertyDescriptionSaverLoader();

    private final ObservableList<TreeItem<String>> symbolicVoterVariableList;
    private final ObservableList<TreeItem<String>> symbolicCandidateVariableList;
    private final ObservableList<TreeItem<String>> symbolicSeatVariableList;
    private NewPropertyCodeArea focusedPane;

    public BooleanExpEditorNEW(final NewPropertyCodeArea prePropertyArea,
                               final NewPropertyCodeArea postPropertyArea,
                               final BoundedVarCodeArea boundedVarCodeArea,
                               final PreAndPostConditionsDescription propDesc,
                               final ParentTreeItem currentTreeItem) {
        prePropertyArea.setParent(this);
        postPropertyArea.setParent(this);
        this.saverLoader = new SaverLoader(".prop", "BEAST property description", this);
        this.preArea = prePropertyArea;
        this.postArea = postPropertyArea;
        this.boundedVarArea = boundedVarCodeArea;
        this.currentPropertyDescription = propDesc;
        this.currentItem = currentTreeItem;
        this.symbolicVoterVariableList =
                GUIController.getController().getVoterTreeItems().getChildren();
        this.symbolicCandidateVariableList =
                GUIController.getController().getCandidateTreeItems().getChildren();
        this.symbolicSeatVariableList =
                GUIController.getController().getSeatTreeItems().getChildren();
    }

    public boolean containsVarName(final String name) {
        return getSymbolicVariableNames().stream().anyMatch(str -> str.equals(name));
    }

    public synchronized String addSymbVar(final InternalTypeContainer container,
                                          final String toAdd,
                                          final boolean fromExisting) {
        if (toAdd.equals("")) {
            return "";
        }

        final String addedString = toAdd.strip();
        if (CCodeHelper.isValidCName(addedString)) {
            GUIController.getController().getVariableNameField().setText("");
            adding: if ((!containsVarName(addedString))) {
                // if (!fromExisting) {
                TreeItem<String> newItem = new TreeItem<String>(addedString);
                switch (container.getInternalType()) {
                case VOTER:
                    symbolicVoterVariableList.add(newItem);
                    break;
                case CANDIDATE:
                    symbolicCandidateVariableList.add(newItem);
                    break;
                case SEAT:
                    symbolicSeatVariableList.add(newItem);
                    break;
                default:
                    break adding;
                }
            } // else {
            // currentPropertyDescription.getSymbolicVariablesAsList()
            // .add(new SymbolicVariable(toAdd, container));
            // }
            // }
        }
        return addedString;
    }

    public void removeVariable(final String name) {
        List<SymbolicVariable> toRemove = new LinkedList<SymbolicVariable>();
        for (SymbolicVariable variable : getAllSymbolicVariables().getSymbolicVariables()) {
            if (variable.getId().equals(name)) {
                switch (variable.getInternalTypeContainer().getInternalType()) {
                case VOTER:
                    for (Iterator<TreeItem<String>> treeItemIterator = symbolicVoterVariableList
                            .iterator(); treeItemIterator.hasNext();) {
                        TreeItem<String> item = (TreeItem<String>) treeItemIterator.next();
                        if (item.getValue().equals(name)) {
                            treeItemIterator.remove();
                        }
                    }
                    break;
                case CANDIDATE:
                    for (Iterator<TreeItem<String>> treeItemIterator = symbolicCandidateVariableList
                            .iterator(); treeItemIterator.hasNext();) {
                        TreeItem<String> item = (TreeItem<String>) treeItemIterator.next();
                        if (item.getValue().equals(name)) {
                            treeItemIterator.remove();
                        }
                    }
                    break;
                case SEAT:
                    for (Iterator<TreeItem<String>> treeItemIterator = symbolicSeatVariableList
                            .iterator(); treeItemIterator.hasNext();) {
                        TreeItem<String> item = (TreeItem<String>) treeItemIterator.next();
                        if (item.getValue().equals(name)) {
                            treeItemIterator.remove();
                        }
                    }
                    break;
                default:
                    break;
                }
                toRemove.add(variable);
            }
        }
        getAllSymbolicVariables().getSymbolicVariables().removeAll(toRemove);
    }

    public void removeAllVariables() {
        symbolicVoterVariableList.clear();
        symbolicCandidateVariableList.clear();
        symbolicSeatVariableList.clear();
    }

    public NewPropertyCodeArea getPrePropertyArea() {
        return preArea;
    }

    public NewPropertyCodeArea getPostPropertyArea() {
        return postArea;
    }

    public PreAndPostConditionsDescription getPropertyDescription() {
        return currentPropertyDescription;
    }

    public void savePropertyTextAreasIntoDescription() {
        currentPropertyDescription.getPreConditionsDescription().setCode(preArea.getText());
        currentPropertyDescription.getPostConditionsDescription().setCode(postArea.getText());
        currentPropertyDescription.getBoundedVarDescription().setCode(boundedVarArea.getText());
        currentPropertyDescription.getSymbolicVariablesAsList().clear();
        currentPropertyDescription.getSymVarList().clearList();
        currentPropertyDescription.getSymVarList()
                .addSymbolicVariableList(getAllSymbolicVariables());
    }

    public void updatePropertyTextAreas(final PreAndPostConditionsDescription description) {
        preArea.setDescription(description.getPreConditionsDescription());
        postArea.setDescription(description.getPostConditionsDescription());
        boundedVarArea.setDescription(description.getBoundedVarDescription());
    }

    public synchronized void setCurrentPropertyDescription(final ParentTreeItem propertyItem,
                                                           final boolean bringToFront) {
        savePropertyTextAreasIntoDescription();
        this.currentPropertyDescription = propertyItem.getPreAndPostProperties();
        this.currentItem = propertyItem;
        this.removeAllVariables();
        updatePropertyTextAreas(currentPropertyDescription);

        for (SymbolicVariable variable
                : currentPropertyDescription.getSymbolicVariablesAsList()) {
            this.addSymbVar(variable.getInternalTypeContainer(), variable.getId(), true);
        }
        if (bringToFront) {
            bringToFront();
        }
        saverLoader.resetHasSaveFile();
    }

    public void bringToFront() {
        GUIController.getController().getMainTabPane().getSelectionModel()
                .select(GUIController.getController().getPropertyTab());
        if (currentItem != null) {
            // checks if there are errors, and if there are,
            // it displays them in the error tab
            BooleanExpEditorGeneralErrorFinder.hasErrors(currentItem);
        }
    }

    public ParentTreeItem getCurrentItem() {
        return currentItem;
    }
    //
    // private void saveDescription() {
    // preArea.saveDescription(null);
    // postArea.saveDescription(null);
    // }

    /**
     * Clears all fields of the editor.
     */
    public void clear() {
        this.removeAllVariables();
        this.preArea.clear();
        this.postArea.clear();
    }

    private PreAndPostConditionsDescription convert(final String json) {
        PreAndPostConditionsDescription newDescription = null;
        if (!json.equals("")) {
            try {
                newDescription = propSaverLoader.createFromSaveString(json);
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }
            return newDescription;
        }
        return null;
    }

    private SymbolicVariableList getAllSymbolicVariables() {
        SymbolicVariableList toReturn = new SymbolicVariableList();
        for (TreeItem<String> voter : symbolicVoterVariableList) {
            toReturn.addSymbolicVariable(voter.getValue(),
                                         new InternalTypeContainer(InternalTypeRep.VOTER));
        }

        for (TreeItem<String> candidate : symbolicCandidateVariableList) {
            toReturn.addSymbolicVariable(candidate.getValue(),
                                         new InternalTypeContainer(InternalTypeRep.CANDIDATE));
        }
        for (TreeItem<String> seat : symbolicSeatVariableList) {
            toReturn.addSymbolicVariable(seat.getValue(),
                                         new InternalTypeContainer(InternalTypeRep.SEAT));
        }
        return toReturn;
    }

    public PreAndPostConditionsDescription open(final File file) {
        return convert(saverLoader.load(file));
    }

    @Override
    public void open() {
        PreAndPostConditionsDescription newDescription = convert(saverLoader.load());
        if (newDescription != null) {
            GUIController.getController().addProperty(newDescription);
            currentPropertyDescription = newDescription;
            saverLoader.resetHasSaveFile();
            preArea.replaceText(newDescription.getPreConditionsDescription().getCode());
            postArea.replaceText(newDescription.getPostConditionsDescription().getCode());
            GUIController.getController().setPropNameField(newDescription.getName());
            bringToFront();
        }
    }

    @Override
    public void save() {
        savePropertyTextAreasIntoDescription();
        String json = propSaverLoader.createSaveString(currentPropertyDescription);
        saverLoader.save("", json);
    }

    @Override
    public void saveAs() {
        savePropertyTextAreasIntoDescription();
        String json = propSaverLoader.createSaveString(currentPropertyDescription);
        saverLoader.saveAs("", json);
    }

    public void saveAs(final PreAndPostConditionsDescription description,
                       final File file) {
        savePropertyTextAreasIntoDescription();
        String json = propSaverLoader.createSaveString(description);
        saverLoader.saveAs(file, json);
    }

    @Override
    public void undo() {
        if (focusedPane != null) {
            focusedPane.undo();
        }
    }

    @Override
    public void redo() {
        if (focusedPane != null) {
            focusedPane.redo();
        }
    }

    @Override
    public void cut() {
        if (focusedPane != null) {
            focusedPane.cut();
        }
    }

    @Override
    public void copy() {
        if (focusedPane != null) {
            focusedPane.copy();
        }
    }

    @Override
    public void paste() {
        if (focusedPane != null) {
            focusedPane.paste();
        }
    }

    @Override
    public void delete() {
        if (focusedPane != null) {
            focusedPane.delete();
        }
    }

    @Override
    public void autoComplete() {

        if (preArea.focusedProperty().get()) {
            preArea.autoComplete();
        } else if (postArea.focusedProperty().get()) {
            postArea.autoComplete();
        }
    }

    public List<String> getSymbolicVariableNames() {
        List<String> toReturn = new ArrayList<String>();
        for (TreeItem<String> item : symbolicVoterVariableList) {
            toReturn.add(item.getValue());
        }
        for (TreeItem<String> item : symbolicCandidateVariableList) {
            toReturn.add(item.getValue());
        }
        for (TreeItem<String> item : symbolicSeatVariableList) {
            toReturn.add(item.getValue());
        }
        return toReturn;
    }

    public void setFocused(final NewPropertyCodeArea pane) {
        this.focusedPane = pane;
    }
}
