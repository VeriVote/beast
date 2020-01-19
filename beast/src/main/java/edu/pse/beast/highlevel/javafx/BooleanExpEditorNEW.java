package edu.pse.beast.highlevel.javafx;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

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

/**
 * The Class BooleanExpEditorNEW.
 *
 * @author Lukas Stapelbroek
 */
public final class BooleanExpEditorNEW implements MenuBarInterface {
    /** The pre area. */
    private NewPropertyCodeArea preArea;

    /** The post area. */
    private NewPropertyCodeArea postArea;

    /** The bounded var area. */
    private BoundedVarCodeArea boundedVarArea;

    /** The current property description. */
    private PreAndPostConditionsDescription currentPropertyDescription;

    /** The current item. */
    private ParentTreeItem currentItem;

    /** The saver loader. */
    private final SaverLoader saverLoader;

    /** The prop saver loader. */
    private final PropertyDescriptionSaverLoader propSaverLoader =
            new PropertyDescriptionSaverLoader();

    /** The symbolic voter variable list. */
    private final ObservableList<TreeItem<String>> symbolicVoterVariableList;

    /** The symbolic candidate variable list. */
    private final ObservableList<TreeItem<String>> symbolicCandidateVariableList;

    /** The symbolic seat variable list. */
    private final ObservableList<TreeItem<String>> symbolicSeatVariableList;

    /** The focused pane. */
    private NewPropertyCodeArea focusedPane;

    /**
     * Instantiates a new boolean exp editor NEW.
     *
     * @param prePropertyArea
     *            the pre property area
     * @param postPropertyArea
     *            the post property area
     * @param boundedVarCodeArea
     *            the bounded var code area
     * @param propDesc
     *            the prop desc
     * @param currentTreeItem
     *            the current tree item
     */
    public BooleanExpEditorNEW(final NewPropertyCodeArea prePropertyArea,
                               final NewPropertyCodeArea postPropertyArea,
                               final BoundedVarCodeArea boundedVarCodeArea,
                               final PreAndPostConditionsDescription propDesc,
                               final ParentTreeItem currentTreeItem) {
        prePropertyArea.setParent(this);
        postPropertyArea.setParent(this);
        this.saverLoader =
                new SaverLoader(".prop",
                                "BEAST property description",
                                this);
        this.preArea = prePropertyArea;
        this.postArea = postPropertyArea;
        this.boundedVarArea = boundedVarCodeArea;
        this.currentPropertyDescription = propDesc;
        this.currentItem = currentTreeItem;
        this.symbolicVoterVariableList =
                GUIController.getController()
                .getVoterTreeItems().getChildren();
        this.symbolicCandidateVariableList =
                GUIController.getController()
                .getCandidateTreeItems().getChildren();
        this.symbolicSeatVariableList =
                GUIController.getController()
                .getSeatTreeItems().getChildren();
    }

    /**
     * Contains var name.
     *
     * @param name
     *            the name
     * @return true, if successful
     */
    public boolean containsVarName(final String name) {
        return getSymbolicVariableNames().stream()
                .anyMatch(str -> str.equals(name));
    }

