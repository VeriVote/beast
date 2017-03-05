package edu.pse.beast.propertylist;

import java.util.ArrayList;

import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import edu.pse.beast.propertylist.Model.PLModelInterface;
import edu.pse.beast.propertylist.Model.PropertyItem;

/**
 * Implementing classes make changes to property items in a list.
 * @author Justin
 *
 */
public interface PLControllerInterface {

    /**
     * Changes the name of the property.
     * @param prop The property you want to change
     * @param newName The new name for the property
     */
    void changeName(PropertyItem prop, String newName);

    /**
     * Sets whether the property will be analyzed by the checker.
     * @param prop The property to analyze
     * @param newStatus A boolean whether it should be analyzed
     */
    void setTestStatus(PropertyItem prop, boolean newStatus);

    /**
     * Lets you edit a property.
     * @param prop The property to edit
     */
    void editProperty(PropertyItem prop);

    /**
     * Deletes a property from the list.
     * @param prop The property to delete
     */
    void deleteProperty(PropertyItem prop);

    /**
     * Adds a property to the list that has already been created.
     * @param prop The property to add
     */
    void addDescription(PropertyItem prop);

    /**
     * Adds a brand new property to the list.
     */
    void addNewProperty();
    
    /**
     * Resets the whole list.
     */
    void setNewList();
    
    /**
     * Returns the list of property items in the list
     * @return An array list with the property items
     */
    ArrayList<PropertyItem> getList();
    
    /**
     * Returns the editor for boolean expressions.
     * @return The editor object
     */
    BooleanExpEditor getEditor();

    /**
     * Sets the data model of property list.
     * @param model The model to replace the old model
     */
    void setPLModel(PLModelInterface model);
}
