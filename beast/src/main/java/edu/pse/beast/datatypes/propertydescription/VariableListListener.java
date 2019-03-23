package edu.pse.beast.datatypes.propertydescription;

/**
 *
 * @author Holger Klein
 */
public interface VariableListListener {
    void addedVar(SymbolicVariable var);

    void removedVar(SymbolicVariable var);
}