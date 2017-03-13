/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.propertylist;

import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.highlevel.DisplaysStringsToUser;
import edu.pse.beast.highlevel.PostAndPrePropertiesDescriptionSource;
import edu.pse.beast.highlevel.ResultInterface;
import edu.pse.beast.highlevel.ResultPresenter;
import edu.pse.beast.propertylist.Model.PLModel;
import edu.pse.beast.propertylist.Model.PropertyItem;
import edu.pse.beast.propertylist.View.PropertyListWindow;
import edu.pse.beast.saverloader.FileChooser;
import edu.pse.beast.stringresource.StringLoaderInterface;
import edu.pse.beast.stringresource.StringResourceLoader;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Class acts as controller for everything related to the property list. Returns
 * the list of properties descriptions, presents results and starts the view.
 *
 * @author Justin
 */
public class PropertyList implements PostAndPrePropertiesDescriptionSource,
        ResultPresenter, Runnable, DisplaysStringsToUser {

    private PLModel model;
    private PropertyListWindow view;
    private BooleanExpEditor editor;
    private StringLoaderInterface sli;
    private PLChangeHandler changeHandler;
    private LinkedList<DeleteDescriptionAction> actionList;
    private FileChooser fileChooser;
    private boolean editorWasVisible = false;
    
    /**
     * Constructor
     * @param model The data model of the property list.
     * @param editor The boolean expression editor that is the source of
     * property descriptions.
     * @param fileChooser the FileChooser with which files can be loaded and saved
     */
    public PropertyList(PLModel model, BooleanExpEditor editor, FileChooser fileChooser,
    		StringLoaderInterface sli) {
        this.model = model;
        this.editor = editor;
        this.sli = sli;
        view = new PropertyListWindow(this, model);
        
        setChangeHandler(new PLChangeHandler(model));
        actionList = new LinkedList<DeleteDescriptionAction>();
        
        model.initialize();
        this.fileChooser = fileChooser;
    }

    /**
     * Test constructor
     * @param model Only needs the model for testing purposes
     */
    public PropertyList(PLModel model) {
    	this(model, null, null, null);
    }

    
    /**
     * Displays the view.
     */
    public void start() {
        java.awt.EventQueue.invokeLater(this);
    }

    @Override
    public void run() {
    }
    
    /**
     * Changes the name of the property.
     * @param prop The property you want to change
     * @param newName The new name for the property
     */
    public void changeName(PropertyItem prop, String newName) {
        if (!model.changeName(prop, newName, editor)) {
            view.rejectNameChange(prop);
        }
    }

    /**
     * Sets whether the property will be analyzed by the checker.
     * @param prop The property to analyze
     * @param newStatus A boolean whether it should be analyzed
     */
    public void setTestStatus(PropertyItem prop, boolean newStatus) {
        model.setTestStatus(prop, newStatus);
    }

    /**
     * Lets you edit a property.
     * @param prop The property to edit
     */
    public void editProperty(PropertyItem prop) {
        model.editProperty(prop, editor);
    }

    /**
     * Deletes a property from the list.
     * @param prop The property to delete
     */
    public void deleteProperty(PropertyItem prop) {
    	DeleteDescriptionAction act = new DeleteDescriptionAction(model, prop, editor);
    	if (act.perform()) {
    		actionList.add(act);
    	}
    }

    /**
     * Adds a property to the list that has already been created.
     * @param prop The property to add
     */
    public void addDescription(PropertyItem prop) {
    	model.addDescription(prop);
    }

    /**
     * Adds a brand new property to the list.
     */
    public void addNewProperty() {
        model.addNewProperty(editor);
    }
    
    /**
     * Resets the whole list.
     */
	public void setNewList() {
		model.setNewList();
	}
	
    /**
     * Returns the list of property items in the list
     * @return An array list with the property items
     */
	public ArrayList<PropertyItem> getList() {
		return model.getPropertyList();
	}

    @Override
    public boolean isCorrect() {
        for (PropertyItem item : model.getPropertyList()) {
            if (item.getTestStatus()) {
                if (!editor.letUserEditPostAndPreProperties(item.getDescription(), true)) {
                    return false;
                } else if (!editor.isCorrect()) {
                    editor.getView().setVisible(true);
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void stopReacting() {
        view.setVisible(true);
        view.stopReacting();
        editorWasVisible = editor.getView().isVisible();
        editor.getView().setVisible(false);
    }

    @Override
    public void resumeReacting() {
        view.resumeReacting();
        editor.getView().setVisible(editorWasVisible);
    }

    @Override
    public ArrayList<PostAndPrePropertiesDescription> getPostAndPrePropertiesDescriptions() {
        editor.updatePostAndPrePropObject();
        ArrayList<PostAndPrePropertiesDescription> result = new ArrayList<PostAndPrePropertiesDescription>();
        ArrayList<PropertyItem> from = model.getPropertyList();
        editor.updatePostAndPrePropObject();
        for (PropertyItem prop : from) {
            if (prop.getTestStatus()) {
                result.add(prop.getDescription());
            }
        }
        return result;
    }

    @Override
    public void presentResult(ResultInterface res, Integer index) {
        model.setNextToBePresented(res, index);
    }

    @Override
    public void resetResults() {
        model.resetResults();
        view.updateView();
    }

    @Override
    public void updateStringRes(StringLoaderInterface sli) {
        this.sli = sli;
        fileChooser.updateStringRessourceLoader(sli.getPropertyListStringResProvider().getOtherStringRes());
    }

    
    /**
     * Provides the StringResourceLoader for the menu strings of property list
     * @return StringResourceLoader for the menu strings
     */
    public StringResourceLoader getMenuStringLoader() {
        return sli.getPropertyListStringResProvider().getMenuStringRes();
    }
    
    /**
     * Returns the last delete action.
     * @return The DeleteDescriptionAction that was lastly performed
     */
    public DeleteDescriptionAction getLastAction() {
    	if (actionList.isEmpty()) {
    		return null;
    	}
    	return actionList.removeLast();
    }
    
    /**
     * Resets the delete action list (e.g. do it when a new list is loaded).
     */
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

    /**
     * Sets the data model of property list.
     * @param model The model to replace the old model
     */
    public void setPLModel(PLModel model) {
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

    public PLChangeHandler getChangeHandler() {
        return changeHandler;
    }

    public void setChangeHandler(PLChangeHandler changeHandler) {
        this.changeHandler = changeHandler;
    }
    
    public FileChooser getFileChooser() {
        return fileChooser;
    }
    
    /**
     * Returns the editor for boolean expressions.
     * @return The editor object
     */
	public BooleanExpEditor getEditor() {
		return editor;
	}





}
