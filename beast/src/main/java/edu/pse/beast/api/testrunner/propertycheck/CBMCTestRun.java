package edu.pse.beast.api.testrunner.propertycheck;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.pse.beast.api.CBMCTestCallback;
import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.codegen.loopbounds.CodeGenLoopBoundHandler;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.testrunner.CBMCCodeFileData;
import edu.pse.beast.api.testrunner.propertycheck.jsonoutput.CBMCJsonOutputHandler;
import edu.pse.beast.api.testrunner.threadpool.WorkUnitState;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.gui.runs.CBMCTestRunGuiController;
import edu.pse.beast.gui.testconfigeditor.testconfig.cbmc.CBMCTestConfiguration;

public class CBMCTestRun implements CBMCTestCallback {

	private CElectionDescription descr;
	private PreAndPostConditionsDescription propDescr;

	private CBMCPropertyCheckWorkUnit workUnit;

	private int V;
	private int S;
	private int C;

	private CodeGenOptions codeGenOptions;

	private String loopboundList;

	private CBMCCodeFileData cbmcCodeFile;

	private List<String> testRunLogs = new ArrayList<>();

	private boolean descrChanged = false;
	private boolean propDescrChanged = false;

	private CBMCTestCallback cb;

	private CBMCJsonOutputHandler jsonOutputHandler;
	
	private CBMCTestConfiguration tc;

	public CBMCTestRun(int v, int s, int c, CodeGenOptions codeGenOptions,
			String loopbounds, CBMCCodeFileData cbmcCodeFile,
			CElectionDescription descr,
			PreAndPostConditionsDescription propDescr, CBMCTestConfiguration tc) {
		V = v;
		S = s;
		C = c;
		this.codeGenOptions = codeGenOptions;
		this.cbmcCodeFile = cbmcCodeFile;
		this.descr = descr;
		this.propDescr = propDescr;
		this.loopboundList = loopbounds;
		this.tc = tc;
	}
	
	public CBMCJsonOutputHandler getJsonOutputHandler() {
		return jsonOutputHandler;
	}
	
	public void setJsonOutputHandler(CBMCJsonOutputHandler jsonOutputHandler) {
		this.jsonOutputHandler = jsonOutputHandler;
	}

	@Override
	public void onPropertyTestRawOutput(String sessionUUID,
			CElectionDescription description,
			PreAndPostConditionsDescription propertyDescr, int s, int c, int v,
			String uuid, String output) {
		testRunLogs.add(output);
		if (cb != null)
			cb.onPropertyTestRawOutput(sessionUUID, description, propertyDescr,
					s, c, v, uuid, output);
	}

	@Override
	public void onPropertyTestFinished(CElectionDescription description,
			PreAndPostConditionsDescription propertyDescr, int s, int c, int v,
			String uuid) {

		jsonOutputHandler = new CBMCJsonOutputHandler(description,
				propertyDescr, cbmcCodeFile.getCodeInfo(), s, c, v,
				testRunLogs);

		if (cb != null)
			cb.onPropertyTestFinished(description, propertyDescr, s, c, v,
					uuid);
	}

	@Override
	public void onPropertyTestStart(CElectionDescription description,
			PreAndPostConditionsDescription propertyDescr, int s, int c, int v,
			String uuid) {
		if (cb != null)
			cb.onPropertyTestStart(description, propertyDescr, s, c, v, uuid);
	}

	@Override
	public void onPropertyTestStopped(CElectionDescription descr,
			PreAndPostConditionsDescription propertyDescr, int s, int c, int v,
			String uuid) {
		if (cb != null)
			cb.onPropertyTestStopped(descr, propertyDescr, s, c, v, uuid);
	}

	public void setAndInitializeWorkUnit(CBMCPropertyCheckWorkUnit workUnit) {
		workUnit.initialize(V, S, C, codeGenOptions, loopboundList,
				cbmcCodeFile, descr, propDescr, this);
		this.workUnit = workUnit;
	}

	public CBMCCodeFileData getCbmcCodeFile() {
		return cbmcCodeFile;
	}

	public WorkUnitState getState() {
		if (workUnit == null)
			return WorkUnitState.NO_WORK_UNIT;
		return workUnit.getState();
	}

	public CBMCPropertyCheckWorkUnit getWorkUnit() {
		return workUnit;
	}

	public String getTestOutput() {
		synchronized (testRunLogs) {
			return String.join("\n", testRunLogs);
		}
	}

	public void setTestRunLogs(String testRunLogs) {
		this.testRunLogs = Arrays.asList(testRunLogs.split("\n"));
	}

	public void handleDescrCodeChange() {
		descrChanged = true;
	}

	public boolean isDescrChanged() {
		return descrChanged;
	}

	public void handlePropDescrChanged() {
		propDescrChanged = true;
	}

	public boolean isPropDescrChanged() {
		return propDescrChanged;
	}

	public void updateDataForCheck(CBMCCodeFileData cbmcFile,
			CodeGenOptions codeGenOptions) {
		this.cbmcCodeFile = cbmcFile;
		this.loopboundList = cbmcFile.getCodeInfo().getLoopBoundHandler()
				.generateCBMCString(V, C, S);
		workUnit.updateDataForCheck(cbmcFile, loopboundList, codeGenOptions);
		descrChanged = false;
		propDescrChanged = false;
	}

	public void setCb(CBMCTestCallback cb) {
		this.cb = cb;
	}

	public String getLoopboundList() {
		return loopboundList;
	}

	public void setLoopboundList(String loopboundList) {
		this.loopboundList = loopboundList;
	}

	public CodeGenOptions getCodeGenOptions() {
		return codeGenOptions;
	}

	public void setCodeGenOptions(CodeGenOptions codeGenOptions) {
		this.codeGenOptions = codeGenOptions;
	}

	public int getV() {
		return V;
	}

	public int getS() {
		return S;
	}

	public int getC() {
		return C;
	}

	public void setV(int v) {
		V = v;
	}

	public void setS(int s) {
		S = s;
	}

	public void setC(int c) {
		C = c;
	}
	
	public CElectionDescription getDescr() {
		return descr;
	}
	
	public PreAndPostConditionsDescription getPropDescr() {
		return propDescr;
	}
	
	public CBMCTestConfiguration getTc() {
		return tc;
	}

	public String getStatusString() {
		String status = getState().toString();
		if(getState() == WorkUnitState.FINISHED) {
			if(getJsonOutputHandler().getFoundCounterExample()) {
				status = "Verification failed";
			} else {
				status = "Verification succeded";
			}
		}
		return status;
	}

}
