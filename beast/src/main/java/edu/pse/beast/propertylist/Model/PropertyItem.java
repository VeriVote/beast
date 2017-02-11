package edu.pse.beast.propertylist.Model;

import java.util.List;
import java.util.UUID;

import edu.pse.beast.datatypes.FailureExample;
import edu.pse.beast.datatypes.propertydescription.FormalPropertiesDescription;
import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariableList;
import edu.pse.beast.highlevel.ResultPresenterElement;
import edu.pse.beast.propertylist.Model.ResultType;

/**
 * This class wraps property descriptions of type PostAndPrePropertiesDescription. Further data is the test status and the result
 * of the property analysis. Also the counter example of the analysis is stored here.
 * @author Justin
 */
public class PropertyItem implements ResultPresenterElement {

    private PostAndPrePropertiesDescription description;
    private Boolean willBeTested;
    private ResultType resultType;
    private List<String> error;
    private FailureExample example;

    /**
     * Constructor for the class
     * @param descr The property description to add to the property item
     * @param testStatus Sets whether the property will be analyzed in the next check
     */
    public PropertyItem(PostAndPrePropertiesDescription descr, Boolean testStatus) {
        description = descr;
        willBeTested = testStatus;
        resultType = ResultType.UNTESTED;
    }

    /**
     * Constructor with one parameter sets the test status to true.
     * @param descr The property description to add to the property item
     */
    public PropertyItem(PostAndPrePropertiesDescription descr) {
        this(descr, true);
    }

    /**
     * Constructor without parameters returns a pretty unique name for the property description and a blank description.
     */
    public PropertyItem() {
        this(new PostAndPrePropertiesDescription(UUID.randomUUID().toString()), false);
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
    public void presentFailureExample(FailureExample example) {
        resultType = ResultType.FAILUREEXAMPLE;
        this.setExample(example);
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

    public void setTestStatus(boolean newStatus) {
        willBeTested = newStatus;
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

}
