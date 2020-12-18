package edu.pse.beast.api;

import java.io.File;

import edu.pse.beast.api.testrunner.propertycheck.PropertyTestResult;
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

	public default void onTestFileCreated(ElectionDescription description,
			PreAndPostConditionsDescription propertyDescr, int v, int c, int s, String uuid, File cbmcFile) {

	}
}
