package edu.pse.beast.propertychecker;

import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.electionSimulator.ElectionSimulation;
import edu.pse.beast.highlevel.ElectionDescriptionSource;
import edu.pse.beast.highlevel.ParameterSource;
import edu.pse.beast.toolbox.ErrorLogger;
import edu.pse.beast.toolbox.FileLoader;
import edu.pse.beast.toolbox.FileSaver;
import edu.pse.beast.toolbox.SuperFolderFinder;

import java.io.File;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Lukas
 *
 */
public abstract class CheckerFactory implements Runnable {

	private static final long SLEEPINTERVAL = 1000;
	private final FactoryController controller;
	private final ElectionDescriptionSource electionDescSrc;
	private final PostAndPrePropertiesDescription postAndPrepPropDesc;
	private final ParameterSource paramSrc;
	private final Result result;
	private final long POLLINGINTERVAL = 1000;

	private final File toCheck;

	private Checker currentlyRunning;
	private boolean stopped = false;
	private boolean finished = false;
	private List<String> lastResult;
	private List<String> lastError;

	private final boolean isMargin;

	/**
	 *
	 * @param controller
	 *            the factoryController that started this factory
	 * @param electionDescSrc
	 *            the electionDescription
	 * @param postAndPrepPropDesc
	 *            the propertyDescription
	 * @param paramSrc
	 *            the parameter
	 * @param result
	 *            the result object where the result has to be put in
	 */
	protected CheckerFactory(FactoryController controller, ElectionDescriptionSource electionDescSrc,
			PostAndPrePropertiesDescription postAndPrepPropDesc, ParameterSource paramSrc, Result result,
			boolean isMargin) {

		this.controller = controller;
		this.electionDescSrc = electionDescSrc;
		this.postAndPrepPropDesc = postAndPrepPropDesc;
		this.paramSrc = paramSrc;
		this.result = result;

		this.toCheck = null;

		this.isMargin = isMargin;

		// because we create a new instance with all variables null, we have to
		// catch it here
		if (result != null) {
			result.setElectionType(getElectionDescription());
			result.setProperty(postAndPrepPropDesc);
		}
	}

	// TODO maybe remove this, is probably unneeded, because we create the
	// margin check later now
	public CheckerFactory(FactoryController controller, File toCheck, ParameterSource paramSrc, Result result,
			boolean isMargin) {
		this.controller = controller;
		this.toCheck = toCheck;
		this.paramSrc = paramSrc;
		this.result = result;

		this.isMargin = isMargin;

		this.postAndPrepPropDesc = null;
		this.electionDescSrc = null;

		// because we create a new instance with all variables null, we have to
		// // catch it here
		// if (result != null) {
		// result.setElectionType(getElectionDescription());
		// result.setProperty(postAndPrepPropDesc);
		// }
	}

