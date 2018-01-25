package edu.pse.beast.propertylist.Model;

import java.util.List;
import java.util.UUID;

import edu.pse.beast.datatypes.FailureExample;
import edu.pse.beast.datatypes.propertydescription.FormalPropertiesDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariableList;
import edu.pse.beast.highlevel.ResultPresenterElement;
import edu.pse.beast.propertychecker.Result;

/**
 * This class wraps property descriptions of type
 * PreAndPostConditionsDescription. Further data is the test status and the
 * result of the property analysis. Also the counter example of the analysis is
 * stored here.
 * 
 * @author Justin
 */
public class PropertyItem implements ResultPresenterElement {

	private PreAndPostConditionsDescription description;
	private boolean willBeTested;
	private ResultType resultType;
	private List<String> error;
	private FailureExample example;
	private boolean willBeMarginComputed;
	private Result result;

	public Result getResult() {
		return result;
	}

	/**
	 * Constructor for the class
	 * 
	 * @param descr
	 *            The property description to add to the property item
	 * @param testStatus
	 *            Sets whether the property will be analyzed in the next check
	 */
	public PropertyItem(PreAndPostConditionsDescription descr, boolean testStatus, boolean willBeMarginComputed) {
		description = descr;
		willBeTested = testStatus;

		this.willBeMarginComputed = willBeMarginComputed;

		resultType = ResultType.UNTESTED;
	}

	/**
	 * Constructor with one parameter sets the test status to true.
	 * 
	 * @param descr
	 *            The property description to add to the property item
	 */
	public PropertyItem(PreAndPostConditionsDescription descr) {
		this(descr, true, false);
	}

	/**
	 * Constructor without parameters returns a pretty unique name for the property
	 * description and a blank description.
	 */
	public PropertyItem() {
		this(new PreAndPostConditionsDescription(UUID.randomUUID().toString()), false, false);
	}

	@Override
	public void presentCanceled(boolean isTimeout) {
		if (isTimeout) {
			resultType = ResultType.TIMEOUT;
		} else {
			resultType = ResultType.CANCEL;
		}
	}

	@Override
	public void presentSuccess() {
		resultType = ResultType.SUCCESS;
	}

	@Override
	public void presentFailure(List<String> error) {
		resultType = ResultType.FAILURE;
		this.setError(error);
	}

	@Override
	public void presentFailureExample(Result result) {
		resultType = ResultType.FAILUREEXAMPLE;
		this.result = result;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || this.getClass() != o.getClass()) {
			return false;
		}
		if (this.description.getName().equals(((PropertyItem) o).description.getName())) {
			return true;
		} else {
			return false;
		}
	}

	// getter and setter
	public PreAndPostConditionsDescription getDescription() {
		return description;
	}

	public void setDescription(PreAndPostConditionsDescription descr) {
		this.description = descr;
	}

	public void setDescription(String newName, FormalPropertiesDescription preCond,
			FormalPropertiesDescription postCond, SymbolicVariableList symVars) {
		this.description = new PreAndPostConditionsDescription(newName, preCond, postCond, symVars);
	}

	public void setDescriptionName(String newName) {
		this.description = new PreAndPostConditionsDescription(newName, this.description.getPreConditionsDescription(),
				this.description.getPostConditionsDescription(), this.description.getSymVarList());
	}

	public String getDescriptionName() {
		return this.description.getName();
	}

	public void setTestStatus(boolean newStatus) {
		willBeTested = newStatus;
	}

	public void setMarginStatus(boolean newStatus) {
		willBeMarginComputed = newStatus;
	}

	public Boolean getTestStatus() {
		return willBeTested;
	}

	public ResultType getResultType() {
		return resultType;
	}

	public void setResultType(ResultType resultType) {
		this.resultType = resultType;
	}

	public FailureExample getExample() {
		return example;
	}

	public void setExample(FailureExample example) {
		this.example = example;
	}

	public List<String> getError() {
		return error;
	}

	public void setError(List<String> error) {
		this.error = error;
	}

	public boolean getMarginStatus() {
		return willBeMarginComputed;
	}

	@Override
	public void present(Result resultToPresent) {
		this.result = resultToPresent;
		this.resultType = ResultType.FAILUREEXAMPLE;
	}

}
