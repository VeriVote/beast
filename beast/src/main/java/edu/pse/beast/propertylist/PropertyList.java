/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.propertylist;

/**
*
* @author Justin
*/
import java.util.ArrayList;

import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;

/**
 *
 * @author Niels
 */
public class PropertyList {
	
	private static PropertyList instance;
	private ArrayList<PropertyItem> propertyDescriptions;
	// private final BooleanExpEditor editor; although this wont be needed, probably
	
	private PropertyList() {
		propertyDescriptions = new ArrayList<PropertyItem>();
	}
    
	public static PropertyList getInstance() {
		if (instance == null) instance = new PropertyList();	
		return instance;
	}
	
	public PropertyList getPostAndPrePropertiesSource() {
		return PropertyList.getInstance();
	}
	
	public ArrayList<PostAndPrePropertiesDescription> getPostAndPrePropertiesDescriptions() {
		ArrayList<PostAndPrePropertiesDescription> res = new ArrayList<PostAndPrePropertiesDescription>();
		for (PropertyItem item : propertyDescriptions) res.add(item.getDescription());
		return res;
	}
	
	public void refillInstance(ArrayList<PropertyItem> newList) {
		propertyDescriptions = newList;
	}
	
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
	
	public void addStandardDescription(PostAndPrePropertiesDescription prop) {
		propertyDescriptions.add(new PropertyItem(prop));
	}
	
	public void newDescription() {
		// TODO: has this class a reference to a BooleanExpEditor?
	}
	
	public void changeDescription() {
		// TODO: has this class a reference to a BooleanExpEditor?
	}
	
	public PropertyItem deleteDescription(PostAndPrePropertiesDescription prop) {
		// TODO: does class propertyItem really make sense?
		return null;
	}
	
	public void changeTestedStatus(PostAndPrePropertiesDescription prop, Boolean testStatus) {
		// TODO
	}
}
