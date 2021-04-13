package edu.pse.beast.api.testrunner.propertycheck;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.pse.beast.api.BEASTCallback;
import edu.pse.beast.api.codegen.CodeGenOptions;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundHandler;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.testrunner.threadpool.WorkSupplier;
import edu.pse.beast.api.testrunner.threadpool.WorkUnit;
import edu.pse.beast.datatypes.electioncheckparameter.ElectionCheckParameter;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.electiontest.cbmb.CBMCCodeFileGenerator;

public class PropertyCheckWorkSupplier implements WorkSupplier {

	private CElectionDescription descrs;
	private List<PreAndPostConditionsDescription> propDescrs;
	private ElectionCheckParameter parameter;
	private BEASTCallback cb;
	private List<PropertyCheckWorkUnit> workQueue = new ArrayList<>();
	private int workIdx = 0;
	private CBMCProcessStarter processStarter;
	private String sessionUUID;

	public PropertyCheckWorkSupplier(
			String sessionUUID,
			CElectionDescription descrs,
			List<PreAndPostConditionsDescription> propDescrs,
			ElectionCheckParameter parameter, BEASTCallback cb,
			CBMCProcessStarter processStarter, CodeGenOptions codeGenOptions) {
		this.sessionUUID = sessionUUID;
		this.descrs = descrs;
		this.propDescrs = propDescrs;
		this.parameter = parameter;
		this.cb = cb;
		this.processStarter = processStarter;
		fillQueue(codeGenOptions);
	}

	private void fillQueue(CodeGenOptions codeGenOptions) {

		synchronized (workQueue) {
			for (PreAndPostConditionsDescription propDescr : propDescrs) {
				LoopBoundHandler loopBoundHandler = new LoopBoundHandler();
				File cbmcFile = null;
				IOException ioExep = null;
				try {
					cbmcFile = CBMCCodeFileGeneratorNEW.createCodeFileTest(
							descrs, propDescr, codeGenOptions,
							loopBoundHandler);
				} catch (IOException e) {
					// TODO pass exception on to work unit
					// handle exceptions thusly
					ioExep = e;
					continue;
				}

				for (int s : parameter.getRangeOfSeats()) {
					for (int c : parameter.getRangeofCandidates()) {
						for (int v : parameter.getRangeOfVoters()) {
							workQueue.add(new PropertyCheckWorkUnit(sessionUUID,
									descrs, propDescr, v, c, s,
									UUID.randomUUID().toString(), cb,
									this.processStarter, cbmcFile,
									loopBoundHandler, codeGenOptions));
						}
					}
				}
			}
		}
	}

	@Override
	public synchronized WorkUnit getWorkUnit() {
		if (workIdx == workQueue.size())
			return null;
		return workQueue.get(workIdx++);
	}

	@Override
	public boolean isFinished() {
		// TODO workIdx-generated method stub
		if (workIdx < workQueue.size())
			return false;
		for (PropertyCheckWorkUnit work : workQueue) {
			if (!work.isFinished())
				return false;
		}
		return true;
	}

	public void waitSync() throws InterruptedException {
		while (!isFinished()) {
			Thread.sleep(200);
		}
	}

	@Override
	public void interruptAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public void interruptSpecific(String uuid) {
		// TODO Auto-generated method stub

	}

}
