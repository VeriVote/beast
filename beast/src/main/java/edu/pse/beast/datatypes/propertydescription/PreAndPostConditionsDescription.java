package edu.pse.beast.datatypes.propertydescription;

import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.api.codegen.SymbolicCBMCVar;
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
		this.name = nameString;
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
		int result = 1;
		result = PRIME * result + ((boundedVarDescription == null) ? 0 : boundedVarDescription.hashCode());
		result = PRIME * result + ((name == null) ? 0 : name.hashCode());
		result = PRIME * result + ((postConditionsDescription == null) ? 0 : postConditionsDescription.hashCode());
		result = PRIME * result + ((preConditionsDescription == null) ? 0 : preConditionsDescription.hashCode());
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
		return true;
	}


}
