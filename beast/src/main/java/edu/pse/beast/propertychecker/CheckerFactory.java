package edu.pse.beast.propertychecker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.pse.beast.datatypes.electioncheckparameter.ElectionCheckParameter;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.highlevel.javafx.GUIController;
import edu.pse.beast.toolbox.ErrorForUserDisplayer;
import edu.pse.beast.toolbox.ErrorLogger;

/**
 *
 * @author Lukas
 *
 */
public abstract class CheckerFactory implements Runnable {

	private static final long SLEEPINTERVAL = 1000;
	private final FactoryController controller;
	private final ElectionDescription electionDesc;
	private final ElectionCheckParameter parameter;

	private final long POLLINGINTERVAL = 1000;

	private Checker currentlyRunning;
	private boolean stopped = false;
	private boolean finished = false;
	private List<String> lastResult;
	private List<String> lastError;

	private Result result;

	/**
	 *
	 * @param controller
	 *            the factoryController that started this factory
	 * @param electionDesc
	 *            the electionDescription
	 * @param postAndPrepPropDesc
	 *            the propertyDescription
	 * @param parameter
	 *            the parameter
	 * @param result
	 *            the result object where the result has to be put in
	 */
	protected CheckerFactory(FactoryController controller, ElectionDescription electionDesc, Result result,
			ElectionCheckParameter parameter) {

		this.controller = controller;
		this.electionDesc = electionDesc;
		this.result = result;
		this.parameter = parameter;

		// because we create a new instance with all variables null, we have to
		// catch it here
		if (result != null) {
			result.setElectionType(getElectionDescription());
		}
	}

	// public CheckerFactory(FactoryController controller, ElectionCheckParameter
	// parameter, Result result,
	// boolean isMargin) {
	// this.controller = controller;
	// this.parameter = parameter;
	// this.result = result;
	//
	// this.isMargin = isMargin;
	//
	// this.postAndPrepPropDesc = null;
	// this.electionDesc = null;
	//
	// // because we create a new instance with all variables null, we have to
	// // // catch it here
	// // if (result != null) {
	// // result.setElectionType(getElectionDescription());
	// // result.setProperty(postAndPrepPropDesc);
	// // }
	// }

	/**
	 * the main working thread in this CheckerFactory. It cycles through all
	 * possible configurations and creates sequentially a new checker for each
	 */
	public void run() {

		String advanced = parameter.getArgument();

		String[] toTrim = advanced.split(";");

		for (int i = 0; i < toTrim.length; i++) {
			// remove all whitespaces so they don't interfere later
			toTrim[i] = toTrim[i].trim();
		}

		advanced = String.join(";", advanced.split(";"));

		int numVotes = parameter.getMarginVotes();

		int numCandidates = parameter.getMarginCandidates();

		int numSeats = parameter.getMarginSeats();

		String[][] votingData = GUIController.getController().getVotingData();

		switch (result.getAnalysisType()) {
		case Check:
			runCheck(advanced);
			break;

		case Margin:
			runMargin(advanced, numVotes, numCandidates, numSeats, votingData);
			break;

		case Test:
			runTest(advanced, numVotes, numCandidates, numSeats, votingData);
			break;

		default:
			break;
		}

		// if the correct flags for the result object haven't been set yet, we
		// set them
		if (!finished && !stopped)

		{
			finished = true;
			if (lastResult != null) {
				if (!result.isFinished()) {
					// we got here without any verification fails, so the
					// property is fulfilled
					result.setSuccess();
					result.setValid();
					result.setFinished();
				} else {
					ErrorLogger.log("The Result Object indicated a finished check, even though the "
							+ "CheckerFactory was still running");
				}
			} else {
				ErrorLogger.log("The last result was null! (CheckerFactory)");
			}
		}

		// give the implementation of this class the chance to clean up after
		// itself
		cleanUp();

		controller.notifyThatFinished(this);

	}

