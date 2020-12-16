package edu.pse.beast.api;

import edu.pse.beast.api.testrunner.threadpool.PropertyTestResult;
import edu.pse.beast.codearea.errorhandling.CodeError;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;

public interface BEASTCallback {
	public default void onTestStarted() {
	}

	public default void onError() {
	}

	public default void onElectionCodeError(CodeError err) {
	}

	public default void onTestFinished() {
	}

	public default void onPropertyCodeError(CodeError err, int propertyIdx) {
	}

	public default void onTestStopped() {
	}

	// TODO also pass raw output
	public default void onPropertyTestResult(ElectionDescription electionDescription,
			PreAndPostConditionsDescription preAndPostConditionsDescription, int v, int c, int s,
			boolean verificationSuccess) {
	}

	public default void onPropertyTestResult(ElectionDescription electionDescription,
			PreAndPostConditionsDescription preAndPostConditionsDescription, int v, int c, int s,
			PropertyTestResult result) {
	}

	public default void onPropertyTestStart(ElectionDescription description,
			PreAndPostConditionsDescription propertyDescr, int s, int c, int v, String uuid) {

	}

	public default void onPropertyTestRawOutput(ElectionDescription description,
			PreAndPostConditionsDescription propertyDescr, int s, int c, int v, String uuid, String output) {

	}
}
