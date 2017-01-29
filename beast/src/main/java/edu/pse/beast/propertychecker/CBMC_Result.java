package edu.pse.beast.propertychecker;

import edu.pse.beast.highlevel.FailureExample;
import edu.pse.beast.highlevel.ResultPresenterElement;
import edu.pse.beast.toolbox.ErrorLogger;

public class CBMC_Result extends Result {

	@Override
	public void presentTo(ResultPresenterElement presenter) {
		if (!finished) {
			ErrorLogger.log("Result isn't ready yet");
			return;
		} else if (timeOut) {
			presenter.presentTimeOut();
		} else if (!valid) {
			presenter.presentFailure();
		} else if (success) {
			presenter.presentSuccess();
		} else {
			presenter.presentFailureExample(new FailureExample(result));
		}
	}

}