	/**
	 * @param advanced
	 */
	private void runCheck(String advanced) {
		outerLoop: for (Iterator<Integer> voteIterator = parameter.getAmountVoters().iterator(); voteIterator
				.hasNext();) {
			int voters = (int) voteIterator.next();
			for (Iterator<Integer> candidateIterator = parameter.getAmountCandidates().iterator(); candidateIterator
					.hasNext();) {
				int candidates = (int) candidateIterator.next();
				for (Iterator<Integer> seatsIterator = parameter.getAmountSeats().iterator(); seatsIterator
						.hasNext();) {
					int seats = (int) seatsIterator.next();

					synchronized (this) {
						if (!stopped) {

							currentlyRunning = startProcessCheck(electionDesc, result.getPropertyDesctiption(),
									advanced, voters, candidates, seats, this);

							// check if the creation was successfull
							if (currentlyRunning == null) {
								// the process creation failed
								stopped = true;
								result.setFinished();
								result.setError("Couldn't start the process, please follow the instructions you "
										+ "got shown on the screen before");
							}
						}
					}

					// wait until we get stopped or the checker finished
					while (!finished && !stopped) {
						try {
							// polling in 1 second steps to save cpu time
							Thread.sleep(POLLINGINTERVAL);
						} catch (InterruptedException e) {
							ErrorLogger.log("interrupted while busy waiting! (CheckerFactory");
						}
					}

					if (stopped) {
						result.setFinished();
						break outerLoop;
					} else { // if it got started normally
								// the checker finished checking for these
								// specific
								// parameters without being stopped and
								// without a failure
								// from the outside
								// set currentlyRunnign to null, so we can
								// catch, if the
								// process creation failed
						currentlyRunning = null;

						// if the last check was successful, we have to
						// keep checking the other ones
						if (checkAssertionSuccess(lastResult)) {
							finished = false;
						} else {
							// the wasn't successfull for some reason
							// so stop now
							finished = true;

							if (checkAssertionFailure(lastResult)) {
								result.setNumVoters(voters);
								result.setNumCandidates(candidates);
								result.setNumSeats(seats);

								result.setResult(lastResult);
								result.setValid();
							} else {
								result.setError(lastError);
							}
							result.setFinished();

							break outerLoop;
						}
					}
				}
			}
		}
	}

