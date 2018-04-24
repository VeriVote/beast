package edu.pse.beast.highlevel.javafx;

import java.util.Iterator;

import edu.pse.beast.codeareaJAVAFX.NewPostPropertyCodeArea;
import edu.pse.beast.codeareaJAVAFX.NewPrePropertyCodeArea;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariableList;
import edu.pse.beast.types.InternalTypeContainer;

public class BooleanExpEditorNEW {
	
	private static SymbolicVariableList symbVars = new SymbolicVariableList();
	
	public static boolean containsVarName(String name) {
		boolean contains = false;
		
		for (Iterator<SymbolicVariable> iterator = symbVars.getSymbolicVariables().iterator(); iterator.hasNext();) {
			SymbolicVariable variable = (SymbolicVariable) iterator.next();
			contains = contains || (variable.getId().equals(name));
		}
		
		return contains;
	}

	public static void addSymbVar(InternalTypeContainer container) {
		String toAdd = GUIController.getController().getVariableNameField().getText();
		
		
		if(!containsVarName(toAdd)) {
			symbVars.getSymbolicVariables().add(new SymbolicVariable(toAdd, container));
		}
	}
	
	public static void removeVariable(String name) {
		for (Iterator<SymbolicVariable> iterator = symbVars.getSymbolicVariables().iterator(); iterator.hasNext();) {
			SymbolicVariable variable = (SymbolicVariable) iterator.next();
			if (variable.getId().equals(name)) {
				iterator.remove();
			}
		}
	}
	
	public static NewPrePropertyCodeArea getPrePropertyArea() {
		return GUIController.getController().getPreCodeArea();
	}
	
	public static NewPostPropertyCodeArea getPostPropertyArea() {
		return GUIController.getController().getPostCodeArea();
	}
	
	public static SymbolicVariableList getSymbVarList() {
		return symbVars;
	}
	
}
