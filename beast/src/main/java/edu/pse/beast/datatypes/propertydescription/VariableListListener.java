package edu.pse.beast.datatypes.propertydescription;

/**
 *
 * @author Holger-Desktop
 */
public interface VariableListListener {
    void addedVar(SymbolicVariable var);

    void removedVar(SymbolicVariable var);
}