	/**
	 * @param advanced
	 */
	private void runMargin(String advanced, int numVoters, int numCandidates, int numSeats, String[][] origData) {
		synchronized (this) {

			if (!stopped) {

				// determine the winner of this input of votes

				// currentlyRunning = startProcessMargin(electionDesc,
				// childItem.getPreAndPostProperties(), advanced,
				// ElectionSimulation.getNumVoters(), ElectionSimulation.getNumCandidates(),
				// ElectionSimulation.getNumSeats(), this, 0, null, true);
				//
				// currentlyRunning = startProcessMargin(electionDesc,
				// childItem.getPreAndPostProperties(), advanced, numVoters, numCandidates,
				// numSeats, this, margin, origResult, origData)

				currentlyRunning = startProcessTest(electionDesc, result.getPropertyDesctiption(), advanced,
						numVoters, numCandidates, numSeats, this, origData);

				while (!finished && !stopped) {
					try {
						// polling in 1 second steps to save cpu time
						Thread.sleep(POLLINGINTERVAL);
					} catch (InterruptedException e) {
						ErrorLogger.log("interrupted while busy waiting! (CheckerFactory");
					}
				}

				// CBMCResult dummyResult = new CBMCResult();

				List<String> origResult = new ArrayList<String>();

				origResult = getElectionDescription().getContainer().getOutputType().getCodeToRunMargin(origResult,
						lastResult);

				int left = 0;

				int right = parameter.getNumVotingPoints();

				if (right == 1) {
					ErrorForUserDisplayer.displayError(
							"You wanted to computate a margin for one voter / votingPoint, which doesn't make any sense");
				}

				int margin = 0;

				List<String> lastFailedRun = new ArrayList<String>();

				boolean hasUpperBound = false;

				boolean hasMargin = false;

				while ((left < right) && !stopped) {
					// calculate the margin to check
					margin = (int) (left + Math.floor((float) (right - left) / 2));

					checkMarginAndWait(margin, origResult, advanced, numVoters, numCandidates, numSeats, origData);

					System.out.println(
							"finished for margin " + margin + " result: " + currentlyRunning.checkAssertionSuccess());

					if (currentlyRunning.checkAssertionSuccess()) {
						left = margin + 1;
						margin = margin + 1;

						hasMargin = true;

					} else {
						hasUpperBound = true;
						right = margin;
						lastFailedRun = lastResult;
					}
				}

				// so far we haven't found an upper bound for the
				// margin, so we have to check the last computated margin now:
				if (!hasUpperBound) {
					checkMarginAndWait(margin, origResult, advanced, numVoters, numCandidates, numSeats, origData);
					hasMargin = currentlyRunning.checkAssertionFailure();
					if (hasMargin) {
						lastFailedRun = lastResult;
					}
				}
				// hasMargin now is true, if there is an upper bound,
				// and false, if there is no margin

				System.out.println("finished: hasFinalMargin: " + hasMargin + " || finalMargin = " + margin);
				
				result.setMarginComp(true);

				result.setHasFinalMargin(hasMargin);

				result.setOrigWinner(origResult);
				result.setOrigVoting(GUIController.getController().getElectionSimulation().getVotingDataListofList());

				if (hasMargin) {
					result.setResult(currentlyRunning.getResultList());
					result.setFinalMargin(margin);
				} else {
					result.setResult(null);
					result.setFinalMargin(-1);
				}

				List<List<String>> newVotes = new ArrayList<List<String>>();
				List<String> newResult = new ArrayList<String>();

				if (hasMargin) {

					newResult = getElectionDescription().getContainer().getOutputType().getNewResult(lastFailedRun);

					newVotes = getElectionDescription().getContainer().getInputType().getNewVotes(lastFailedRun);

					result.setNewVotes(newVotes);
					result.setNewWinner(newResult);

					result.setValid();
					result.setFinished();
					this.finished = true;

					int count = 0;

					for (Iterator<List<String>> iterator = newVotes.iterator(); iterator.hasNext();) {
						int count2 = 0;
						List<String> list = (List<String>) iterator.next();
						System.out.println("");
						System.out.print("new_votes: " + count++ + "==");
						for (Iterator<String> iterator2 = list.iterator(); iterator2.hasNext();) {
							String long1 = (String) iterator2.next();
							System.out.print(count2++ + ":" + long1 + "|");
						}
					}
					System.out.println("");
					System.out.println("===========");

					count = 0;

					for (Iterator<String> iterator = newResult.iterator(); iterator.hasNext();) {
						String string1 = (String) iterator.next();
						System.out.println("new_result " + count + ": " + string1);
						count = count + 1;
					}
				}
				result.setFinished();
			}
		}
	}

