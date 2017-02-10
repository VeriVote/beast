/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.propertylist;

import java.util.ArrayList;
import java.util.LinkedList;

import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.highlevel.DisplaysStringsToUser;
import edu.pse.beast.highlevel.PostAndPrePropertiesDescriptionSource;
import edu.pse.beast.highlevel.ResultInterface;
import edu.pse.beast.highlevel.ResultPresenter;
import edu.pse.beast.propertylist.Model.PLModel;
import edu.pse.beast.propertylist.Model.PLModelInterface;
import edu.pse.beast.propertylist.Model.PropertyItem;
import edu.pse.beast.propertylist.View.PropertyListWindow;
import edu.pse.beast.stringresource.StringLoaderInterface;
import edu.pse.beast.stringresource.StringResourceLoader;
import edu.pse.beast.saverloader.FileChooser;

/**
 * Class acts as controller for everything related to the property list. Returns
 * the list of properties descriptions, presents results and starts the view.
 *
 * @author Justin
 */
public class PropertyList implements PLControllerInterface, PostAndPrePropertiesDescriptionSource,
        ResultPresenter, Runnable, DisplaysStringsToUser {

    private PLModelInterface model;

    private PropertyListWindow view;

    private BooleanExpEditor editor;

    private StringLoaderInterface sli;

    private PLSaveBeforeChangeHandler saveBeforeChangeHandler;
    
    private LinkedList<DeleteDescriptionAction> actionList;

    private FileChooser fileChooser;
    
    private boolean editorWasVisible = false;
    
    /**
     * Constructor
     *
     * @param model The data model of the property list.
     * @param editor The boolean expression editor that is the source of
     * property descriptions.
     * @param fileChooser the FileChooser with which files can be loaded and saved
     */
    public PropertyList(PLModelInterface model, BooleanExpEditor editor, FileChooser fileChooser) {
        this.model = model;
        this.editor = editor;
        this.sli = new StringLoaderInterface("de");
        editor.showWindow();
        view = new PropertyListWindow(this, model);
        setSaveBeforeChangeHandler(new PLSaveBeforeChangeHandler(model, null));
        actionList = new LinkedList<DeleteDescriptionAction>();
        model.initialize();
        this.fileChooser = fileChooser;
    }

    /**
     * Test constructor
     *
     * @param model Only needs the model for testing purposes
     */
    public PropertyList(PLModelInterface model) {
        this.model = model;
        view = new PropertyListWindow(this, model);
        model.initialize();
    }

    /**
     * Displays the view.
     */
    public void start() {
        java.awt.EventQueue.invokeLater(this);
    }

    @Override
    public void run() {
        //view.setVisible(true);
        //view.setVisible(false);
    }

    @Override
    public void changeName(PropertyItem prop, String newName) {
        if (!model.changeName(prop, newName)) {
            view.rejectNameChange(prop);
        }
    }

    @Override
    public void setTestStatus(PropertyItem prop, boolean newStatus) {
        model.setTestStatus(prop, newStatus);
    }

    @Override
    public void editProperty(PropertyItem prop) {
        model.editProperty(prop, editor);
    }

    @Override
    public void deleteProperty(PropertyItem prop) {
    	DeleteDescriptionAction act = new DeleteDescriptionAction(model, prop);
    	act.perform();
    	actionList.add(act);
    }

    @Override
    public void addDescription(PropertyItem prop) {
    	model.addDescription(prop);
    }

    @Override
    public void addNewProperty() {
        model.addNewProperty(editor);
    }

    @Override
    public boolean isCorrect() {
        for (PropertyItem item : model.getPropertyList()) {
            if (item.willBeTested()) {
                if (!editor.letUserEditPostAndPreProperties(item.getDescription(), true)) {
                    return false;
                } else if (!editor.isCorrect()) {
                    System.out.println("Property '" + item.getDescription().getName() + "' contains errors.");
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void stopReacting() {
        view.stopReacting();
        editorWasVisible = editor.getView().isVisible();
        editor.getView().setVisible(false);
    }

    @Override
    public void resumeReacting() {
        view.resumeReacting();
        if (editorWasVisible) {
            editor.getView().setVisible(true);
        }
    }

    @Override
    public ArrayList<PostAndPrePropertiesDescription> getPostAndPrePropertiesDescriptions() {
        editor.updatePostAndPrePropObject();
        ArrayList<PostAndPrePropertiesDescription> result = new ArrayList<PostAndPrePropertiesDescription>();
        ArrayList<PropertyItem> from = model.getPropertyList();
        editor.updatePostAndPrePropObject();
        for (PropertyItem prop : from) {
            if (prop.willBeTested()) {
                result.add(prop.getDescription());
            }
        }
        return result;
    }

    @Override
    public void presentResult(ResultInterface res) {
        model.setNextToBePresented(res);
    }

    @Override
    public void resetResults() {
        model.resetResults();
        view.updateView();
    }

    @Override
    public void updateStringRes(StringLoaderInterface sli) {
        this.sli = sli;
    }

    /**
     * Provides the StringResourceLoader for the menu strings of property list
     * @return StringResourceLoader for the menu strings
     */
    public StringResourceLoader getMenuStringLoader() {
        return sli.getPropertyListStringResProvider().getMenuStringRes();
    }
    
    public DeleteDescriptionAction getLastAction() {
    	if (actionList.isEmpty()) {
    		return null;
    	}
    	return actionList.removeLast();
    }
    
    public void resetActionList() {
    	actionList = new LinkedList<DeleteDescriptionAction>();
    }
    
    
    // getter and setter follow
    /**
     * Returns the data model.
     *
     * @return Data model of property list.
     */
    public PLModel getModel() {
        return (PLModel) model;
    }

    public void setPLModel(PLModelInterface model) {
        this.model.loadAnotherModel(model);
        view.setWindowTitle(((PLModel) model).getName());
    }

    /**
     * Provides the view of property list.
     *
     * @return The PropertyListWindow
     */
    public PropertyListWindow getView() {
        return view;
    }

    public PLSaveBeforeChangeHandler getSaveBeforeChangeHandler() {
        return saveBeforeChangeHandler;
    }

    public void setSaveBeforeChangeHandler(PLSaveBeforeChangeHandler saveHandler) {
        this.saveBeforeChangeHandler = saveHandler;
    }
    
    public FileChooser getFileChooser() {
        return fileChooser;
    }
    
	@Override
	public BooleanExpEditor getEditor() {
		return editor;
	}

}
