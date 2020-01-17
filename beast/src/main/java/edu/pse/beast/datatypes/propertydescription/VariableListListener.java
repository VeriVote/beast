package edu.pse.beast.datatypes.propertydescription;

/**
 * The listener interface for receiving variableList events. The class that is
 * interested in processing a variableList event implements this interface, and
 * the object created with that class is registered with a component using the
 * component's <code>addVariableListListener</code> method. When the
 * variableList event occurs, that object's appropriate method is invoked.
 *
 * @author Holger Klein
 */
public interface VariableListListener {

    /**
     * Added var.
     *
     * @param var
     *            the var
     */
    void addedVar(SymbolicVariable var);

    /**
     * Removed var.
     *
     * @param var
     *            the var
     */
    void removedVar(SymbolicVariable var);
}
