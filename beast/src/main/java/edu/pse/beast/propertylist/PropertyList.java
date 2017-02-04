/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.propertylist;

import java.util.ArrayList;

import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.highlevel.PostAndPrePropertiesDescriptionSource;
import edu.pse.beast.highlevel.ResultInterface;
import edu.pse.beast.highlevel.ResultPresenter;
import edu.pse.beast.propertylist.Model.PLModel;
import edu.pse.beast.propertylist.Model.PLModelInterface;
import edu.pse.beast.propertylist.View.PropertyListWindow;

/**
 *
 * @author Justin
 */
public class PropertyList implements PLControllerInterface, PostAndPrePropertiesDescriptionSource, ResultPresenter {

	
	private PLModelInterface model;
	
	private PropertyListWindow view;
	
	private BooleanExpEditor editor;
	
	/**
	 * Constructor
	 * @param editor
	 */
	public PropertyList(PLModelInterface model, BooleanExpEditor editor) {
		this.model = model;
		this.editor = editor;
		editor.showWindow();
		view = new PropertyListWindow(this, model);
		model.initialize();
	}
	
	/**
	 * Test constructor
	 * @param model
	 */
	public PropertyList(PLModelInterface model) {
		this.model = model;
		view = new PropertyListWindow(this, model);
		model.initialize();
	}
	
	/**
	 * @return
	 */
	public PLModel getModel() {
		return (PLModel)model;
	}
	/**
	 * @return
	 */
	public PropertyListWindow getView() {
		return view;
	}
		
	@Override
	public void changeName(PropertyItem prop, String newName) {
		if (!model.changeName(prop, newName)) view.rejectNameChange(prop);
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
		model.deleteProperty(prop, editor);
	}
	@Override
	public void addDescription(PostAndPrePropertiesDescription desc) {
		// TODO Only when Descriptions are already available
	}
	@Override
	public void addNewProperty() {
		model.addNewProperty(editor);
	}


    /* 
     * @see edu.pse.beast.highlevel.PostAndPrePropertiesDescriptionSource#isCorrect()
     */
    @Override
    public boolean isCorrect() { throw new UnsupportedOperationException("Not supported yet."); 
        //return true;
        // TODO what exactly should be done?
        

        /* for (PropertyItem item : model.getList()) {
         */
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
    public ArrayList<PostAndPrePropertiesDescription> getPostAndPrePropertiesDescriptions() {
        ArrayList<PostAndPrePropertiesDescription> result = new ArrayList<PostAndPrePropertiesDescription>();
        ArrayList<PropertyItem> from = model.getList();

        for (PropertyItem prop : from) {
            if (prop.willBeTested()) {
                result.add(prop.getDescription());
            }
        }
        return result;
    }

    /* (non-Javadoc)
	 * @see edu.pse.beast.highlevel.ResultPresenter#presentResult(edu.pse.beast.highlevel.ResultInterface)
     */
    @Override
    public void presentResult(ResultInterface res) {
        // Auto-generated method stub

        // if (res.readyToPresent()) res.presentTo(presenter);
        // ich brauch noch das postandpreproperties dazu
        model.setNextToBePresented(res);
        //res.presentTo(view.getNextToPresent());
        //view.updateView();
    }

}
