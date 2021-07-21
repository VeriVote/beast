package edu.pse.beast.api.descr.property_description;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.pse.beast.api.codegen.cbmc.SymbolicCBMCVar;

/**
 * The Class PreAndPostConditionsDescription.
 *
 * @author Niels Hanselmann
 */
public final class PreAndPostConditionsDescription {

    private String uuid;

    /** The name. */
    private String name;

    private List<SymbolicCBMCVar> cbmcVariables = new ArrayList<>();

    /** The preconditions description. */
    private final FormalPropertiesDescription preConditionsDescription;

    /** The postconditions description. */
    private final FormalPropertiesDescription postConditionsDescription;

    /** The bounded variable description. */
    private final FormalPropertiesDescription boundedVarDescription;

    /**
     * Instantiates a new pre and post conditions description.
     *
     * @param nameString the name string
     */
    public PreAndPostConditionsDescription(final String nameString) {
        this.uuid = UUID.randomUUID().toString();
        this.name = nameString;
        this.preConditionsDescription = new FormalPropertiesDescription("");
        this.postConditionsDescription = new FormalPropertiesDescription("");
        this.boundedVarDescription = new FormalPropertiesDescription("");
    }

    public PreAndPostConditionsDescription(final String uuidString,
                                           final String nameString,
                                           final List<SymbolicCBMCVar> cbmcVars,
                                           final FormalPropertiesDescription preConditionsDescr,
                                           final FormalPropertiesDescription postConditionsDescr) {
        this.uuid = uuidString;
        this.name = nameString;
        this.cbmcVariables = cbmcVars;
        this.preConditionsDescription = preConditionsDescr;
        this.postConditionsDescription = postConditionsDescr;
        this.boundedVarDescription = new FormalPropertiesDescription("");
    }

    /**
     * Creator with a SymbolicVariableList.
     *
     * @param nameString     name of the PreAndPostConditionsDescription
     * @param preDescr       the preConditionDescription
     * @param postDescr      the postConditionDescription
     * @param boundedVarDesc the boundedVarDescription
     * @param symbVarList    the symbolicVariableList
     */
    public PreAndPostConditionsDescription(final String nameString,
                                           final FormalPropertiesDescription preDescr,
                                           final FormalPropertiesDescription postDescr,
                                           final FormalPropertiesDescription boundedVarDesc) {
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

    public List<SymbolicCBMCVar> getCbmcVariables() {
        return cbmcVariables;
    }

    /**
     * Gets the post conditions description.
     *
     * @return the postConditionsDescription
     */
    public FormalPropertiesDescription getPostConditionsDescription() {
        return postConditionsDescription;
    }

    /**
     * Gets the pre conditions description.
     *
     * @return the preConditionsDescirption
     */
    public FormalPropertiesDescription getPreConditionsDescription() {
        return preConditionsDescription;
    }

    /**
     * Gets the bounded var description.
     *
     * @return the bounded var description
     */
    public FormalPropertiesDescription getBoundedVarDescription() {
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
        final PreAndPostConditionsDescription other = (PreAndPostConditionsDescription) obj;
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

    public void addCBMCVar(final SymbolicCBMCVar newVar) {
        cbmcVariables.add(newVar);
    }
}