	/**
	 * @param advanced
	 */
	private void runTest(String advanced, int numVoters, int numCandidates, int numSeats, String[][] origData) {
		synchronized (this) {

			if (!stopped) {

				// determine the winner of this input of votes

				currentlyRunning = startProcessTest(electionDesc, result.getPropertyDesctiption(), advanced,
						numVoters, numCandidates, numSeats, this, origData);

				while (!finished && !stopped) {
					try {
						// polling in 1 second steps to save cpu time
						Thread.sleep(POLLINGINTERVAL);
					} catch (InterruptedException e) {
						ErrorLogger.log("interrupted while busy waiting! (CheckerFactory");
					}
				}

				// CBMCResult dummyResult = new CBMCResult();

				List<String> origResult = new ArrayList<String>();

				origResult = getElectionDescription().getContainer().getOutputType().getCodeToRunMargin(origResult,
						lastResult);

				int left = 0;
				int right = GUIController.getController().getElectionSimulation().getNumVotingPoints(); // how many
																										// votes we have

				if (right == 1) {
					ErrorForUserDisplayer.displayError(
							"You wanted to computate a margin for one voter / votingPoint, which doesn't make any sense");
				}

				int margin = 0;

				List<String> lastFailedRun = new ArrayList<String>();

				boolean hasUpperBound = false;

				boolean hasMargin = false;

				while ((left < right) && !stopped) {
					// calculate the margin to check
					margin = (int) (left + Math.floor((float) (right - left) / 2));

					checkMarginAndWait(margin, origResult, advanced, numVoters, numCandidates, numSeats, origData);

					System.out.println(
							"finished for margin " + margin + " result: " + currentlyRunning.checkAssertionSuccess());

					if (currentlyRunning.checkAssertionSuccess()) {
						left = margin + 1;
						margin = margin + 1;

						hasMargin = true;

					} else {
						hasUpperBound = true;
						right = margin;
						lastFailedRun = lastResult;
					}
				}

				// so far we haven't found an upper bound for the
				// margin, so we have to check the last computated margin now:
				if (!hasUpperBound) {
					checkMarginAndWait(margin, origResult, advanced, numVoters, numCandidates, numSeats, origData);
					hasMargin = currentlyRunning.checkAssertionFailure();
					if (hasMargin) {
						lastFailedRun = lastResult;
					}
				}
				// hasMargin now is true, if there is an upper bound,
				// and false, if there is no margin

				System.out.println("finished: hasFinalMargin: " + hasMargin + " || finalMargin = " + margin);

				result.setMarginComp(true);

				result.setHasFinalMargin(hasMargin);

				result.setOrigWinner(origResult);
				result.setOrigVoting(GUIController.getController().getElectionSimulation().getVotingDataListofList());

				if (hasMargin) {
					result.setResult(currentlyRunning.getResultList());
					result.setFinalMargin(margin);
				} else {
					result.setResult(null);
					result.setFinalMargin(-1);
				}

				List<List<String>> newVotes = new ArrayList<List<String>>();
				List<String> newResult = new ArrayList<String>();

				if (hasMargin) {

					newResult = getElectionDescription().getContainer().getOutputType().getNewResult(lastFailedRun);

					newVotes = getElectionDescription().getContainer().getInputType().getNewVotes(lastFailedRun);

					result.setNewVotes(newVotes);
					result.setNewWinner(newResult);

					result.setValid();
					result.setFinished();
					this.finished = true;

					int count = 0;

					for (Iterator<List<String>> iterator = newVotes.iterator(); iterator.hasNext();) {
						int count2 = 0;
						List<String> list = (List<String>) iterator.next();
						System.out.println("");
						System.out.print("new_votes: " + count++ + "==");
						for (Iterator<String> iterator2 = list.iterator(); iterator2.hasNext();) {
							String long1 = (String) iterator2.next();
							System.out.print(count2++ + ":" + long1 + "|");
						}
					}
					System.out.println("");
					System.out.println("===========");

					count = 0;

					for (Iterator<String> iterator = newResult.iterator(); iterator.hasNext();) {
						String string1 = (String) iterator.next();
						System.out.println("new_result " + count + ": " + string1);
						count = count + 1;
					}
				}

			}
		}
	}

	/**
	 * signals to this checkerFactory that it has to stop checking. It then also
	 * stops the currently running checker
	 */
	public synchronized void stopChecking() {
		stopped = true;
		if (currentlyRunning != null) {
			currentlyRunning.stopChecking();
		}
	}

	/**
	 * when a checker finished it calls this method to let the factory know that it
	 * can start the next one, if it still has more to start
	 *
	 * @param lastResult
	 *            the last result output from the checker that finished last
	 * @param lastError
	 *            the last error output from the checker that finished last
	 */
	public void notifyThatFinished(List<String> lastResult, List<String> lastError) {
		finished = true;
		this.lastResult = lastResult;
		this.lastError = lastError;
	}

