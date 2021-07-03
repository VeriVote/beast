package edu.pse.beast.api.testrunner.propertycheck.processes.process_handler;

import java.io.File;
import java.io.IOException;

import edu.pse.beast.api.CBMCTestCallback;
import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;

public interface CBMCProcessHandler {

	public CBMCProcessStarterType getType();
	

	// at this point, the code file is final. This means the following wont
	// change
	// for this run unless we update it anyways::
	// - names of the constants for V, C, S
	// - loop bound indices
	// we could update the V, C, S amounts if we wanted.
	// however, in this case, just create a new Propertycheck imo
	public Process startCheckForParam(String sessionUUID, int V, int C, int S,
			String uuid, CBMCTestCallback cb, File cbmcFile,
			String loopBounds, CodeGenOptions codeGenOptions)
			throws IOException;
	
	public void endProcess(Process p);

}
