package edu.pse.beast.api;

import edu.pse.beast.codearea.errorhandling.CodeError;

public interface BEASTCallback {
	public void onTestStarted();
	public void onError();
	public void onElectionCodeError(CodeError err);
	public void onTestFinished();
	public void onPropertyCodeError(CodeError err, int propertyIdx);
	public void onTestStopped();
}
