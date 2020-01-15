package edu.pse.beast.datatypes.propertydescription;

import edu.pse.beast.types.InternalTypeContainer;

//import edu.pse.beast.datatypes.internal.InternalTypeContainer;
/**
 *
 * @author Niels Hanselmann
 */
public class SymbolicVariable {
    private final String id;
    private final InternalTypeContainer internalTypeContainer;

    /**
     * Creates a new SymbolicVariable.
     *
     * @param idVal              the id of the new variabele
     * @param internalTypeRep the Type of the new variable
     */
    public SymbolicVariable(final String idVal,
                            final InternalTypeContainer internalTypeRep) {
        this.id = idVal;
        this.internalTypeContainer = internalTypeRep;
    }

    /**
     *
     * @return the Id of the variable
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @return the type of the Variable
     */
    public InternalTypeContainer getInternalTypeContainer() {
        return internalTypeContainer;
    }

    @Override
    public SymbolicVariable clone() {
        return new SymbolicVariable(id, internalTypeContainer);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((id == null)
                        ? 0 : id.hashCode());
        result = prime * result
                + ((internalTypeContainer == null)
                        ? 0 : internalTypeContainer.hashCode());
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
        SymbolicVariable other = (SymbolicVariable) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (internalTypeContainer == null) {
            if (other.internalTypeContainer != null) {
                return false;
            }
        } else if (!internalTypeContainer.equals(other.internalTypeContainer)) {
            return false;
        }
        return true;
    }
}
