/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.saverloader.StaticSaverLoaders;

import edu.pse.beast.datatypes.internal.InternalTypeContainer;
import edu.pse.beast.datatypes.internal.InternalTypeRep;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariableList;

/**
 * Implements static methods for creating saveStrings from SymbolicVarList objects and vice versa.
 * Methods are static due to convenience.
 * @author Holger-Desktop
 */
public class SymbolicVarListSaverLoader {

    /**
     * Creates a String from a given SymbolicVariableList, that can then be saved to a file and later given to
     * createFromSaveString() to retrieve the saved object.
     * @param list the SymbolicVariableList
     * @return the saveString
     */
    public static String createSaveString(SymbolicVariableList list) {
        String created = "";
        for (SymbolicVariable var : list.getSymbolicVariables()) {
            created += "symbolic_variable: ";
            created += createSaveStringForVar(var) + ";\n";
        }
        return created;
    }


    private static String createSaveStringForVar(SymbolicVariable var) {
        return "id: " + var.getId() + " type: " + var.getInternalTypeContainer().getInternalType().toString();
    }

    /**
     * Creates a SymbolicVariableList object from a given, by createSaveString() generated, saveString
     * @param s the SaveString
     * @return the SymbolicVariableList object
     */
    public static SymbolicVariableList createFromSaveString(String s) {
        SymbolicVariableList created = new SymbolicVariableList();
        String newString = s.replaceAll("\n", "");
        String[] var = newString.split(";");
        for (int i = 0; i < var.length; ++i) {
            createSymbVarFromSaveString(var[i].replace("symbolic_variable:", ""), created);
        }
        return created;
    }

    private static void createSymbVarFromSaveString(String s, SymbolicVariableList list) {
        if (s == null || s.length() == 0) return;

        String[] data = s.split(" ");
        String id = data[2];
        String typeString = data[4];
        InternalTypeRep type = InternalTypeRep.valueOf(typeString);
        list.addSymbolicVariable(id, new InternalTypeContainer(type));
    }
}
