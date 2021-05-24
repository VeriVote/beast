package edu.pse.beast.api.testrunner.propertycheck;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.pse.beast.api.CBMCTestCallback;
import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.codegen.loopbounds.CodeGenLoopBoundHandler;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.testrunner.CBMCCodeFileData;
import edu.pse.beast.api.testrunner.propertycheck.processes.process_handler.CBMCProcessHandler;
import edu.pse.beast.api.testrunner.threadpool.WorkUnit;
import edu.pse.beast.api.testrunner.threadpool.WorkUnitState;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;

public class CBMCPropertyCheckWorkUnit implements WorkUnit {
	// fields to start the election check
	CElectionDescription descr;
	PreAndPostConditionsDescription propDescr;
	int v, c, s;
	CBMCCodeFileData cbmcCodeFile;
	private List<LoopBound> loopBounds;
	private CodeGenOptions codeGenOptions;

	// fields to handle the workunit state
	CBMCTestCallback cb;

	String uuid;
	private String sessionUUID;

	CBMCProcessHandler processStarter;
	private Process process;

	private WorkUnitState state;

	public CBMCPropertyCheckWorkUnit(CBMCProcessHandler processStarter,
			String sessionUUID) {
		this.uuid = UUID.randomUUID().toString();
		this.processStarter = processStarter;
		this.sessionUUID = sessionUUID;
		this.state = WorkUnitState.CREATED;
	}

	public void initialize(int v, int s, int c, CodeGenOptions codeGenOptions,
			List<LoopBound> loopBounds, CBMCCodeFileData cbmcCodeFile,
			CElectionDescription descr,
			PreAndPostConditionsDescription propDescr, CBMCTestCallback cb) {
		this.descr = descr;
		this.propDescr = propDescr;
		this.v = v;
		this.c = c;
		this.s = s;
		this.cbmcCodeFile = cbmcCodeFile;
		this.loopBounds = loopBounds;
		this.codeGenOptions = codeGenOptions;
		this.cb = cb;
		this.state = WorkUnitState.INITIALIZED;
	}

	public int getC() {
		return c;
	}

	public int getS() {
		return s;
	}

	public int getV() {
		return v;
	}

	public void setCallback(CBMCTestCallback cb) {
		this.cb = cb;
	}

	public boolean hasCallback() {
		return this.cb != null;
	}

	public void updateDataForCheck(CBMCCodeFileData cbmcFile,
			List<LoopBound> loopBounds, CodeGenOptions codeGenOptions) {
		this.cbmcCodeFile = cbmcFile;
		this.loopBounds = loopBounds;
		this.codeGenOptions = codeGenOptions;
	}

	@Override
	public void doWork() {
		state = WorkUnitState.WORKED_ON;
		cb.onPropertyTestStart(descr, propDescr, s, c, v, uuid);
		try {
			process = processStarter.startCheckForParam(sessionUUID, v, c, s,
					sessionUUID, cb, cbmcCodeFile.getFile(), loopBounds,
					codeGenOptions);
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(process.getInputStream()));
			String line;
			List<String> cbmcOutput = new ArrayList<>();
			try {
				while ((line = reader.readLine()) != null) {
					cb.onPropertyTestRawOutput(sessionUUID, descr, propDescr, s,
							c, v, uuid, line);
					cbmcOutput.add(line);
				}
			} catch (IOException e) {
				// TODO errorhandling
				e.printStackTrace();
			}
			cb.onPropertyTestRawOutputComplete(descr, propDescr, s, c, v, uuid,
					cbmcOutput);
			state = WorkUnitState.FINISHED;
			process.destroy();

			cb.onPropertyTestFinished(descr, propDescr, s, c, v, uuid);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			state = WorkUnitState.FINISHED;
			process.destroy();
		}
	}

	@Override
	public String getUUID() {
		return uuid;
	}

	@Override
	public void interrupt() {
		process.destroyForcibly();
		state = WorkUnitState.STOPPED;
		cb.onPropertyTestStopped(descr, propDescr, s, c, v, uuid);
	}

	@Override
	public void addedToQueue() {
		state = WorkUnitState.ON_QUEUE;
		cb.onPropertyTestAddedToQueue(descr, propDescr, s, c, v, uuid);
	}

	@Override
	public WorkUnitState getState() {
		return state;
	}

	public CBMCCodeFileData getCbmcFile() {
		return cbmcCodeFile;
	}

	public void setCbmcFile(CBMCCodeFileData cbmcFile) {
		this.cbmcCodeFile = cbmcFile;
	}
}
