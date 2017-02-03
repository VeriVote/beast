package edu.pse.beast.propertychecker;

import edu.pse.beast.datatypes.FailureExample;
import edu.pse.beast.highlevel.ResultPresenterElement;
import edu.pse.beast.toolbox.ErrorLogger;

public class CBMC_Result extends Result {

	@Override
	public void presentTo(ResultPresenterElement presenter) {
		if (!isFinished()) {
			ErrorLogger.log("Result isn't ready yet");
			return;
		} else if (isTimedOut()) {
			System.out.println("timeout");
//			presenter.presentTimeOut();
		} else if (!isValid()) {
			System.out.println("other tpye of failure");
			presenter.presentFailure(getError());
		} else if (isSuccess()) {
			System.out.println("success");
//			presenter.presentSuccess();
		} else {
		    //TODO implement here real counterexample
			System.out.println("counter example is sent to the displayer here ");
			//presenter.presentFailureExample(new FailureExample(null, null, null, null, null));
		}
	}
}
