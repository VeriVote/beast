package edu.pse.beast.api;

import java.io.File;
import java.util.List;

import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.codearea.errorhandling.CodeError;
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

	public default void onPropertyTestStart(CElectionDescription description,
			PreAndPostConditionsDescription propertyDescr, int s, int c, int v, String uuid) {

	}

	public default void onPropertyTestRawOutput(CElectionDescription description,
			PreAndPostConditionsDescription propertyDescr, int s, int c, int v, String uuid, String output) {

	}

	public default void onTestFileCreated(CElectionDescription description,
			PreAndPostConditionsDescription propertyDescr, int v, int c, int s, String uuid, File cbmcFile) {

	}

	public default void onCompleteCommand(CElectionDescription description,
			PreAndPostConditionsDescription propertyDescr, int v, int c, int s, String uuid, String completeCommand) {
	}

	public default void onException(Exception ex) throws Exception {
	}

	public default void onPropertyTestFinished(CElectionDescription description,
			PreAndPostConditionsDescription propertyDescr, int s, int c, int v, String uuid) {

	}

	public default void onPropertyTestRawOutputComplete(CElectionDescription description,
			PreAndPostConditionsDescription propertyDescr, int s, int c, int v, String uuid, List<String> cbmcOutput) {

	}
}
