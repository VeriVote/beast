/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.saverloader;

import edu.pse.beast.datatypes.internal.InternalTypeContainer;
import edu.pse.beast.datatypes.internal.InternalTypeRep;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariableList;

/**
 *
 * @author Holger-Desktop
 */
public class SymbolicVarListSaverLoader {
    public static String createSaveString(SymbolicVariableList list) {
        String created = "";
        for(SymbolicVariable var : list.getSymbolicVariables()) {
            created += "symbolic_variable: ";
            created += createSaveStringForVar(var) + ";\n";
        }
        return created;
    }
    
    
    private static String createSaveStringForVar(SymbolicVariable var) {
        return "id: " + var.getId() + " type: " + var.getInternalTypeContainer().getInternalType().toString();
    }
    
    public static SymbolicVariableList createFromSaveString(String s) {
        SymbolicVariableList created = new SymbolicVariableList();
        s = s.replaceAll("\n", "");
        String[] var = s.split(";");
        for(int i = 0; i < var.length; ++i) {
            createSymbVarFromSaveString(var[i].replace("symbolic_variable:", ""), created);
        } 
        return created;
    }
    
    private static void createSymbVarFromSaveString(String s, SymbolicVariableList list) {
    	if (s == null) return;
        String[] data = s.split(" ");
        String id = data[2];
        String typeString = data[4];
        InternalTypeRep type = InternalTypeRep.valueOf(typeString);
        list.addSymbolicVariable(id, new InternalTypeContainer(type));
    }
}
