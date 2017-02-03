/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.propertychecker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.highlevel.ElectionDescriptionSource;
import edu.pse.beast.highlevel.ParameterSource;
import edu.pse.beast.highlevel.PostAndPrePropertiesDescriptionSource;
import edu.pse.beast.highlevel.ResultInterface;
import edu.pse.beast.toolbox.ErrorLogger;
import edu.pse.beast.toolbox.TimeOutNotifier;

/**
 *
 * @author Niels & Lukas
 */
public class FactoryController implements Runnable {

	private final ElectionDescriptionSource electionDescSrc;
	private final PostAndPrePropertiesDescriptionSource postAndPrePropDescrSrc;
	private final ParameterSource parmSrc;
	private final List<Result> results;
	private final TimeOutNotifier notifier;

	private final long pollingInterval = 1000;

	private List<CheckerFactory> currentlyRunning;
	private final String checkerID;
	private boolean stopped = false;
	private final int concurrentChecker;

	/**
	 * 
	 * @param electionDescSrc
	 *            the source for the election descriptions
	 * @param postAndPrePropDescrSrc
	 *            the properties to be checked
	 * @param parmSrc
	 *            the parameter
	 * @param checkerID
	 *            the ID of the checker that should be used
	 * @param concurrentChecker
	 *            the amount of concurrent checker to be used
	 */
	public FactoryController(ElectionDescriptionSource electionDescSrc,
			PostAndPrePropertiesDescriptionSource postAndPrePropDescrSrc, ParameterSource parmSrc, String checkerID,
			int concurrentChecker) {
		this.electionDescSrc = electionDescSrc;
		this.postAndPrePropDescrSrc = postAndPrePropDescrSrc;
		this.parmSrc = parmSrc;
		this.checkerID = checkerID;
		this.currentlyRunning = new ArrayList<CheckerFactory>(concurrentChecker);
		
		this.results = CheckerFactoryFactory.getMatchingResult(checkerID,
				postAndPrePropDescrSrc.getPostAndPrePropertiesDescriptions().size());

		this.concurrentChecker = concurrentChecker;

		// start the factorycontroller
		new Thread(this, "FactoryController").start();

		if (parmSrc.getParameter().getTimeout().isActive()) {
			notifier = new TimeOutNotifier(this, parmSrc.getParameter().getTimeout().getDuration());
		} else {
			notifier = null;
		}
	}

	/**
	 * starts the factoryController, so it then starts the needed checker
	 */
	@Override
	public void run() {

		List<PostAndPrePropertiesDescription> properties = postAndPrePropDescrSrc.getPostAndPrePropertiesDescriptions();

		outerLoop: for (int i = 0; i < properties.size(); i++) {
			innerLoop: while (!stopped) {
				if (currentlyRunning.size() < concurrentChecker) {
					CheckerFactory factory = CheckerFactoryFactory.getCheckerFactory(checkerID, this, electionDescSrc,
							properties.get(i), parmSrc, results.get(i));

					
					synchronized (this) {
						currentlyRunning.add(factory);						
					}
					
					new Thread(factory, "CheckerFactory Property " + i).start();


					break innerLoop;
				} else {
					try {
						Thread.sleep(pollingInterval);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

			if (stopped) {
				break outerLoop;
			}
		}
		if (notifier != null) {
			// if the notifier thread is still active, we stop it.
			notifier.disable();
		}
	}

	/**
	 * tells the controller to stop checking. It stops all currently running
	 * Checkers and doesn't start new ones.
	 * 
	 * @param timeOut
	 *            if it is true, the checking was stopped because of a timeout;
	 */
	public void stopChecking(boolean timeOut) {

		if (!stopped) {
			this.stopped = true;
			for (Iterator<CheckerFactory> iterator = currentlyRunning.iterator(); iterator.hasNext();) {
				CheckerFactory toStop = (CheckerFactory) iterator.next();
				toStop.stopChecking();
			}

			// set all not finished results to finished, to indicate that they
			// are
			// ready.
			for (Iterator<Result> iterator = results.iterator(); iterator.hasNext();) {
				Result result = (Result) iterator.next();
				if (!result.isFinished()) {
					result.setFinished();
					// in case of a timeout set a timeout flag
					if (timeOut) {
						result.setTimeoutFlag();
					}
				}
			}
		}
	}

	public synchronized void notifyThatFinished(CheckerFactory finishedFactory) {
		if (currentlyRunning.size() == 0) {
			ErrorLogger.log("A checker finished when no checker was active.");
		} else {
			synchronized (this) {
				currentlyRunning.remove(finishedFactory);
			}
		}
	}

	public List<ResultInterface> getResults() {
		if (results == null) {

			ErrorLogger.log("Result objects couldn't be created.");
			return null;

		} else {

			// TODO schöner machen, wenn möglich
			List<? extends ResultInterface> toReturn = results;

			return (List<ResultInterface>) toReturn;
		}
	}
}
