package edu.pse.beast.propertylist.Model;

import java.util.ArrayList;
import java.util.Observer;

import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import edu.pse.beast.highlevel.ResultInterface;

/**
 * This interface describes all methods that can performed with the data model of property list.
 * @author Justin
 *
 */
public interface PLModelInterface {

    /**
     * Initializes the model.
     */
    void initialize();

    /**
     * Changes the name of a property.
     * @param prop The property to be renamed
     * @param newName The new name for the property
     * @return true, if the name change was successful
     */
    boolean changeName(PropertyItem prop, String newName);

    /**
     * Adds a given property to the property list
     * @param prop The property to add
     * @return true when the addition was a success
     */
    boolean addDescription(PropertyItem prop);

    /**
     * Adds a brand new property to the property list.
     * @param editor The BooleanExpEditor in which you can edit the property
     * @return Returns true if the property could be added
     */
    boolean addNewProperty(BooleanExpEditor editor);

    /**
     * Edits a property in the property list.
     * @param prop The property to edit
     * @param editor The BooleanExpEditor object in which to edit the given property
     */
    void editProperty(PropertyItem prop, BooleanExpEditor editor);

    /**
     * Deletes a property from the list
     * @param prop The property to remove
     * @return Returns true if the deletion was a success
     */
    boolean deleteProperty(PropertyItem prop);

    /**
     * Sets whether the property should be analyzed by the checker or not.
     * @param prop The property to analyze
     * @param newStatus Sets if the property will be analyzed in the next check
     */
    void setTestStatus(PropertyItem prop, boolean newStatus);

    /**
     * Clears the current list.
     */
    void setNewList();

    /**
     * Returns a list of PropertyItem.
     * @return An ArrayList with all the property items in the property list
     */
    ArrayList<PropertyItem> getPropertyList();
    /**
     * Sets a new property list.
     * @param list The new list of property items that make up the new property list.
     */
    void setPropertyList(ArrayList<PropertyItem> list);

    /**
     * Class is observable. You can add observer objects.
     * @param o The observer to add
     */
    void addObserver(Observer o);

    /**
     * Class is observable. You can also delete observers.
     * @param o The observer to remove
     */
    void deleteObserver(Observer o);

    /**
     * Gets the next property item that gets a result presentation.
     * @param res The ResultInterface with the analysis result for the property
     * @return Returns if there is a next item to receive a result
     */
    boolean setNextToBePresented(ResultInterface res);

    /**
     * Resets all result data in the property list.
     */
    void resetResults();

    /**
     * Getter for the data model.
     * @return Returns the property list model
     */
    PLModel getModel();
    /**
     * Setter for the data model.
     * @param model The new model to replace the old one
     */
    void loadAnotherModel(PLModelInterface model);
    
    /**
     * Sets the name of the property list.
     * @param name The new name for the list
     */
    void setNewName(String name);
    /**
     * Returns the name of the property list.
     * @return The name of the list
     */
    String getName();
}
