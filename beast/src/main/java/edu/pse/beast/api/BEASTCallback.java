package edu.pse.beast.api;

import edu.pse.beast.codearea.errorhandling.CodeError;

public interface BEASTCallback {
	public default void onTestStarted() {}
	public default void onError() {}
	public default void onElectionCodeError(CodeError err) {}
	public default void onTestFinished() {}
	public default void onPropertyCodeError(CodeError err, int propertyIdx) {}
	public default void onTestStopped() {}
}
