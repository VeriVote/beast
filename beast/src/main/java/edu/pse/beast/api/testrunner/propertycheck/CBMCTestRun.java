package edu.pse.beast.api.testrunner.propertycheck;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.pse.beast.api.CBMCTestCallback;
import edu.pse.beast.api.codegen.CodeGenOptions;
import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundHandler;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.testrunner.threadpool.WorkUnitState;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.gui.runs.CBMCTestRunGuiController;

public class CBMCTestRun implements CBMCTestCallback {

	private CElectionDescription descr;
	private PreAndPostConditionsDescription propDescr;

	private CBMCPropertyCheckWorkUnit workUnit;

	private int V;
	private int S;
	private int C;

	private CodeGenOptions codeGenOptions;

	private List<LoopBound> loopboundList;

	private CBMCCodeFileData cbmcCodeFile;

	private List<String> testRunLogs = new ArrayList<>();

	// gui stuff:
	// TODO move to gui decorator
	private CBMCTestRunGuiController updateListener;
	private boolean descrChanged = false;
	private boolean propDescrChanged = false;

	public CBMCTestRun(int v, int s, int c, CodeGenOptions codeGenOptions,
			List<LoopBound> loopbounds, CBMCCodeFileData cbmcCodeFile,
			CElectionDescription descr,
			PreAndPostConditionsDescription propDescr) {
		V = v;
		S = s;
		C = c;
		this.codeGenOptions = codeGenOptions;
		this.cbmcCodeFile = cbmcCodeFile;
		this.descr = descr;
		this.propDescr = propDescr;
		this.loopboundList = loopbounds;
	}

	public void setAndInitializeWorkUnit(CBMCPropertyCheckWorkUnit workUnit) {
		workUnit.initialize(V, S, C, codeGenOptions, loopboundList,
				cbmcCodeFile, descr, propDescr, this);
		this.workUnit = workUnit;
	}

	public void setUpdateListener(CBMCTestRunGuiController updateListener) {
		this.updateListener = updateListener;
	}

	public void removeUpdateListener() {
		this.updateListener = null;
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

	private void updateGui() {
		if (updateListener != null)
			updateListener.handleRunUpdate();
	}

	@Override
	public void onPropertyTestAddedToQueue(CElectionDescription description,
			PreAndPostConditionsDescription propertyDescr, int s, int c, int v,
			String uuid) {
		synchronized (testRunLogs) {
			testRunLogs.clear();
		}
		updateGui();
	}

	@Override
	public void onPropertyTestStart(CElectionDescription description,
			PreAndPostConditionsDescription propertyDescr, int s, int c, int v,
			String uuid) {
		updateGui();
	}

	@Override
	public void onPropertyTestRawOutput(String sessionUUID,
			CElectionDescription description,
			PreAndPostConditionsDescription propertyDescr, int s, int c, int v,
			String uuid, String output) {
		synchronized (testRunLogs) {
			testRunLogs.add(output);
		}
		updateGui();
	}

	@Override
	public void onPropertyTestFinished(CElectionDescription description,
			PreAndPostConditionsDescription propertyDescr, int s, int c, int v,
			String uuid) {
		updateGui();
	}

	public void handleDescrCodeChange() {
		descrChanged = true;
		updateGui();
	}

	public boolean isDescrChanged() {
		return descrChanged;
	}

	public void handlePropDescrChanged() {
		propDescrChanged = true;
		updateGui();
	}

	public boolean isPropDescrChanged() {
		return propDescrChanged;
	}

	public void updateDataForCheck(CBMCCodeFileData cbmcFile,
			List<LoopBound> loopBounds, CodeGenOptions codeGenOptions) {
		workUnit.updateDataForCheck(cbmcFile, loopBounds, codeGenOptions);
		descrChanged = false;
		propDescrChanged = false;
		updateGui();
	}

	public List<LoopBound> getLoopboundList() {
		return loopboundList;
	}

	public void setLoopboundList(List<LoopBound> loopboundList) {
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

}