	/**
	 * the main working thread in this CheckerFactory. It cycles through all
	 * possible configurations and creates sequentially a new checker for each
	 */
	public void run() {

		String advanced = paramSrc.getParameter().getArgument();

		String[] toTrim = advanced.split(";");

		for (int i = 0; i < toTrim.length; i++) {
			// remove all whitespaces so they don't interfere later
			toTrim[i] = toTrim[i].trim();
		}

		advanced = String.join(";", advanced.split(";"));

		if (!isMargin) {

			outerLoop: for (Iterator<Integer> voteIterator = paramSrc.getParameter().getAmountVoters()
					.iterator(); voteIterator.hasNext();) {
				int voters = (int) voteIterator.next();
				for (Iterator<Integer> candidateIterator = paramSrc.getParameter().getAmountCandidates()
						.iterator(); candidateIterator.hasNext();) {
					int candidates = (int) candidateIterator.next();
					for (Iterator<Integer> seatsIterator = paramSrc.getParameter().getAmountSeats()
							.iterator(); seatsIterator.hasNext();) {
						int seats = (int) seatsIterator.next();

						synchronized (this) {
							if (!stopped) {
								currentlyRunning = startProcessCheck(electionDescSrc, postAndPrepPropDesc, advanced,
										voters, candidates, seats, this, false);

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
		} else { // we want to make a margin computation
			synchronized (this) {

				if (!stopped) {

					// determine the winner of this input of votes

					currentlyRunning = startProcessMargin(electionDescSrc, postAndPrepPropDesc, advanced,
							ElectionSimulation.getNumVoters(), ElectionSimulation.getNumCandidates(),
							ElectionSimulation.getNumSeats(), this, 0, null, true);

					// TODO wait here for it to finish

					List<Integer> origResult = null;
					Result marginResult;
					
					
					
					int left = 0;
					int right = ElectionSimulation.getNumVoters(); // how many votes we have
					int margin = 0;

					UnprocessedCBMCResult finalMarginResult = null;

					while ((left < right) && !stopped) {
						// calculate the margin to check
						margin = (int) (left + Math.floor((float) (right - left) / 2));

						currentlyRunning = startProcessMargin(electionDescSrc, postAndPrepPropDesc, advanced,
								ElectionSimulation.getNumVoters(), ElectionSimulation.getNumCandidates(),
								ElectionSimulation.getNumSeats(), this, margin, origResult, false);
						
						while (!currentlyRunning.isFinished()) {
							try {
								Thread.sleep(SLEEPINTERVAL);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

						System.out.println("finished for margin " + margin + " result: "
								+ currentlyRunning.checkAssertionSuccess());
						
						if (currentlyRunning.checkAssertionSuccess()) {
							left = margin + 1;
							margin = margin + 1;
						} else {
							right = margin;
							finalMarginResult = new UnprocessedCBMCResult();
							
							finalMarginResult.setResult(currentlyRunning.getResultList());
						}
					}
				}
			}
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
	 * when a checker finished it calls this methode to let the factory know
	 * that it can start the next one, if it still has more to start
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
	 * starts a new Checker with the given parameters. Implementation depends on
	 * the extending class
	 *
	 * @param electionDescSrc
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
	protected abstract Checker startProcessCheck(ElectionDescriptionSource electionDescSrc,
			PostAndPrePropertiesDescription postAndPrepPropDesc, String advanced, int voters, int candidates, int seats,
			CheckerFactory parent, boolean isMargin);

	/**
	 * starts a new Checker with the given parameters. Implementation depends on
	 * the extending class
	 *
	 * @param electionDescSrc
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
	protected abstract Checker startProcessMargin(ElectionDescriptionSource electionDescSrc,
			PostAndPrePropertiesDescription postAndPrepPropDesc, String advanced, int voters, int candidates, int seats,
			CheckerFactory parent, int margin, List<Integer> origResult, boolean isTest);

	/**
	 * starts a new Checker with the given parameters. Implementation depends on
	 * the extending class
	 *
	 * @param electionDescSrc
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
	protected abstract Checker startProcessTest(ElectionDescriptionSource electionDescSrc,
			PostAndPrePropertiesDescription postAndPrepPropDesc, String advanced, int voters, int candidates, int seats,
			CheckerFactory parent);

	// /**
	// * starts a new Checker with the given parameters. Implementation depends
	// on
	// * the extending class
	// *
	// * @param electionDescSrc
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
	// electionDescSrc,
	// PostAndPrePropertiesDescription postAndPrepPropDesc, String advanced, int
	// voters, int candidates, int seats,
	// CheckerFactory parent);
	//
	// /**
	// * starts a new Checker, which checks a given file with the given
	// * parameters. Implementation depends on the extending class
	// *
	// * @param electionDescSrc
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
	// electionDescSrc,
	// PostAndPrePropertiesDescription postAndPrepPropDesc, String advanced,
	// CheckerFactory parent);
	//
	// /**
	// * starts a new Checker, which checks a given file with the given
	// * parameters. Implementation depends on the extending class
	// *
	// * @param electionDescSrc
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
	// electionDescSrc,
	// PostAndPrePropertiesDescription postAndPrepPropDesc, String advanced,
	// CheckerFactory parent);

	/**
	 * allows the underlying implementation of the checker to clean up after
	 * itself, after the
	 */
	protected abstract void cleanUp();

	/**
	 *
	 * @param amount
	 *            the amount of objects to be created
	 * @return the result object that belongs to the Checker produced by this
	 *         factory
	 */
	public abstract List<Result> getMatchingResult(int amount);

	/**
	 *
	 * @param controller
	 *            the Factorycontroller that controls this checkerfactory
	 * @param electionDescSrc
	 *            the electiondescriptionsource
	 * @param postAndPrepPropDesc
	 *            a description of the given property
	 * @param paramSrc
	 *            the source for the parameters
	 * @param result
	 *            the object in which the result should be saved in later
	 * @param isMargin
	 * @return a new CheckerFactory
	 */
	public abstract CheckerFactory getNewInstance(FactoryController controller,
			ElectionDescriptionSource electionDescSrc, PostAndPrePropertiesDescription postAndPrepPropDesc,
			ParameterSource paramSrc, Result result, boolean isMargin);

	/**
	 *
	 * @param controller
	 *            the Factorycontroller that controls this checkerfactory
	 * @param toCheck
	 *            the file we want the Checker to check
	 * @param paramSrc
	 *            the source for the parameters
	 * @param result
	 *            the object in which the result should be saved in later
	 * @return a new CheckerFactory
	 */ // TODO maybe remove this, because fromfile should not be used if the
		// margin computation works
	public abstract CheckerFactory getNewInstance(FactoryController controller, File toCheck, ParameterSource paramSrc,
			Result result, boolean isMargin);

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
	 * @return true, if the property failes, false, if the propery was
	 *         successfull
	 */
	public abstract boolean checkAssertionFailure(List<String> toCheck);

	/**
	 * this methode extracts the electionType from the description
	 *
	 * @return the election Type that the here give ElectionDescriptionSource
	 *         describes
	 */
	private ElectionDescription getElectionDescription() {
		return electionDescSrc.getElectionDescription();
	}
}
