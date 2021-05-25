package edu.pse.beast.api.testrunner.propertycheck.processes.process_handler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.pse.beast.api.CBMCTestCallback;
import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.codegen.loopbounds.CodeGenLoopBoundHandler;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.toolbox.CCodeHelper;
import edu.pse.beast.toolbox.ErrorForUserDisplayer;
import edu.pse.beast.toolbox.FileLoader;
import edu.pse.beast.toolbox.SuperFolderFinder;
import edu.pse.beast.toolbox.UnifiedNameContainer;

public class CBMCProcessHandlerLinux implements CBMCProcessHandler {
	private String RELATIVE_PATH_TO_CBMC_64 = "/linux/cbmcLin/cbmc";

	@Override
	public CBMCProcessStarterType getType() {
		return CBMCProcessStarterType.LINUX;
	}

	@Override
	public Process startCheckForParam(String sessionUUID, int V, int C, int S,
			String uuid, CBMCTestCallback cb, File cbmcFile,
			String loopBounds, CodeGenOptions codeGenOptions)
			throws IOException {
		File cbmcProgFile = new File(
				SuperFolderFinder.getSuperFolder() + RELATIVE_PATH_TO_CBMC_64);

		String arguments = CBMCCommandHelper.getArgumentsForCBMCJsonOutput(
				cbmcFile, codeGenOptions, loopBounds, V, C, S);

		return Runtime.getRuntime().exec(arguments, null, cbmcProgFile);
	}

}
