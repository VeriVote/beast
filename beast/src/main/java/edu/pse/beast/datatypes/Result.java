package edu.pse.beast.datatypes;

import java.util.List;

import edu.pse.beast.highlevel.ResultInterface;
import edu.pse.beast.highlevel.ResultPresenterElement;

public abstract class Result implements ResultInterface {

    private boolean finished = false;
    private boolean successfull = false;
    private boolean valid = false;

    private List<String> result;


    /**
     * 
     * @return if the result is in a state that it can be presented
     */
    public boolean readyToPresent() {
        return finished;
    }

    /**
     * Presents the result of the check.
     * Every class that extends this class has to implement it for itself.
     * @param presenter the presentable where it is supposed to be presented on
     */
    public abstract void presentTo(ResultPresenterElement presenter);

    /**
     * to be set when the checking for this result is completed
     */
    public void setFinished() {
        finished = true;
    }

    /**
     * to be set when the checking was completed and there were no errors during
     * the checking
     */
    public void setValid() {
        valid = true;
    }

    /**
     * sets the result of this object, so it can be displayed later.
     * @param result the result of the check that should be stored in this result object
     */
    public void setOutput(List<String> result) {
        this.result = result;
    }
}
