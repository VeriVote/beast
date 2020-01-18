package edu.pse.beast.propertychecker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.pse.beast.datatypes.electioncheckparameter.ElectionCheckParameter;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.electionsimulator.ElectionSimulationData;
import edu.pse.beast.highlevel.javafx.GUIController;
import edu.pse.beast.toolbox.ErrorForUserDisplayer;
import edu.pse.beast.toolbox.ErrorLogger;
import edu.pse.beast.toolbox.UnifiedNameContainer;

/**
 * A factory for creating Checker objects.
 *
 * @author Lukas Stapelbroek
 */
public abstract class CheckerFactory implements Runnable {

    /** The Constant SLEEP_INTERVAL. */
    private static final long SLEEP_INTERVAL = 1000;

    /** The Constant POLLING_INTERVAL. */
    private static final long POLLING_INTERVAL = 1000;

    /** The controller. */
    private final FactoryController controller;

    /** The election desc. */
    private final ElectionDescription electionDesc;

    /** The parameter. */
    private final ElectionCheckParameter parameter;

    /** The currently running. */
    private Checker currentlyRunning;

    /** The stopped. */
    private boolean stopped = false;

    /** The finished. */
    private boolean finished = false;

    /** The last result. */
    private List<String> lastResult;

    /** The last error. */
    private List<String> lastError;

    /** The result. */
    private Result result;

    /**
     * Instantiates a new checker factory.
     *
     * @param factController
     *            the factoryController that started this factory
     * @param electionDescription
     *            the electionDescription
     * @param res
     *            the result object where the result has to be put in
     * @param param
     *            the parameter
     */
    protected CheckerFactory(final FactoryController factController,
                             final ElectionDescription electionDescription,
                             final Result res,
                             final ElectionCheckParameter param) {
        this.controller = factController;
        this.electionDesc = electionDescription;
        this.result = res;
        this.parameter = param;
        // because we create a new instance with all variables null, we have to
        // catch it here
        if (res != null) {
            res.setElectionType(getElectionDescription());
        }
    }

    // public CheckerFactory(FactoryController controller,
    // ElectionCheckParameter
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

