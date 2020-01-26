package edu.pse.beast.datatypes.propertydescription;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import edu.pse.beast.toolbox.ErrorLogger;
import edu.pse.beast.types.InternalTypeContainer;

/**
 * The Class SymbolicVariableList.
 *
 * @author Niels Hanselmann
 */
public final class SymbolicVariableList {
    /** The Constant PRIME. */
    private static final int PRIME = 31;

    /** The symbolic variable list. */
    private final LinkedList<SymbolicVariable> symbolicVariableList =
            new LinkedList<SymbolicVariable>();

    /** The listener list. */
    private final transient List<VariableListListener> listenerList =
            new ArrayList<VariableListListener>();

    /**
     * Instantiates a new symbolic variable list.
     */
    public SymbolicVariableList() { }

    /**
     * Adds the symbolic variable.
     *
     * @param id
     *            id of the variable
     * @param internalTypeContainer
     *            the type of the added variable
     */
    public synchronized void
                addSymbolicVariable(final String id,
                                    final InternalTypeContainer internalTypeContainer) {
        if (id != null && internalTypeContainer != null) {
            final SymbolicVariable var =
                    new SymbolicVariable(id, internalTypeContainer);
            symbolicVariableList.add(var);
            listenerList.forEach(listener -> {
                listener.addedVar(var);
            });
        } else {
            ErrorLogger.log("Tried to add a variable without an id or "
                            + "without a type to a symbolic variable list");
        }
    }

    /**
     * Adds the symbolic variable.
     *
     * @param var
     *            the symbolic variable
     */
    public synchronized void addSymbolicVariable(final SymbolicVariable var) {
        symbolicVariableList.add(var);
        listenerList.forEach(listener -> {
            listener.addedVar(var);
        });
    }

    /**
     * Checks if is var ID allowed.
     *
     * @param id
     *            id which is to be tested
     * @return true if the variable id is not already used
     */
    public synchronized boolean isVarIDAllowed(final String id) {
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
     * Sets the symbolic Variable list and updates it for all listeners.
     *
     * @param symbVarList
     *            the new var list
     */
    public synchronized void setSymbolicVariableList(final List<SymbolicVariable> symbVarList) {
        this.symbolicVariableList.clear();
        for (SymbolicVariable var : symbVarList) {
            this.symbolicVariableList.add(var);
            listenerList.forEach((VariableListListener listener) -> {
                listener.addedVar(var);
            });
        }
    }

    /**
     * Gets the symbolic variables.
     *
     * @return returns the linked List of SymbolicVariables
     */
    public synchronized List<SymbolicVariable> getSymbolicVariables() {
        return symbolicVariableList;
    }

    /**
     * Gets the symbolic variables cloned.
     *
     * @return returns the linked List of SymbolicVariables
     */
    public synchronized List<SymbolicVariable> getSymbolicVariablesCloned() {
        return cloneSymbVars();
    }

    /**
     * Removes the symbolic variable.
     *
     * @param id
     *            the id of the variable, that is to be removed
     * @return returns true if the variable was found
     */
    public synchronized boolean removeSymbolicVariable(final String id) {
        boolean varFound = false;
        for (SymbolicVariable var : symbolicVariableList) {
            varFound = var.getId().equals(id);
            if (varFound) {
                symbolicVariableList.remove(var);
                listenerList.forEach(listener -> {
                    listener.removedVar(var);
                });
                break;
            }
        }
        return varFound;
    }

    /**
     * Removes the symbolic variable.
     *
     * @param index
     *            the index of the variable, that is to be removed
     */
    public synchronized void removeSymbolicVariable(final int index) {
        if (index >= 0) {
            final SymbolicVariable var = symbolicVariableList.get(index);
            listenerList.forEach(l -> {
                l.removedVar(var);
            });
            symbolicVariableList.remove(index);
        }
    }

    /**
     * Adds the listener.
     *
     * @param listener
     *            the listenerList which is to add
     */
    public synchronized void addListener(final VariableListListener listener) {
        this.listenerList.add(listener);
        symbolicVariableList.forEach(var -> {
            listener.addedVar(var);
        });
    }

    /**
     * Removes the listener.
     *
     * @param listener
     *            the listenerList that will be removed
     */
    public synchronized void removeListener(final VariableListListener listener) {
        this.listenerList.remove(listener);
        symbolicVariableList.forEach(var -> {
            listener.removedVar(var);
        });
    }

    /**
     * Clear list.
     */
    public void clearList() {
        symbolicVariableList.clear();
    }

    /**
     * Adds the symbolic variable list.
     *
     * @param allSymbolicVariables
     *            the all symbolic variables
     */
    public void addSymbolicVariableList(final SymbolicVariableList allSymbolicVariables) {
        for (Iterator<SymbolicVariable> iterator =
                allSymbolicVariables.getSymbolicVariables().iterator();
                iterator.hasNext();) {
            final SymbolicVariable var = iterator.next();
            this.addSymbolicVariable(var);
        }
    }

    /**
     * Clone symb vars.
     *
     * @return a clone of the symbVarList
     */
    private synchronized List<SymbolicVariable> cloneSymbVars() {
        final List<SymbolicVariable> clonedSymbVariables =
                new LinkedList<SymbolicVariable>();
        for (Iterator<SymbolicVariable> iterator =
                symbolicVariableList.iterator();
                iterator.hasNext();) {
            final SymbolicVariable symbolicVariable = iterator.next();
            clonedSymbVariables.add(symbolicVariable.clone());
        }
        return clonedSymbVariables;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = PRIME * result
                + ((symbolicVariableList == null)
                        ? 0 : symbolicVariableList.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SymbolicVariableList other = (SymbolicVariableList) obj;
        if (symbolicVariableList == null) {
            if (other.symbolicVariableList != null) {
                return false;
            }
        } else if (!symbolicVariableList.equals(other.symbolicVariableList)) {
            return false;
        }
        return true;
    }
}
