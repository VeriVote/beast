/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.propertychecker;

import java.util.Iterator;
import java.util.List;

import edu.pse.beast.datatypes.ElectionCheckParameter;
import edu.pse.beast.datatypes.descofvoting.ElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.highlevel.ElectionDescriptionSource;
import edu.pse.beast.highlevel.ParameterSource;
import edu.pse.beast.highlevel.ResultInterface;

/**
 *
 * @author Niels & Lukas
 */
public class FactoryController implements Runnable {

	private final ElectionDescription electionDesc;
	private final List<PostAndPrePropertiesDescription> postAndPrePropDescr;
	private final ElectionCheckParameter parmSrc;
	private final List<Result> results;
	private List<CheckerFactory> currentlyRunning;
	private final String checkerID;
	private Thread controllerThread;
	private boolean stopped = false;
	private final int concurrentChecker;

	public FactoryController(ElectionDescription electionDesc,
			List<PostAndPrePropertiesDescription> postAndPrePropDescr, ElectionCheckParameter parmSrc, String checkerID,
			int concurrentChecker) {
		this.electionDesc = electionDesc;
		this.postAndPrePropDescr = postAndPrePropDescr;
		this.parmSrc = parmSrc;
		this.checkerID = checkerID;

		this.results = CheckerFactoryFactory.
		
		
		this.concurrentChecker = 4; // TODO ask options for the amount of
									// concurrent checkers

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	public boolean stopChecking() {
		for (Iterator<CheckerFactory> iterator = currentlyRunning.iterator(); iterator.hasNext();) {
			CheckerFactory toStop = (CheckerFactory) iterator.next();
			toStop.stopChecking();
		}
	}

	public List<ResultInterface> getResults() {
		// TODO Auto-generated method stub
		return null;
	}
}
