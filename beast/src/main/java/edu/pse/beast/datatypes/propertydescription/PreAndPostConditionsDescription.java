package edu.pse.beast.datatypes.propertydescription;

import java.util.List;

import edu.pse.beast.toolbox.antlr.booleanexp.generateast.BooleanExpScope;

/**
 * The Class PreAndPostConditionsDescription.
 *
 * @author Niels Hanselmann
 */
public final class PreAndPostConditionsDescription {
	/** The Constant PRIME. */
	private static final int PRIME = 31;

	/** The name. */
	private String name;

	/** The symbolic variable list. */
	private final SymbolicVariableList symbolicVariableList;

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
		this.name = nameString;
		this.symbolicVariableList = new SymbolicVariableList();
		this.preConditionsDescription = new FormalPropertiesDescription("");
		this.postConditionsDescription = new FormalPropertiesDescription("");
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
	public PreAndPostConditionsDescription(final String nameString, final FormalPropertiesDescription preDescr,
			final FormalPropertiesDescription postDescr, final FormalPropertiesDescription boundedVarDesc,
			final SymbolicVariableList symbVarList) {
		this.name = nameString;
		this.preConditionsDescription = preDescr;
		this.postConditionsDescription = postDescr;
		this.boundedVarDescription = boundedVarDesc;
		this.symbolicVariableList = symbVarList;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Gets the symbolic variables as list.
	 *
	 * @return the SymbolicVariableList as a List
	 */
	public List<SymbolicVariable> getSymbolicVariablesAsList() {
		return symbolicVariableList.getSymbolicVariables();
	}

	/**
	 * Gets the symbolic variables cloned.
	 *
	 * @return the symbolic variables cloned
	 */
	public List<SymbolicVariable> getSymbolicVariablesCloned() {
		return symbolicVariableList.getSymbolicVariablesCloned();
	}

	/**
	 * Gets the sym var list.
	 *
	 * @return the symbolicVariableList as the datatype
	 */
	public SymbolicVariableList getSymVarList() {
		return symbolicVariableList;
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
		int result = 1;
		result = PRIME * result + ((boundedVarDescription == null) ? 0 : boundedVarDescription.hashCode());
		result = PRIME * result + ((name == null) ? 0 : name.hashCode());
		result = PRIME * result + ((postConditionsDescription == null) ? 0 : postConditionsDescription.hashCode());
		result = PRIME * result + ((preConditionsDescription == null) ? 0 : preConditionsDescription.hashCode());
		result = PRIME * result + ((symbolicVariableList == null) ? 0 : symbolicVariableList.hashCode());
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
		if (symbolicVariableList == null) {
			if (other.symbolicVariableList != null) {
				return false;
			}
		} else if (!symbolicVariableList.equals(other.symbolicVariableList)) {
			return false;
		}
		return true;
	}

	public BooleanExpScope getSymVarsAsScope() {
		BooleanExpScope declaredVars = new BooleanExpScope();
		getSymbolicVariablesAsList().forEach(v -> {
			declaredVars.addTypeForId(v.getId(), v.getInternalTypeContainer());
		});
		return declaredVars;
	}
}
