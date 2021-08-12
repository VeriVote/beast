package edu.pse.beast.api.property;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.pse.beast.api.codegen.cbmc.SymbolicVariable;

/**
 * The Class PropertyDescription.
 *
 * @author Niels Hanselmann
 */
public final class PropertyDescription {
    private String uuid;

    /** The name. */
    private String name;

    private List<SymbolicVariable> variables = new ArrayList<SymbolicVariable>();

    /** The preconditions description. */
    private final FormalPropertyDescription preConditionsDescription;

    /** The postconditions description. */
    private final FormalPropertyDescription postConditionsDescription;

    /** The bounded variable description. */
    private final FormalPropertyDescription boundedVarDescription;

    /**
     * Instantiates a new pre and post conditions description.
     *
     * @param nameString the name string
     */
    public PropertyDescription(final String nameString) {
        this.uuid = UUID.randomUUID().toString();
        this.name = nameString;
        this.preConditionsDescription = new FormalPropertyDescription();
        this.postConditionsDescription = new FormalPropertyDescription();
        this.boundedVarDescription = new FormalPropertyDescription();
    }

    public PropertyDescription(final String uuidString,
                    final String nameString,
                    final List<SymbolicVariable> cbmcVars,
                    final FormalPropertyDescription preConditionsDescr,
                    final FormalPropertyDescription postConditionsDescr) {
        this.uuid = uuidString;
        this.name = nameString;
        this.variables = cbmcVars;
        this.preConditionsDescription = preConditionsDescr;
        this.postConditionsDescription = postConditionsDescr;
        this.boundedVarDescription = new FormalPropertyDescription();
    }

    /**
     * Creator with a SymbolicVariableList.
     *
     * @param nameString     name of the PropertyDescription
     * @param preDescr       the preConditionDescription
     * @param postDescr      the postConditionDescription
     * @param boundedVarDesc the boundedVarDescription
     */
    public PropertyDescription(final String nameString,
                               final FormalPropertyDescription preDescr,
                               final FormalPropertyDescription postDescr,
                               final FormalPropertyDescription boundedVarDesc) {
        this.name = nameString;
        this.preConditionsDescription = preDescr;
        this.postConditionsDescription = postDescr;
        this.boundedVarDescription = boundedVarDesc;
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    public List<SymbolicVariable> getVariables() {
        return variables;
    }

    /**
     * Gets the post conditions description.
     *
     * @return the postConditionsDescription
     */
    public FormalPropertyDescription getPostConditionsDescription() {
        return postConditionsDescription;
    }

    /**
     * Gets the pre conditions description.
     *
     * @return the preConditionsDescirption
     */
    public FormalPropertyDescription getPreConditionsDescription() {
        return preConditionsDescription;
    }

    /**
     * Gets the bounded var description.
     *
     * @return the bounded var description
     */
    public FormalPropertyDescription getBoundedVarDescription() {
        return boundedVarDescription;
    }

    /**
     * Sets the name.
     *
     * @param nameString the new name
     */
    public void setName(final String nameString) {
        this.name = nameString;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((boundedVarDescription == null) ? 0
                : boundedVarDescription.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((postConditionsDescription == null) ? 0
                : postConditionsDescription.hashCode());
        result = prime * result + ((preConditionsDescription == null) ? 0
                : preConditionsDescription.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final PropertyDescription other = (PropertyDescription) obj;
        if (boundedVarDescription == null) {
            if (other.boundedVarDescription != null) {
                return false;
            }
        } else if (!boundedVarDescription.equals(other.boundedVarDescription)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (postConditionsDescription == null) {
            if (other.postConditionsDescription != null) {
                return false;
            }
        } else if (!postConditionsDescription.equals(other.postConditionsDescription)) {
            return false;
        }
        if (preConditionsDescription == null) {
            if (other.preConditionsDescription != null) {
                return false;
            }
        } else if (!preConditionsDescription.equals(other.preConditionsDescription)) {
            return false;
        }
        return true;
    }

    public void addVariable(final SymbolicVariable newVar) {
        variables.add(newVar);
    }
}
