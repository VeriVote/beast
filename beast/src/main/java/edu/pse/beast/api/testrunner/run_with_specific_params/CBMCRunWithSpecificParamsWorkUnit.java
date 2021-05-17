package edu.pse.beast.api.testrunner.run_with_specific_params;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.pse.beast.api.CBMCTestCallback;
import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.specificcbmcrun.SpecificCBMCRunParametersInfo;
import edu.pse.beast.api.testrunner.CBMCCodeFileData;
import edu.pse.beast.api.testrunner.propertycheck.processes.process_handler.CBMCProcessHandler;
import edu.pse.beast.api.testrunner.threadpool.WorkUnit;
import edu.pse.beast.api.testrunner.threadpool.WorkUnitState;

public class CBMCRunWithSpecificParamsWorkUnit implements WorkUnit {
	private WorkUnitState state;
	private SpecificCBMCRunParametersInfo paramInfo;
	private CBMCCodeFileData cbmcCodeFileData;
	private CBMCProcessHandler processStarter;
	private CBMCTestCallback cb;
	private String uuid = UUID.randomUUID().toString();
	private CodeGenOptions codeGenOptions;
	private Process process;

	public CBMCRunWithSpecificParamsWorkUnit(
			CBMCProcessHandler processStarter) {
		this.processStarter = processStarter;
		this.state = WorkUnitState.CREATED;
	}

	public void initialize(SpecificCBMCRunParametersInfo paramInfo,
			CBMCCodeFileData cbmcCodeFileData, CBMCTestCallback cb,
			CodeGenOptions codeGenOptions) {
		this.paramInfo = paramInfo;
		this.cbmcCodeFileData = cbmcCodeFileData;
		this.cb = cb;
		this.codeGenOptions = codeGenOptions;
		state = WorkUnitState.INITIALIZED;
	}
	
	public void setCb(CBMCTestCallback cb) {
		this.cb = cb;
	}

	@Override
	public void doWork() {
		state = WorkUnitState.WORKED_ON;
		cb.onPropertyTestStart(paramInfo.getDescr(), null, paramInfo.getV(),
				paramInfo.getC(), paramInfo.getS(), uuid);
		try {
			process = processStarter.startCheckForParam(null, paramInfo.getV(),
					paramInfo.getC(), paramInfo.getS(), uuid, cb,
					cbmcCodeFileData.getFile(), paramInfo.getDescr()
							.getLoopBoundHandler().getLoopBoundsAsList(),
					codeGenOptions);
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(process.getInputStream()));
			String line;
			List<String> cbmcOutput = new ArrayList<>();

			try {
				while ((line = reader.readLine()) != null) {
					cb.onPropertyTestRawOutput(null, paramInfo.getDescr(), null,
							paramInfo.getS(), paramInfo.getC(),
							paramInfo.getV(), uuid, line);
					cbmcOutput.add(line);
				}
			} catch (IOException e) {
				// TODO errorhandling
				e.printStackTrace();
			}
			cb.onPropertyTestRawOutputComplete(paramInfo.getDescr(), null,
					paramInfo.getS(), paramInfo.getC(), paramInfo.getV(), uuid,
					cbmcOutput);
			state = WorkUnitState.FINISHED;
			process.destroy();

			cb.onPropertyTestFinished(paramInfo.getDescr(), null,
					paramInfo.getS(), paramInfo.getC(), paramInfo.getV(), uuid);
		} catch (IOException e) {
			e.printStackTrace();
			state = WorkUnitState.FINISHED;
			process.destroy();
		}
	}

	@Override
	public void addedToQueue() {
		state = WorkUnitState.ON_QUEUE;
	}

	@Override
	public String getUUID() {
		return uuid;
	}

	@Override
	public void interrupt() {
		state = WorkUnitState.STOPPED;
		process.destroy();
	}

	@Override
	public WorkUnitState getState() {
		return state;
	}

}