    // The main working thread in this CheckerFactory. It cycles through all
    // possible configurations and creates sequentially a new checker for each
    @Override
    public final void run() {
        String advanced = parameter.getArgument();
        String[] toTrim = advanced.split(";");
        for (int i = 0; i < toTrim.length; i++) {
            // remove all white spaces so they do not interfere later
            toTrim[i] = toTrim[i].trim();
        }
        advanced = String.join(";", advanced.split(";"));
        int numVotes = parameter.getMarginVotes();
        int numCandidates = parameter.getMarginCandidates();
        int numSeats = parameter.getMarginSeats();
        ElectionSimulationData votingData = GUIController.getController()
                .getElectionSimulation().getVotingData();

        result.setStarted();
        switch (result.getAnalysisType()) {
        case Check:
            runCheck(advanced, result);
            break;
        case Margin:
            runMargin(advanced, numVotes, numCandidates, numSeats, votingData,
                    result);
            break;
        case Test:
            runTest(advanced, numVotes, numCandidates, numSeats, votingData,
                    result);
            break;
        default:
            break;
        }

        // if the correct flags for the result object have not been set yet, we
        // set them
        if (!finished && !stopped) {
            finished = true;
            if (lastResult != null) {
                if (!result.isFinished()) {
                    // we got here without any verification fails, so the
                    // property is fulfilled
                    result.setSuccess();
                    result.setValid();
                    result.setFinished();
                } else {
                    ErrorLogger.log(
                            "The Result Object indicated a finished check, even though the "
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
     * Run check.
     *
     * @param advanced
     *            advanced options
     * @param res
     *            the result
     */
    private void runCheck(final String advanced, final Result res) {
        outerLoop: for (Iterator<Integer> voteIterator = parameter
                .getAmountVoters().iterator(); voteIterator.hasNext();) {
            int voters = voteIterator.next();
            for (Iterator<Integer> candidateIterator =
                    parameter.getAmountCandidates().iterator();
                    candidateIterator.hasNext();) {
                int candidates = candidateIterator.next();
                for (Iterator<Integer> seatsIterator =
                        parameter.getAmountSeats().iterator();
                        seatsIterator.hasNext();) {
                    int seats = seatsIterator.next();
                    synchronized (this) {
                        if (!stopped) {
                            currentlyRunning =
                                    startProcessCheck(electionDesc,
                                                      res.getPropertyDesctiption(),
                                                      advanced, voters, candidates,
                                                      seats, this, res);
                            // check if the creation was successful
                            if (currentlyRunning == null) {
                                // the process creation failed
                                stopped = true;
                                res.setFinished();
                                res.setError(
                                        "Could not start the process, please follow "
                                                + "the instructions you "
                                                + "got shown on the screen before");
                            }
                        }
                    }
                    busyWaiting();
                    if (stopped) {
                        res.setFinished();
                        break outerLoop;
                    } else {
                        // if it got started normally
                        // the checker finished checking for these specific
                        // parameters without being stopped and
                        // without a failure from the outside
                        // set currentlyRunnign to null, so we can
                        // catch, if the process creation failed
                        currentlyRunning = null;
                        // if the last check was successful, we have to
                        // keep checking the other ones

                        if (res.checkAssertionSuccess()) {
                            finished = false;
                        } else {
                            // this was not successful for some reason, so stop
                            // now
                            finished = true;
                            if (res.checkAssertionFailure()) {
                                res.setNumVoters(voters);
                                res.setNumCandidates(candidates);
                                res.setNumSeats(seats);
                                res.setResult(lastResult);
                                res.setError(lastError);
                                res.setValid();
                            } else {
                                res.setError(lastError);
                            }
                            res.setFinished();
                            break outerLoop;
                        }
                    }
                }
            }
        }
    }

    /**
     * Busy waiting.
     */
    private void busyWaiting() {
        // wait until we get stopped or the checker finished
        while (!finished && !stopped) {
            try {
                // polling in 1 second steps to save CPU time
                Thread.sleep(POLLING_INTERVAL);
            } catch (InterruptedException e) {
                ErrorLogger
                        .log("interrupted while busy waiting! (CheckerFactory");
            }
        }
    }

    /**
     * Run margin.
     *
     * @param advanced
     *            advanced options
     * @param numVoters
     *            amount of voters
     * @param numCandidates
     *            amount of candidates
     * @param numSeats
     *            amount of seats
     * @param origData
     *            original data
     * @param res
     *            the result
     */
    private void runMargin(final String advanced, final int numVoters,
                           final int numCandidates, final int numSeats,
                           final ElectionSimulationData origData,
                           final Result res) {
        synchronized (this) {
            if (!stopped) {
                // determine the winner of this input of votes
                currentlyRunning =
                        startProcessTest(electionDesc,
                                         res.getPropertyDesctiption(),
                                         advanced, numVoters,
                                         numCandidates, numSeats,
                                         this, origData, res);
                busyWaiting();
                ElectionSimulationData origResult =
                        new ElectionSimulationData(
                                numVoters, numCandidates, numSeats,
                                res.readVariableValue("elect\\d").get(0)
                                );
                int left = 0;
                int right = parameter.getNumVotingPoints();
                if (right == 1) {
                    ErrorForUserDisplayer.displayError(
                            "You wanted to computate a margin for one voter / votingPoint, "
                                    + "which does not make sense.");
                }
                int margin = 0;
                List<String> lastFailedRun = new ArrayList<String>();
                boolean hasUpperBound = false;
                boolean hasMargin = false;
                System.out.println("Started new margin computation!");
                int currentRun = 1;
                while ((left < right) && !stopped) {
                    System.out.println("left: " + left + " | right: " + right);
                    // calculate the margin to check
                    margin = (int) (left
                            + Math.floor((float) (right - left) / 2));
                    checkMarginAndWait(margin, origResult, advanced, numVoters,
                                       numCandidates, numSeats, origData, res);
                    res.setResult(currentlyRunning.getResultList());
                    res.addStatusString("finished run " + currentRun
                                        + " for margin " + margin + " Result: "
                                        + res.checkAssertionSuccess());
                    System.out.println("finished run " + currentRun
                                        + " for margin " + margin + " Result: "
                                        + res.checkAssertionSuccess());
                    currentRun++;
                    if (res.checkAssertionSuccess()) {
                        left = margin + 1;
                        margin = margin + 1;
                        hasMargin = true;
                    } else {
                        hasUpperBound = true;
                        right = margin;
                        lastFailedRun = currentlyRunning.getResultList();
                    }
                }
                // so far, we have not found an upper bound for the
                // margin, so we must check the last computed margin now:
                if (!hasUpperBound) {
                    checkMarginAndWait(margin, origResult, advanced, numVoters,
                                       numCandidates, numSeats, origData, res);
                    hasMargin = res.checkAssertionFailure();
                    if (hasMargin) {
                        lastFailedRun = lastResult;
                    }
                }
                // hasMargin now is true, if there is an upper bound,
                // and false, if there is no margin
                System.out.println("finished: hasFinalMargin: " + hasMargin
                                    + " || finalMargin = " + margin);
                res.addStatusString("finished: hasFinalMargin: " + hasMargin
                                    + " || finalMargin = " + margin);
                res.setMarginComp(true);
                res.setHasFinalMargin(hasMargin);
                res.setOrigWinner(origResult);
                res.setOrigVoting(GUIController.getController()
                                    .getElectionSimulation().getVotingData());
                if (hasMargin) {
                    res.setResult(lastFailedRun);
                    res.setFinalMargin(margin);
                } else {
                    res.setResult(currentlyRunning.getResultList());
                    res.setFinalMargin(-1);
                }
                ElectionSimulationData newVotes;
                ElectionSimulationData newResult;
                if (hasMargin) {
                    String voteName = UnifiedNameContainer.getNewVotesName();
                    String newResName = UnifiedNameContainer.getNewResultName();
                    newVotes = new ElectionSimulationData(
                            numVoters, numCandidates, numSeats,
                            res.readVariableValue(voteName).get(0)
                            );
                    newResult = new ElectionSimulationData(
                            numVoters, numCandidates, numSeats,
                            res.readVariableValue(newResName).get(0)
                            );
                    res.setNewVotes(newVotes);
                    res.setNewWinner(newResult);
                    res.setValid();
                    res.setFinished();
                    this.finished = true;
                }
                res.setFinished();
            }
        }
    }

    /**
     * Run test.
     *
     * @param advanced
     *            advanced options
     * @param numVoters
     *            amount of voters
     * @param numCandidates
     *            amount of candidates
     * @param numSeats
     *            amount of seats
     * @param origData
     *            original data
     * @param res
     *            the result
     */
    private void runTest(final String advanced, final int numVoters,
                         final int numCandidates, final int numSeats,
                         final ElectionSimulationData origData,
                         final Result res) {
        synchronized (this) {
            if (!stopped) {
                // determine the winner of this input of votes
                currentlyRunning =
                        startProcessTest(electionDesc,
                                         res.getPropertyDesctiption(),
                                         advanced, numVoters,
                                         numCandidates, numSeats, this,
                                         origData, res);
                busyWaiting();
                // CBMCResult dummyResult = new CBMCResult();
                // List<String> origResult = new ArrayList<String>();
                // origResult =
                // getElectionDescription().getContainer().getOutputType()
                // .getCodeToRunMargin(origResult, lastResult);
                //
                System.out.println("add namecontainer CHECKERFACTORY");
                ElectionSimulationData origResult =
                        new ElectionSimulationData(
                                numVoters, numCandidates, numSeats,
                                res.readVariableValue("elect\\d").get(0)
                                );
                int left = 0;
                // how many votes we have
                int right = GUIController.getController()
                                .getElectionSimulation().getNumVotingPoints();
                if (right == 1) {
                    ErrorForUserDisplayer.displayError(
                            "You wanted to compute a margin for one voter / votingPoint, "
                                    + "which does not make any sense");
                }
                int margin = 0;
                List<String> lastFailedRun = new ArrayList<String>();
                boolean hasUpperBound = false;
                boolean hasMargin = false;
                while ((left < right) && !stopped) {
                    // calculate the margin to check
                    margin = (int) (left
                            + Math.floor((float) (right - left) / 2));
                    checkMarginAndWait(margin, origResult, advanced, numVoters,
                            numCandidates, numSeats, origData, res);
                    System.out.println("finished for margin " + margin
                            + " result: " + res.checkAssertionSuccess());
                    if (res.checkAssertionSuccess()) {
                        left = margin + 1;
                        margin++;
                        hasMargin = true;
                    } else {
                        hasUpperBound = true;
                        right = margin;
                        lastFailedRun = lastResult;
                    }
                }
                // so far, we have not found an upper bound for the
                // margin, so we have to check the last computed margin now:
                if (!hasUpperBound) {
                    checkMarginAndWait(margin, origResult, advanced, numVoters,
                                       numCandidates, numSeats, origData, res);
                    hasMargin = res.checkAssertionFailure();
                    if (hasMargin) {
                        lastFailedRun = lastResult;
                    }
                }
                // hasMargin now is true, if there is an upper bound,
                // and false, if there is no margin
                System.out.println("finished: hasFinalMargin: " + hasMargin
                                    + " || finalMargin = " + margin);
                res.setMarginComp(true);
                res.setHasFinalMargin(hasMargin);
                res.setOrigWinner(origResult);
                // result.setOrigVoting(GUIController.getController()
                // .getElectionSimulation().getVotingDataListofList());
                res.setOrigVoting(GUIController.getController()
                        .getElectionSimulation().getVotingData());
                if (hasMargin) {
                    res.setResult(currentlyRunning.getResultList());
                    res.setFinalMargin(margin);
                } else {
                    res.setResult(null);
                    res.setFinalMargin(-1);
                }
                // List<List<String>> newVotes = new ArrayList<List<String>>();
                // List<String> newResult = new ArrayList<String>();
                ElectionSimulationData newVotes;
                ElectionSimulationData newResult;
                if (hasMargin) {
                    // newResult =
                    // getElectionDescription().getContainer().getOutputType()
                    // .getNewResult(lastFailedRun, 0);
                    // newVotes =
                    // getElectionDescription().getContainer().getInputType()
                    // .getNewVotes(lastFailedRun, 0);
                    newResult = new ElectionSimulationData(
                            numVoters, numCandidates, numSeats,
                            res.readVariableValue("elect\\d").get(0)
                            );
                    newVotes = new ElectionSimulationData(
                            numVoters, numCandidates, numSeats,
                            res.readVariableValue("votes\\d").get(0)
                            );
                    res.setNewVotes(newVotes);
                    res.setNewWinner(newResult);
                    res.setValid();
                    res.setFinished();
                    this.finished = true;
                    // int count = 0;

                    System.out.println("fix output CheckerFactory");
                    // for (Iterator<List<String>> iterator =
                    // newVotes.iterator(); iterator.hasNext();) {
                    // int count2 = 0;
                    // List<String> list = (List<String>) iterator.next();
                    // System.out.println("");
                    // System.out.print("new_votes: " + count++ + "==");
                    // for (Iterator<String> iterator2 = list.iterator();
                    // iterator2.hasNext();) {
                    // String long1 = (String) iterator2.next();
                    // System.out.print(count2++ + ":" + long1 + "|");
                    // }
                    // }
                    // System.out.println("");
                    // System.out.println("===========");
                    // count = 0;
                    // for (Iterator<String> iterator = newResult.iterator();
                    // iterator.hasNext();) {
                    // String string1 = (String) iterator.next();
                    // System.out.println("new_result " + count + ": " +
                    // string1);
                    // count = count + 1;
                    // }
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
     * When a checker finished, it calls this method to let the factory know
     * that it can start the next one, if it still has more to start.
     *
     * @param lastRes
     *            the last result output from the checker that finished last
     * @param lastErr
     *            the last error output from the checker that finished last
     */
    public void notifyThatFinished(final List<String> lastRes,
                                   final List<String> lastErr) {
        finished = true;
        this.lastResult = lastRes;
        this.lastError = lastErr;
    }

    /**
     * Executes a margin computation and waits for it to finish. The
     * result/error is in "lastResult/lastError" after the method returned.
     *
     * @param margin
     *            the margin
     * @param origData
     *            the orig data
     * @param advanced
     *            advanced options
     * @param numVoters
     *            amount of voters
     * @param numCandidates
     *            amount of candidates
     * @param numSeats
     *            amount of seats
     * @param votingData
     *            the voting data
     * @param res
     *            the result
     */
    protected void checkMarginAndWait(final int margin,
                                      final ElectionSimulationData origData,
                                      final String advanced,
                                      final int numVoters,
                                      final int numCandidates,
                                      final int numSeats,
                                      final ElectionSimulationData votingData,
                                      final Result res) {
        currentlyRunning =
                startProcessMargin(electionDesc,
                                   res.getPropertyDesctiption(),
                                   advanced, numVoters,
                                   numCandidates, numSeats, this,
                                   margin, origData, votingData,
                                   res);
        while (!currentlyRunning.isFinished()) {
            try {
                Thread.sleep(SLEEP_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Starts a new Checker with the given parameters. Implementation depends on
     * the extending class.
     *
     * @param elecDesc
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
     * @param res
     *            the result
     * @return the newly created Checker that is now checking the given file and
     *         other properties
     */
    protected abstract Checker
        startProcessCheck(ElectionDescription elecDesc,
                          PreAndPostConditionsDescription postAndPrepPropDesc,
                          String advanced, int voters, int candidates, int seats,
                          CheckerFactory parent, Result res);

    /**
     * Starts a new Checker with the given parameters. Implementation depends on
     * the extending class.
     *
     * @param elecDesc
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
     * @param margin
     *            the margin
     * @param origResult
     *            the voting data
     * @param votingData
     *            TODO
     * @param res
     *            the result
     * @return the newly created Checker that is now checking the given file and
     *         other properties
     */
    protected abstract Checker
        startProcessMargin(ElectionDescription elecDesc,
                           PreAndPostConditionsDescription postAndPrepPropDesc,
                           String advanced, int voters, int candidates, int seats,
                           CheckerFactory parent, int margin,
                           ElectionSimulationData origResult,
                           ElectionSimulationData votingData, Result res);

    /**
     * Starts a new Checker with the given parameters. Implementation depends on
     * the extending class.
     *
     * @param elecDesc
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
     * @param origData
     *            the voting data
     * @param res
     *            the result
     * @return the newly created Checker that is now checking the given file and
     *         other properties
     */
    protected abstract Checker
        startProcessTest(ElectionDescription elecDesc,
                         PreAndPostConditionsDescription postAndPrepPropDesc,
                         String advanced, int voters, int candidates, int seats,
                         CheckerFactory parent, ElectionSimulationData origData,
                         Result res);

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
     * Allows the underlying implementation of the checker to clean up after
     * itself, after the cleanup.
     */
    protected abstract void cleanUp();

    /**
     * Gets the matching result.
     *
     * @return the result object that belongs to the Checker produced by this
     *         factory
     */
    public abstract Result getMatchingResult();

    // /**
    // *
    // * @param controller
    // * the factory controller that controls this checker factory
    // * @param electionDesc
    // * the election description source
    // * @param postAndPrepPropDesc
    // * a description of the given property
    // * @param parameter
    // * the source for the parameters
    // * @param result
    // * the object in which the result should be saved in later
    // * @param isMargin
    // * @return a new CheckerFactory
    // */
    // public abstract CheckerFactory getNewInstance(FactoryController
    // controller,
    // ElectionDescriptionSource electionDesc, PreAndPostConditionsDescription
    // postAndPrepPropDesc,
    // ElectionCheckParameter parameter, Result result, boolean isMargin);
    //

    /**
     * Gets the new instance.
     *
     * @param contr
     *            the contr
     * @param electionDescription
     *            the election description
     * @param res
     *            the res
     * @param param
     *            the param
     * @return the new instance
     */
    public abstract CheckerFactory getNewInstance(FactoryController contr,
                                                  ElectionDescription electionDescription,
                                                  Result res, ElectionCheckParameter param);

    //
    // /**
    // *
    // * @param controller
    // * the factory controller that controls this checker factory
    // * @param toCheck
    // * the file we want the Checker to check
    // * @param paramSrc
    // * the source for the parameters
    // * @param result
    // * the object in which the result should be saved in later
    // * @return a new CheckerFactory
    // */
    // public abstract CheckerFactory getNewInstance(FactoryController
    // controller,
    // File toCheck, ParameterSource paramSrc,
    // Result result, boolean isMargin);

    /**
     * This method extracts the electionType from the description.
     *
     * @return the election Type that the here give ElectionDescriptionSource
     *         describes
     */
    private ElectionDescription getElectionDescription() {
        return electionDesc;
    }
}
