package edu.pse.beast.datatypes.propertydescription;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import edu.pse.beast.toolbox.ErrorLogger;
import edu.pse.beast.types.InternalTypeContainer;

/**
 *
 * @author Niels
 */
public class SymbolicVariableList {

	private final LinkedList<SymbolicVariable> symbolicVariableList = new LinkedList<>();;
	private transient final List<VariableListListener> listenerList = new ArrayList<VariableListListener>();

	/**
	 *
	 */
	public SymbolicVariableList() {
		
	}

	/**
	 *
	 * @param id
	 *            id of the variable
	 * @param internalTypeContainer
	 *            the type of the added variable
	 */
	public synchronized void addSymbolicVariable(String id, InternalTypeContainer internalTypeContainer) {
		if (id != null && internalTypeContainer != null) {
			SymbolicVariable var = new SymbolicVariable(id, internalTypeContainer);
			symbolicVariableList.add(var);
			listenerList.forEach((listener) -> {
				listener.addedVar(var);
			});
		} else {
			ErrorLogger.log("Tried to add a Variable without an id or without a type to a SymbolicVariableList");
		}
	}

	/**
	 * @param id
	 *            id which is to be tested
	 * @return true if the var Id is not allready used
	 */
	public synchronized boolean isVarIDAllowed(String id) {
		boolean varAllowed = true;
		if (!symbolicVariableList.isEmpty()) {
			for (SymbolicVariable var : symbolicVariableList) {
				if (var.getId().equals(id)) {
					varAllowed = false;
					break;
				}
			}
		}
		return varAllowed;
	}

	/**
	 * sets the symbolic Variable list and updates it for all listeners
	 *
	 * @param symbolicVariableList
	 *            the new var list
	 */
	public synchronized void setSymbolicVariableList(List<SymbolicVariable> symbolicVariableList) {
		this.symbolicVariableList.clear();
		for (SymbolicVariable var : symbolicVariableList) {
			this.symbolicVariableList.add(var);
			listenerList.forEach((VariableListListener listener) -> {
				listener.addedVar(var);
			});
		}
	}

	/**
	 *
	 * @return returns the linked List of SymbolicVariables
	 */
	public synchronized List<SymbolicVariable> getSymbolicVariables() {
		return symbolicVariableList;
	}

	/**
	 *
	 * @return returns the linked List of SymbolicVariables
	 */
	public synchronized List<SymbolicVariable> getSymbolicVariablesCloned() {
		return cloneSymbVars();
	}

	/**
	 *
	 * @param id
	 *            the id of the variable, that is to be removed
	 * @return returns true if the variable was found
	 */
	public synchronized boolean removeSymbolicVariable(String id) {
		boolean varFound = false;
		for (SymbolicVariable var : symbolicVariableList) {
			varFound = var.getId().equals(id);
			if (varFound) {
				symbolicVariableList.remove(var);
				listenerList.forEach((listener) -> {
					listener.removedVar(var);
				});
				break;
			}
		}
		return varFound;
	}

	/**
	 *
	 * @param index
	 *            the index of the variable, that is to be removed
	 */
	public synchronized void removeSymbolicVariable(int index) {
		if (index >= 0) {
			SymbolicVariable var = symbolicVariableList.get(index);
			listenerList.forEach((l) -> {
				l.removedVar(var);
			});
			symbolicVariableList.remove(index);
		}
	}

	/**
	 *
	 * @param listener
	 *            the listenerList which is to add
	 */
	public synchronized void addListener(VariableListListener listener) {
		this.listenerList.add(listener);
		symbolicVariableList.forEach((var) -> {
			listener.addedVar(var);
		});
	}

	/**
	 *
	 * @param listener
	 *            the listenerList that will be removed
	 */
	public synchronized void removeListener(VariableListListener listener) {
		this.listenerList.remove(listener);
		symbolicVariableList.forEach((var) -> {
			listener.removedVar(var);
		});
	}

	/**
	 * 
	 * @return a clone of the symbVarList
	 */
	private synchronized List<SymbolicVariable> cloneSymbVars() {
		List<SymbolicVariable> clonedSymbVariables = new LinkedList<SymbolicVariable>();
		for (Iterator<SymbolicVariable> iterator = symbolicVariableList.iterator(); iterator.hasNext();) {
			SymbolicVariable symbolicVariable = (SymbolicVariable) iterator.next();
			clonedSymbVariables.add(symbolicVariable.clone());
		}
		return clonedSymbVariables;
	}
}
