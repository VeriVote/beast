package edu.pse.beast.api.testrunner.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.pse.beast.api.BEASTCallback;
import edu.pse.beast.datatypes.electioncheckparameter.ElectionCheckParameter;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;

public class PropertyCheckWorkSupplier implements WorkSupplier {

	private ElectionDescription descrs;
	private List<PreAndPostConditionsDescription> propDescrs;
	private ElectionCheckParameter parameter;
	private BEASTCallback cb;
	private List<PropertyCheckWorkUnit> workQueue = new ArrayList<>();
	private int workIdx = 0;

	public PropertyCheckWorkSupplier(ElectionDescription descrs, List<PreAndPostConditionsDescription> propDescrs,
			ElectionCheckParameter parameter, BEASTCallback cb) {
		this.descrs = descrs;
		this.propDescrs = propDescrs;
		this.parameter = parameter;
		this.cb = cb;
		fillQueue();
	}

	private void fillQueue() {
		synchronized (workQueue) {
			for (PreAndPostConditionsDescription propDescr : propDescrs) {
				for (int s : parameter.getRangeOfSeats()) {
					for (int c : parameter.getRangeofCandidates()) {
						for (int v : parameter.getRangeOfVoters()) {
							workQueue.add(new PropertyCheckWorkUnit(descrs, propDescr, v, c, s,
									UUID.randomUUID().toString(), cb));
						}
					}
				}
			}
		}
	}

	@Override
	public synchronized WorkUnit getWorkUnit() {
		if(workIdx == workQueue.size()) return null;
		return workQueue.get(workIdx++);
	}

	@Override
	public synchronized boolean isFinished() {		
		// TODO workIdx-generated method stub
		if (workIdx < workQueue.size()) return false;
		for(PropertyCheckWorkUnit work : workQueue) {
			if(!work.isFinished()) return false;
		}
		return true;
	}

	public void waitSync() throws InterruptedException {
		while (!isFinished()) {
			Thread.sleep(200);
		}
	}

}
