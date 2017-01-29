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
import edu.pse.beast.propertychecker.Result;

/**
 *
 * @author Justin
 */

public class PropertyList implements PostAndPrePropertiesDescriptionSource, ResultPresenter {
	
	// private static PropertyList instance;
	private ArrayList<PropertyItem> propDescs;
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
	 
	public void refillInstance(ArrayList<PropertyItem> newList) {
		propertyDescriptions = newList;
	}*/
	
	private void checkForCurrentEditorContent() {
		String currentlyInEditor = booleanExpEditor.getCurrentlyLoadedPostAndPreProp().getName();
		
		PropertyItem editorProp = propDescs.get(indexOfName(currentlyInEditor));
		
		editorProp.setDescription(booleanExpEditor.getCurrentlyLoadedPostAndPreProp());
		
	}
	
	private int indexOfName(String name) {
		for (PropertyItem current : propDescs) {
			if (current.getDescription().getName() == name) return propDescs.indexOf(current);
		}
		return -1;
	}
	
	/**
	 * @param prop
	 * @param newName
	 * @return
	 */
	public Boolean changeName(PropertyItem prop, String newName) {
		
		int index = propDescs.indexOf(prop);
		if (index == -1) return false;
		
		if (indexOfName(newName) != -1) return false;
		
		PostAndPrePropertiesDescription old = propDescs.get(index).getDescription();
		propDescs.get(index).setDescription(newName, old.getPrePropertiesDescription(),
				old.getPostPropertiesDescription(), old.getSymVarList());
		
		return true;
	}
	
	/**
	 * @param desc
	 */
	public void addStandardDescription(PostAndPrePropertiesDescription desc) {
		checkForCurrentEditorContent();
		propDescs.add(new PropertyItem(desc));
	}
	
	/**
	 * 
	 */
	public void newDescription(String name) {
		checkForCurrentEditorContent();
		PropertyItem newItem = new PropertyItem(new PostAndPrePropertiesDescription(name), false);
		booleanExpEditor.loadPostAndPreProperties(newItem.getDescription());
		propDescs.add(newItem);
	}
	
	/**
	 * 
	 */
	public void changeDescription(PropertyItem prop) {
		checkForCurrentEditorContent();
		booleanExpEditor.loadPostAndPreProperties(prop.getDescription());
	}
	
	/**
	 * @param prop
	 * @return
	 */
	public PropertyItem deleteDescription(PropertyItem prop) {
		int index = propDescs.indexOf(prop);
		if (index == -1) return null;
		return propDescs.remove(index);
	}
	
	/**
	 * @param prop
	 */
	public void changeTestedStatus(PropertyItem prop) {
		prop.toggleTestStatus();
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
		/*ArrayList<PostAndPrePropertiesDescription> res = new ArrayList<PostAndPrePropertiesDescription>();
		for (PropertyItem item : propertyDescriptions) res.add(item.getDescription());
		return res;*/
		ArrayList<PostAndPrePropertiesDescription> result = new ArrayList<PostAndPrePropertiesDescription>();
		for (PropertyItem item : propDescs) {
			result.add(item.getDescription());
		}
		return result;
	}
	
	public ArrayList<PropertyItem> getDescr() {
		return propDescs;
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
