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

    /**
     * Constructor
     *
     * @param model The data model of the property list.
     * @param editor The boolean expression editor that is the source of
     * property descriptions.
     */
    public PropertyList(PLModelInterface model, BooleanExpEditor editor) {
        this.model = model;
        this.editor = editor;
        this.sli = new StringLoaderInterface("de");
        editor.showWindow();
        view = new PropertyListWindow(this, model);
        setSaveBeforeChangeHandler(new PLSaveBeforeChangeHandler(model, null));
        actionList = new LinkedList<DeleteDescriptionAction>();
        model.initialize();
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
     * Returns the data model.
     *
     * @return Data model of property list.
     */
    public PLModel getModel() {
        return (PLModel) model;
    }

    /**
     * Provides the view of property list.
     *
     * @return The PropertyListWindow
     */
    public PropertyListWindow getView() {
        return view;
    }

    /**
     * Displays the view.
     */
    public void start() {
        java.awt.EventQueue.invokeLater(this);
    }

    @Override
    public void run() {
        view.setVisible(true);
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
        //model.deleteProperty(prop);
    }

    @Override
    public void addDescription(PropertyItem prop) {
    	model.addDescription(prop);
    }

    @Override
    public void addNewProperty() {
        model.addNewProperty(editor);
    }


    /**
     * @see edu.pse.beast.highlevel.PostAndPrePropertiesDescriptionSource#isCorrect()
     */
    @Override
    public boolean isCorrect() {
        for (PropertyItem item : model.getList()) {
            if (item.willBeTested()) {
                if (!editor.letUserEditPostAndPreProperties(item.getDescription(), true)) {
                    return false;
                } else if (!editor.isCorrect()) {
                    System.out.println(item.getDescription().getName());
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void stopReacting() {
        view.stopReacting();
    }

    @Override
    public void resumeReacting() {
        view.resumeReacting();
    }

    /* 
     * @see edu.pse.beast.highlevel.PostAndPrePropertiesDescriptionSource#getPostAndPrePropertiesDescriptions()
     */
    @Override
    public ArrayList<PostAndPrePropertiesDescription> getPostAndPrePropertiesDescriptions() {
        editor.updatePostAndPrePropObject();
        ArrayList<PostAndPrePropertiesDescription> result = new ArrayList<PostAndPrePropertiesDescription>();
        ArrayList<PropertyItem> from = model.getList();
        editor.updatePostAndPrePropObject();
        for (PropertyItem prop : from) {
            if (prop.willBeTested()) {
                result.add(prop.getDescription());
            }
        }
        return result;
    }

    /*
	 * @see edu.pse.beast.highlevel.ResultPresenter#presentResult(edu.pse.beast.highlevel.ResultInterface)
     */
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

    public StringResourceLoader getMenuStringLoader() {
        return sli.getPropertyListStringResProvider().getMenuStringRes();
    }

    public PLSaveBeforeChangeHandler getSaveBeforeChangeHandler() {
        return saveBeforeChangeHandler;
    }

    public void setSaveBeforeChangeHandler(PLSaveBeforeChangeHandler saveHandler) {
        this.saveBeforeChangeHandler = saveHandler;
    }

    public void setPLModel(PLModelInterface model) {
        this.model.loadAnotherModel(model);
        ((PLModel) this.model).updateView();
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

}