    /**
     * Adds the symb var.
     *
     * @param container
     *            the container
     * @param toAdd
     *            the to add
     * @param fromExisting
     *            the from existing
     * @return the string
     */
    public synchronized String addSymbVar(final InternalTypeContainer container,
                                          final String toAdd,
                                          final boolean fromExisting) {
        if ("".equals(toAdd)) {
            return "";
        }

        final String addedString = toAdd.strip();
        if (CCodeHelper.isValidCName(addedString)) {
            GUIController.getController().getVariableNameField().setText("");
            adding: if (!containsVarName(addedString)) {
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

    /**
     * Removes the variable.
     *
     * @param name
     *            the name
     */
    public void removeVariable(final String name) {
        List<SymbolicVariable> toRemove = new LinkedList<SymbolicVariable>();
        for (SymbolicVariable variable
                : getAllSymbolicVariables().getSymbolicVariables()) {
            if (variable.getId().equals(name)) {
                switch (variable.getInternalTypeContainer().getInternalType()) {
                case VOTER:
                    for (Iterator<TreeItem<String>> treeItemIterator =
                        symbolicVoterVariableList.iterator();
                            treeItemIterator.hasNext();) {
                        TreeItem<String> item = treeItemIterator.next();
                        if (item.getValue().equals(name)) {
                            treeItemIterator.remove();
                        }
                    }
                    break;
                case CANDIDATE:
                    for (Iterator<TreeItem<String>> treeItemIterator =
                        symbolicCandidateVariableList.iterator();
                            treeItemIterator.hasNext();) {
                        TreeItem<String> item = treeItemIterator.next();
                        if (item.getValue().equals(name)) {
                            treeItemIterator.remove();
                        }
                    }
                    break;
                case SEAT:
                    for (Iterator<TreeItem<String>> treeItemIterator =
                        symbolicSeatVariableList.iterator();
                            treeItemIterator.hasNext();) {
                        TreeItem<String> item = treeItemIterator.next();
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

    /**
     * Removes the all variables.
     */
    public void removeAllVariables() {
        symbolicVoterVariableList.clear();
        symbolicCandidateVariableList.clear();
        symbolicSeatVariableList.clear();
    }

    /**
     * Gets the pre property area.
     *
     * @return the pre property area
     */
    public NewPropertyCodeArea getPrePropertyArea() {
        return preArea;
    }

    /**
     * Gets the post property area.
     *
     * @return the post property area
     */
    public NewPropertyCodeArea getPostPropertyArea() {
        return postArea;
    }

    /**
     * Gets the property description.
     *
     * @return the property description
     */
    public PreAndPostConditionsDescription getPropertyDescription() {
        return currentPropertyDescription;
    }

    /**
     * Save property text areas into description.
     */
    public void savePropertyTextAreasIntoDescription() {
        currentPropertyDescription.getPreConditionsDescription()
                .setCode(preArea.getText());
        currentPropertyDescription.getPostConditionsDescription()
                .setCode(postArea.getText());
        currentPropertyDescription.getBoundedVarDescription()
                .setCode(boundedVarArea.getText());
        currentPropertyDescription.getSymbolicVariablesAsList().clear();
        currentPropertyDescription.getSymVarList().clearList();
        currentPropertyDescription.getSymVarList()
                .addSymbolicVariableList(getAllSymbolicVariables());
    }

    /**
     * Update property text areas.
     *
     * @param description
     *            the description
     */
    public void updatePropertyTextAreas(final PreAndPostConditionsDescription description) {
        preArea.setDescription(description.getPreConditionsDescription());
        postArea.setDescription(description.getPostConditionsDescription());
        boundedVarArea.setDescription(description.getBoundedVarDescription());
    }

    /**
     * Sets the current property description.
     *
     * @param propertyItem
     *            the property item
     * @param bringToFront
     *            the bring to front
     */
    public synchronized void setCurrentPropertyDescription(final ParentTreeItem propertyItem,
                                                           final boolean bringToFront) {
        savePropertyTextAreasIntoDescription();
        this.currentPropertyDescription = propertyItem
                .getPreAndPostProperties();
        this.currentItem = propertyItem;
        this.removeAllVariables();
        updatePropertyTextAreas(currentPropertyDescription);

        for (SymbolicVariable variable : currentPropertyDescription
                .getSymbolicVariablesAsList()) {
            this.addSymbVar(variable.getInternalTypeContainer(),
                    variable.getId(), true);
        }
        if (bringToFront) {
            bringToFront();
        }
        saverLoader.resetHasSaveFile();
    }

    /**
     * Bring to front.
     */
    public void bringToFront() {
        GUIController.getController().getMainTabPane().getSelectionModel()
                .select(GUIController.getController().getPropertyTab());
        if (currentItem != null) {
            // checks if there are errors, and if there are,
            // it displays them in the error tab
            BooleanExpEditorGeneralErrorFinder.hasErrors(currentItem);
        }
    }

    /**
     * Gets the current item.
     *
     * @return the current item
     */
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

    /**
     * Convert.
     *
     * @param json
     *            the json
     * @return the pre and post conditions description
     */
    private PreAndPostConditionsDescription convert(final String json) {
        PreAndPostConditionsDescription newDescription = null;
        if (!"".equals(json)) {
            try {
                newDescription = propSaverLoader.createFromSaveString(json);
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }
            return newDescription;
        }
        return null;
    }

    /**
     * Gets the all symbolic variables.
     *
     * @return the all symbolic variables
     */
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

    /**
     * Open.
     *
     * @param file
     *            the file
     * @return the pre and post conditions description
     */
    public PreAndPostConditionsDescription open(final File file) {
        return convert(saverLoader.load(file));
    }

    @Override
    public void open() {
        PreAndPostConditionsDescription newDescription = convert(
                saverLoader.load());
        if (newDescription != null) {
            GUIController.getController().addProperty(newDescription);
            currentPropertyDescription = newDescription;
            saverLoader.resetHasSaveFile();
            preArea.replaceText(
                    newDescription.getPreConditionsDescription().getCode());
            postArea.replaceText(
                    newDescription.getPostConditionsDescription().getCode());
            GUIController.getController()
                    .setPropNameField(newDescription.getName());
            bringToFront();
        }
    }

    @Override
    public void save() {
        savePropertyTextAreasIntoDescription();
        String json = propSaverLoader
                .createSaveString(currentPropertyDescription);
        saverLoader.save("", json);
    }

    @Override
    public void saveAs() {
        savePropertyTextAreasIntoDescription();
        String json = propSaverLoader
                .createSaveString(currentPropertyDescription);
        saverLoader.saveAs("", json);
    }

    /**
     * Save as.
     *
     * @param description
     *            the description
     * @param file
     *            the file
     */
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

    /**
     * Gets the symbolic variable names.
     *
     * @return the symbolic variable names
     */
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

    /**
     * Sets the focused.
     *
     * @param pane
     *            the new focused
     */
    public void setFocused(final NewPropertyCodeArea pane) {
        this.focusedPane = pane;
    }
}