	/**
	 * executes a margin computation and waits for it to finish. The result/error is
	 * in "lastResult/lastError" after the method returned
	 * 
	 * @param margin
	 * @param origResult
	 * @param advanced
	 */
	protected void checkMarginAndWait(int margin, List<String> origResult, String advanced, int numVoters,
			int numCandidates, int numSeats, String[][] votingData) {

		currentlyRunning = startProcessMargin(electionDesc, result.getPropertyDesctiption(), advanced, numVoters,
				numCandidates, numSeats, this, margin, origResult, votingData);

		while (!currentlyRunning.isFinished()) {
			try {
				Thread.sleep(SLEEPINTERVAL);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * starts a new Checker with the given parameters. Implementation depends on the
	 * extending class
	 *
	 * @param electionDesc
	 *            the election description
	 * @param postAndPrepPropDesc
	 *            the property description
	 * @param advanced
	 *            the advanced options to be passed to the checker
	 * @param voters
	 *            the amount of voters
	 * @param candidates
	 *            the amount of candidates
	 * @param seats
	 *            the amount of seats
	 * @param parent
	 *            the parent that has to be notified when the checker finished
	 * @return the newly created Checker that is now checking the given file and
	 *         other properties
	 */
	protected abstract Checker startProcessCheck(ElectionDescription electionDesc,
			PreAndPostConditionsDescription postAndPrepPropDesc, String advanced, int voters, int candidates, int seats,
			CheckerFactory parent);

	/**
	 * starts a new Checker with the given parameters. Implementation depends on the
	 * extending class
	 *
	 * @param electionDesc
	 *            the election description
	 * @param postAndPrepPropDesc
	 *            the property description
	 * @param advanced
	 *            the advanced options to be passed to the checker
	 * @param voters
	 *            the amount of voters
	 * @param candidates
	 *            the amount of candidates
	 * @param seats
	 *            the amount of seats
	 * @param parent
	 *            the parent that has to be notified when the checker finished
	 * @return the newly created Checker that is now checking the given file and
	 *         other properties
	 */
	protected abstract Checker startProcessMargin(ElectionDescription electionDesc,
			PreAndPostConditionsDescription postAndPrepPropDesc, String advanced, int voters, int candidates, int seats,
			CheckerFactory parent, int margin, List<String> origResult, String[][] origData);

	/**
	 * starts a new Checker with the given parameters. Implementation depends on the
	 * extending class
	 *
	 * @param electionDesc
	 *            the election description
	 * @param postAndPrepPropDesc
	 *            the property description
	 * @param advanced
	 *            the advanced options to be passed to the checker
	 * @param voters
	 *            the amount of voters
	 * @param candidates
	 *            the amount of candidates
	 * @param seats
	 *            the amount of seats
	 * @param parent
	 *            the parent that has to be notified when the checker finished
	 * @return the newly created Checker that is now checking the given file and
	 *         other properties
	 */
	protected abstract Checker startProcessTest(ElectionDescription electionDesc,
			PreAndPostConditionsDescription postAndPrepPropDesc, String advanced, int voters, int candidates, int seats,
			CheckerFactory parent, String[][] origData);

	// /**
	// * starts a new Checker with the given parameters. Implementation depends
	// on
	// * the extending class
	// *
	// * @param electionDesc
	// * the election description
	// * @param postAndPrepPropDesc
	// * the property description
	// * @param advanced
	// * the advanced options to be passed to the checker
	// * @param voters
	// * the amount of voters
	// * @param candidates
	// * the amount of candidates
	// * @param seats
	// * the amount of seats
	// * @param parent
	// * the parent that has to be notified when the checker finished
	// * @return the newly created Checker that is now checking the given file
	// and
	// * other properties
	// */
	// protected abstract Checker startProcessCheck(ElectionDescriptionSource
	// electionDesc,
	// PreAndPostConditionsDescription postAndPrepPropDesc, String advanced, int
	// voters, int candidates, int seats,
	// CheckerFactory parent);
	//
	// /**
	// * starts a new Checker, which checks a given file with the given
	// * parameters. Implementation depends on the extending class
	// *
	// * @param electionDesc
	// * the election description
	// * @param postAndPrepPropDesc
	// * the property description
	// * @param advanced
	// * the advanced options to be passed to the checker
	// * @param voters
	// * the amount of voters
	// * @param candidates
	// * the amount of candidates
	// * @param seats
	// * the amount of seats
	// * @param parent
	// * the parent that has to be notified when the checker finished
	// * @return the newly created Checker that is now checking the given file
	// and
	// * other properties
	// */
	// protected abstract Checker startProcessMargin(ElectionDescriptionSource
	// electionDesc,
	// PreAndPostConditionsDescription postAndPrepPropDesc, String advanced,
	// CheckerFactory parent);
	//
	// /**
	// * starts a new Checker, which checks a given file with the given
	// * parameters. Implementation depends on the extending class
	// *
	// * @param electionDesc
	// * the election description
	// * @param postAndPrepPropDesc
	// * the property description
	// * @param advanced
	// * the advanced options to be passed to the checker
	// * @param voters
	// * the amount of voters
	// * @param candidates
	// * the amount of candidates
	// * @param seats
	// * the amount of seats
	// * @param parent
	// * the parent that has to be notified when the checker finished
	// * @return the newly created Checker that is now checking the given file
	// and
	// * other properties
	// */
	// protected abstract Checker startProcessTest(ElectionDescriptionSource
	// electionDesc,
	// PreAndPostConditionsDescription postAndPrepPropDesc, String advanced,
	// CheckerFactory parent);

	/**
	 * allows the underlying implementation of the checker to clean up after itself,
	 * after the
	 */
	protected abstract void cleanUp();

	/**
	 *
	 * @param amount
	 *            the amount of objects to be created
	 * @return the result object that belongs to the Checker produced by this
	 *         factory
	 */
	public abstract Result getMatchingResult();

	// /**
	// *
	// * @param controller
	// * the Factorycontroller that controls this checkerfactory
	// * @param electionDesc
	// * the electiondescriptionsource
	// * @param postAndPrepPropDesc
	// * a description of the given property
	// * @param parameter
	// * the source for the parameters
	// * @param result
	// * the object in which the result should be saved in later
	// * @param isMargin
	// * @return a new CheckerFactory
	// */
	// public abstract CheckerFactory getNewInstance(FactoryController controller,
	// ElectionDescriptionSource electionDesc, PreAndPostConditionsDescription
	// postAndPrepPropDesc,
	// ElectionCheckParameter parameter, Result result, boolean isMargin);
	//

	public abstract CheckerFactory getNewInstance(FactoryController controller, ElectionDescription electionDesc,
			Result result, ElectionCheckParameter parameter);

	//
	// /**
	// *
	// * @param controller
	// * the Factorycontroller that controls this checkerfactory
	// * @param toCheck
	// * the file we want the Checker to check
	// * @param paramSrc
	// * the source for the parameters
	// * @param result
	// * the object in which the result should be saved in later
	// * @return a new CheckerFactory
	// */
	// public abstract CheckerFactory getNewInstance(FactoryController controller,
	// File toCheck, ParameterSource paramSrc,
	// Result result, boolean isMargin);

	/**
	 * checks if the result from the given checker found a counterexample or not
	 *
	 * @param toCheck
	 *            the output of the checker to test
	 * @return true, if the property wasn't violated, false if that was the case
	 */
	public abstract boolean checkAssertionSuccess(List<String> toCheck);

	/**
	 * checks the given list, if it indicates a failure
	 *
	 * @param toCheck
	 *            the list to be searched for clues
	 * @return true, if the property failes, false, if the propery was successfull
	 */
	public abstract boolean checkAssertionFailure(List<String> toCheck);

	/**
	 * this method extracts the electionType from the description
	 *
	 * @return the election Type that the here give ElectionDescriptionSource
	 *         describes
	 */
	private ElectionDescription getElectionDescription() {
		return electionDesc;
	}
}
