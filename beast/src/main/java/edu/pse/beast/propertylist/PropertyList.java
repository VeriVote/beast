/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.propertylist;


import java.util.ArrayList;
import java.util.Observable;

import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.highlevel.PostAndPrePropertiesDescriptionSource;
import edu.pse.beast.highlevel.ResultInterface;
import edu.pse.beast.highlevel.ResultPresenter;
import edu.pse.beast.propertychecker.Result;
import edu.pse.beast.propertylist.Model.PLModelInterface;
import edu.pse.beast.propertylist.View.PropertyListWindow;

/**
 *
 * @author Justin
 */

public class PropertyList implements PLControllerInterface, PostAndPrePropertiesDescriptionSource, ResultPresenter {
	
	private PLModelInterface model;
	private PropertyListWindow view;
	
	private BooleanExpEditor booleanExpEditor;
	
	/**
	 * Constructor
	 * @param booleanExpEditor
	 */
	public PropertyList(PLModelInterface model, BooleanExpEditor booleanExpEditor) {
		this.model = model;
		this.booleanExpEditor = booleanExpEditor;
		view = new PropertyListWindow(this, model);
		model.initialize();
	}
	
	@Override
	public void toggleResult() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void changeName(PropertyItem prop, String newName) {
		model.changeName(prop, newName);
	}
	@Override
	public void setTestStatus(PropertyItem prop) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void editProperty(PropertyItem prop) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void deleteProperty(PropertyItem prop) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void addDescription(PostAndPrePropertiesDescription desc) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void addNewProperty() {
		model.addNewProperty();
		view.addItem(model.getList().get(model.getList().size() - 1));
	}
	

	

    /* (non-Javadoc)
     * @see edu.pse.beast.highlevel.PostAndPrePropertiesDescriptionSource#isCorrect()
     */
    @Override
    public boolean isCorrect() { throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

	@Override
	public void stopReacting() {

	}

	@Override
	public void resumeReacting() {

	}

	/* (non-Javadoc)
     * @see edu.pse.beast.highlevel.PostAndPrePropertiesDescriptionSource#getPostAndPrePropertiesDescriptions()
     */
	public ArrayList<PostAndPrePropertiesDescription> getPostAndPrePropertiesDescriptions() {
		return null;
		/*ArrayList<PostAndPrePropertiesDescription> res = new ArrayList<PostAndPrePropertiesDescription>();
		for (PropertyItem item : propertyDescriptions) res.add(item.getDescription());
		return res;
		ArrayList<PostAndPrePropertiesDescription> result = new ArrayList<PostAndPrePropertiesDescription>();
		for (PropertyItem item : propDescs) {
			result.add(item.getDescription());
		}
		return result;*/
	}
	


	@Override
	public void presentResult(ResultInterface res) {
		// TODO Auto-generated method stub
		
	}
	
	// Interface methods
    /* (non-Javadoc)
     * @see edu.pse.beast.highlevel.ResultPresenter#presentResult(edu.pse.beast.propertychecker.Result)
     */
    public void presentResult(Result res) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

	
	
}
