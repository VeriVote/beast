package edu.pse.beast.propertychecker;

import edu.pse.beast.highlevel.ResultPresenterElement;
import edu.pse.beast.toolbox.ErrorLogger;

public class UnprocessedCBMCResult extends CBMCResult {
	
	@Override
	public void presentTo(ResultPresenterElement presenter) {
		// we dont have to present this result, because it will get used
		//internally
		ErrorLogger.log("There was an attempt to present an UnprocessedResult. This should + "
				+ "never happen");
	}
	
}
