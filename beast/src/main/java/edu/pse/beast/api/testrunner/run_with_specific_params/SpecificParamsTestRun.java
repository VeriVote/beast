package edu.pse.beast.api.testrunner.run_with_specific_params;

import edu.pse.beast.api.CBMCTestCallback;
import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.specificcbmcrun.SpecificCBMCRunParametersInfo;
import edu.pse.beast.api.testrunner.CBMCCodeFileData;

public class SpecificParamsTestRun implements CBMCTestCallback {
	private CBMCRunWithSpecificParamsWorkUnit workUnit;
	private SpecificCBMCRunParametersInfo paramInfo;
	private CBMCCodeFileData cbmcCodeFileData;
	private CodeGenOptions codeGenOptions;

	public SpecificParamsTestRun(SpecificCBMCRunParametersInfo paramInfo,
			CBMCCodeFileData cbmcCodeFileData, CodeGenOptions codeGenOptions) {
		this.paramInfo = paramInfo;
		this.cbmcCodeFileData = cbmcCodeFileData;
		this.codeGenOptions = codeGenOptions;
	}

	public void setWorkUnit(CBMCRunWithSpecificParamsWorkUnit workUnit) {
		this.workUnit = workUnit;
		workUnit.initialize(paramInfo, cbmcCodeFileData, this, codeGenOptions);
	}

}
