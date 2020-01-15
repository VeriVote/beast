package edu.pse.beast.datatypes.propertydescription;

import java.util.List;

/**
 *
 * @author Niels Hanselmann
 */
public class PreAndPostConditionsDescription {
    private String name;
    private final SymbolicVariableList symbolicVariableList;
    private final FormalPropertiesDescription preConditionsDescription;
    private final FormalPropertiesDescription postConditionsDescription;
    private final FormalPropertiesDescription boundedVarDescription;

    public PreAndPostConditionsDescription(final String nameString) {
        this.name = nameString;
        this.symbolicVariableList = new SymbolicVariableList();
        this.preConditionsDescription = new FormalPropertiesDescription("");
        this.postConditionsDescription = new FormalPropertiesDescription("");
        this.boundedVarDescription = new FormalPropertiesDescription("");
    }

    /**
     * Creator with a SymbolicVariableList.
     *
     * @param nameString            name of the PreAndPostConditionsDescription
     * @param preDescr              the preConditionDescription
     * @param postDescr             the postConditionDescription
     * @param boundedVarDesc        the boundedVarDescription
     * @param symbVarList           the symbolicVariableList
     */
    public PreAndPostConditionsDescription(final String nameString,
                                           final FormalPropertiesDescription preDescr,
                                           final FormalPropertiesDescription postDescr,
                                           final FormalPropertiesDescription boundedVarDesc,
                                           final SymbolicVariableList symbVarList) {
        this.name = nameString;
        this.preConditionsDescription = preDescr;
        this.postConditionsDescription = postDescr;
        this.boundedVarDescription = boundedVarDesc;
        this.symbolicVariableList = symbVarList;
    }

    public String getName() {
        return this.name;
    }

    /**
     *
     * @return the SymbolicVariableList as a List
     */
    public List<SymbolicVariable> getSymbolicVariablesAsList() {
        return symbolicVariableList.getSymbolicVariables();
    }

    public List<SymbolicVariable> getSymbolicVariablesCloned() {
        return symbolicVariableList.getSymbolicVariablesCloned();
    }

    /**
     *
     * @return the symbolicVariableList as the datatype
     */
    public SymbolicVariableList getSymVarList() {
        return symbolicVariableList;
    }

    /**
     *
     * @return the postConditionsDescription
     */
    public FormalPropertiesDescription getPostConditionsDescription() {
        return postConditionsDescription;
    }

    /**
     *
     * @return the preConditionsDescirption
     */
    public FormalPropertiesDescription getPreConditionsDescription() {
        return preConditionsDescription;
    }

    public FormalPropertiesDescription getBoundedVarDescription() {
        return boundedVarDescription;
    }

    public void setName(final String nameString) {
        this.name = nameString;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((boundedVarDescription == null)
                        ? 0 : boundedVarDescription.hashCode());
        result = prime * result + ((name == null)
                ? 0 : name.hashCode());
        result = prime * result
                + ((postConditionsDescription == null)
                        ? 0 : postConditionsDescription.hashCode());
        result = prime * result
                + ((preConditionsDescription == null)
                        ? 0 : preConditionsDescription.hashCode());
        result = prime * result
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
        PreAndPostConditionsDescription other
              = (PreAndPostConditionsDescription) obj;
        if (boundedVarDescription == null) {
            if (other.boundedVarDescription != null) {
                return false;
            }
        } else if (!boundedVarDescription
                .equals(other.boundedVarDescription)) {
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
        } else if (!postConditionsDescription
                .equals(other.postConditionsDescription)) {
            return false;
        }
        if (preConditionsDescription == null) {
            if (other.preConditionsDescription != null) {
                return false;
            }
        } else if (!preConditionsDescription
                .equals(other.preConditionsDescription)) {
            return false;
        }
        if (symbolicVariableList == null) {
            if (other.symbolicVariableList != null) {
                return false;
            }
        } else if (!symbolicVariableList
                .equals(other.symbolicVariableList)) {
            return false;
        }
        return true;
    }
}
