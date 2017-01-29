package edu.pse.beast.propertychecker;

import edu.pse.beast.highlevel.FailureExample;
import edu.pse.beast.highlevel.ResultPresenterElement;
import edu.pse.beast.toolbox.ErrorLogger;

public class CBMC_Result extends Result {

	@Override
	public void presentTo(ResultPresenterElement presenter) {
		if (!isFinished()) {
			ErrorLogger.log("Result isn't ready yet");
			return;
		} else if (isTimedOut()) {
			presenter.presentTimeOut();
		} else if (!isValid()) {
			presenter.presentFailure();
		} else if (isSuccess()) {
			presenter.presentSuccess();
		} else {
		    //TODO implement here real counterexample
			presenter.presentFailureExample(new FailureExample(getResult()));
		}
	}
}
