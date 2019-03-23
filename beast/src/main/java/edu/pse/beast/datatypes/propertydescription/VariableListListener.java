package edu.pse.beast.datatypes.propertydescription;

/**
 *
 * @author Holger-Desktop
 */
public interface VariableListListener {
    public void addedVar(SymbolicVariable var);
    public void removedVar(SymbolicVariable var);
}
