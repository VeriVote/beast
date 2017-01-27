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
import edu.pse.beast.highlevel.ResultPresenter;
import edu.pse.beast.propertychecker.Result;

/**
 *
 * @author Justin
 */

public class PropertyList implements PostAndPrePropertiesDescriptionSource, ResultPresenter {
	
	private static PropertyList instance;
	private ArrayList<PropertyItem> propertyDescriptions;
    private BooleanExpEditor booleanExpEditor;
	
	/**
	 * Constructor
	 * @param booleanExpEditor
	 */
	public PropertyList(BooleanExpEditor booleanExpEditor) {
		this.booleanExpEditor = booleanExpEditor;
	}
        	
	
	/**
	 * @param newList
	 */
	public void refillInstance(ArrayList<PropertyItem> newList) {
		propertyDescriptions = newList;
	}
	
	/**
	 * @param prop
	 * @param newName
	 * @return
	 */
	public Boolean changeName(PostAndPrePropertiesDescription prop, String newName) {
		PropertyItem item = new PropertyItem(prop);
		if (!propertyDescriptions.contains(item)) return false;
		
		for (PropertyItem current : propertyDescriptions) {
			if (current.getDescription().getName() == newName) return false;
		}
		
		// TODO: assign new name to property item
		item = new PropertyItem(prop);
		return true;
	}
	
	/**
	 * @param prop
	 */
	public void addStandardDescription(PostAndPrePropertiesDescription prop) {
		propertyDescriptions.add(new PropertyItem(prop));
	}
	
	/**
	 * 
	 */
	public void newDescription() {
		// TODO: has this class a reference to a BooleanExpEditor?
	}
	
	/**
	 * 
	 */
	public void changeDescription() {
		// TODO: has this class a reference to a BooleanExpEditor?
	}
	
	/**
	 * @param prop
	 * @return
	 */
	public PropertyItem deleteDescription(PostAndPrePropertiesDescription prop) {
		// TODO: does class propertyItem really make sense?
		return null;
	}
	
	/**
	 * @param prop
	 * @param testStatus
	 */
	public void changeTestedStatus(PostAndPrePropertiesDescription prop, Boolean testStatus) {
		// TODO
	}

	
	// Interface methods
    /* (non-Javadoc)
     * @see edu.pse.beast.highlevel.ResultPresenter#presentResult(edu.pse.beast.propertychecker.Result)
     */
    @Override
    public void presentResult(Result res) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /* (non-Javadoc)
     * @see edu.pse.beast.highlevel.PostAndPrePropertiesDescriptionSource#isCorrect()
     */
    @Override
    public boolean isCorrect() { throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /* (non-Javadoc)
	 * @see edu.pse.beast.highlevel.PostAndPrePropertiesDescriptionSource#getPostAndPrePropertiesDescriptions()
	 */
	public ArrayList<PostAndPrePropertiesDescription> getPostAndPrePropertiesDescriptions() {
		/*ArrayList<PostAndPrePropertiesDescription> res = new ArrayList<PostAndPrePropertiesDescription>();
		for (PropertyItem item : propertyDescriptions) res.add(item.getDescription());
		return res;*/
		// TODO
        return null;
	}
}
