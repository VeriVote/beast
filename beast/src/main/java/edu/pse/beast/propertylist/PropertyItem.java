package edu.pse.beast.propertylist;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import edu.pse.beast.datatypes.FailureExample;
import edu.pse.beast.datatypes.propertydescription.FormalPropertiesDescription;
import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariableList;
import edu.pse.beast.highlevel.ResultInterface;
import edu.pse.beast.highlevel.ResultPresenterElement;
import edu.pse.beast.propertylist.Model.ResultType;

/**
*
* @author Justin
*/
public class PropertyItem implements ResultPresenterElement {
	
	private PostAndPrePropertiesDescription description;
	private Boolean willBeTested;
	private ResultType resultType;
	private List<String> error;
	private FailureExample example;
	
	public PropertyItem(PostAndPrePropertiesDescription descr, Boolean testStatus) {
		description = descr;
		willBeTested = testStatus;
		resultType = ResultType.UNTESTED;
	}
	public PropertyItem(PostAndPrePropertiesDescription descr) {
		this(descr, true);
	}
	public PropertyItem() {
		this(new PostAndPrePropertiesDescription(UUID.randomUUID().toString()), false);
	}
	
	
	public PostAndPrePropertiesDescription getDescription() {
		return description;
	}
	public void setDescription(PostAndPrePropertiesDescription descr) {
		this.description = descr;
	}
	public void setDescription(String newName, FormalPropertiesDescription preProp,
			FormalPropertiesDescription postProp, SymbolicVariableList symVars) {
		this.description = new PostAndPrePropertiesDescription(newName, preProp, postProp, symVars);
	}
	
	public Boolean willBeTested() {
		return willBeTested;
	}
	public void setTestStatus(boolean newStatus) {
		willBeTested = newStatus;
	}
	public void toggleTestStatus() {
		willBeTested = !willBeTested;
	}

	@Override
	public boolean equals (Object o) {
		if (o == null || this.getClass() != o.getClass()) return false;
		if (this.description.getName().equals(((PropertyItem)o).description.getName())) return true;
		else return false;
	}
	
	
	public ResultType getResultType() {
		return resultType;
	}
	public void setResultType(ResultType resultType) {
		this.resultType = resultType;
	}
	
	
	
	@Override
	public void presentTimeOut() {
		resultType = ResultType.TIMEOUT;
		
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
	public void presentFailureExample(FailureExample example) {
		resultType = ResultType.FAILUREEXAMPLE;
		this.setExample(example);
		
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
	
	
}
