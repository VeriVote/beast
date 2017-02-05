package edu.pse.beast.propertychecker;

import java.util.Iterator;
import java.util.List;

import edu.pse.beast.datatypes.ElectionType;
import edu.pse.beast.datatypes.internal.InternalTypeContainer;
import edu.pse.beast.datatypes.internal.InternalTypeRep;
import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.highlevel.ElectionDescriptionSource;
import edu.pse.beast.highlevel.ParameterSource;
import edu.pse.beast.toolbox.ErrorLogger;

/**
 * 
 * @author Lukas
 *
 */
public abstract class CheckerFactory implements Runnable {

    private final FactoryController controller;
    private final ElectionDescriptionSource electionDescSrc;
    private final PostAndPrePropertiesDescription postAndPrepPropDesc;
    private final ParameterSource paramSrc;
    private final Result result;
    private final long pollInterval = 1000;

    private Checker currentlyRunning;
    private boolean stopped = false;
    private boolean finished = false;
    private List<String> lastResult;
    private List<String> lastError;

    protected CheckerFactory(FactoryController controller, ElectionDescriptionSource electionDescSrc,
            PostAndPrePropertiesDescription postAndPrepPropDesc, ParameterSource paramSrc, Result result) {

        this.controller = controller;
        this.electionDescSrc = electionDescSrc;
        this.postAndPrepPropDesc = postAndPrepPropDesc;
        this.paramSrc = paramSrc;
        this.result = result;

        // because we create a new instance with all variables null, we have to
        // catch it here
        if (result != null) {
            result.setElectionType(getElectionTypeFromElectionDescription());
            
            // TODO find out the real type somehow
            result.setElectionType(ElectionType.APPROVAL);
        }
    }

    public void run() {

        String advanced = paramSrc.getParameter().getArgument();

        String[] toTrim = advanced.split(";");

        for (int i = 0; i < toTrim.length; i++) {
            // remove all whitespaces so they don't interfere later
            toTrim[i] = toTrim[i].trim();
        }

        advanced = String.join(";", advanced.split(";"));

        outerLoop: for (Iterator<Integer> voteIterator = paramSrc.getParameter().getAmountVoters()
                .iterator(); voteIterator.hasNext();) {
            int voters = (int) voteIterator.next();
            for (Iterator<Integer> candidateIterator = paramSrc.getParameter().getAmountCandidates()
                    .iterator(); candidateIterator.hasNext();) {
                int candidates = (int) candidateIterator.next();
                for (Iterator<Integer> seatsIterator = paramSrc.getParameter().getAmountSeats()
                        .iterator(); seatsIterator.hasNext();) {
                    int seats = (int) seatsIterator.next();

                    if (!stopped) {
                        currentlyRunning = startProcess(electionDescSrc, postAndPrepPropDesc, advanced, voters,
                                candidates, seats, this);

                        if (currentlyRunning == null) {
                            // the process creation failed
                            stopped = true;
                        }

                    }

                    while (!finished && !stopped) {
                        try {
                            // polling in 1 second steps to save cpu time
                            Thread.sleep(pollInterval);
                        } catch (InterruptedException e) {
                            ErrorLogger.log("interrupted while busy waiting! (CheckerFactory");
                        }
                    }

                    if (stopped) {
                        result.setFinished();
                        break outerLoop;
                    } else {

                        // the checker finished checking for these specific
                        // parameters without being stopped without a failure
                        // from the outside

                        //set currentlyRunnign to null, so we can catch, if the process creation failed
                        currentlyRunning = null;

                        // if the last check was successful, we have to keep
                        // checking the other ones
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
                            "The Result Object indicated a finished check, even though the CheckerFactory was still running");
                }
            } else {
                if (finished == true) {
                    ErrorLogger.log("Finished was true even though the last result was null! (CheckerFactory)");
                }
            }
        }

        controller.notifyThatFinished(this);
    }

    public void stopChecking() {
        stopped = true;
        if (currentlyRunning != null) {
            currentlyRunning.stopChecking();
        }
    }

    public void notifyThatFinished(List<String> lastResult, List<String> lastError) {
        finished = true;
        this.lastResult = lastResult;
        this.lastError = lastError;
    }

    protected abstract Result createCounterExample(List<String> result);

    /**
     * 
     * @param electionDescSrc2
     * @param postAndPrepPropDesc2
     * @param advanced
     * @param voters
     * @param candidates
     * @param seats
     * @param parent
     */
    protected abstract Checker startProcess(ElectionDescriptionSource electionDescSrc,
            PostAndPrePropertiesDescription postAndPrepPropDesc, String advanced, int voters, int candidates, int seats,
            CheckerFactory parent);

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
     * @param postAndPrepPropDescSrc
     *            the source that describes the property to be checked
     * @param paramSrc
     *            the source for the parameters
     * @param result
     *            the object in which the result should be saved in later
     * @return a new CheckerFactory
     */
    public abstract CheckerFactory getNewInstance(FactoryController controller,
            ElectionDescriptionSource electionDescSrc, PostAndPrePropertiesDescription postAndPrepPropDesc,
            ParameterSource paramSrc, Result result);

    /**
     * checks if the result from the given checker found a counterexample or not
     * 
     * @param toCheck
     *            the output of the checker to test
     * @return true, if the property wasn't violated, false if that was the case
     */
    public abstract boolean checkAssertionSuccess(List<String> toCheck);
    
    public abstract boolean checkAssertionFailure(List<String> toCheck);
    
    private ElectionType getElectionTypeFromElectionDescription() {
        InternalTypeContainer inputType = electionDescSrc.getElectionDescription().getInputType().getType();
        if (inputType.isList() && inputType.getInternalType() == InternalTypeRep.VOTER) {
            inputType = inputType.getListedType();
            if (!inputType.isList() && inputType.getInternalType() == InternalTypeRep.CANDIDATE) {
                return ElectionType.SINGLECHOICE;
            } else if (inputType.isList()) {
                inputType = inputType.getListedType();
                if (!inputType.isList()) {
                    if (null != inputType.getInternalType()) switch (inputType.getInternalType()) {
                        case APPROVAL:
                            return ElectionType.APPROVAL;
                        case WEIGHTEDAPPROVAL:
                            return ElectionType.WEIGHTEDAPPROVAL;
                        case INTEGER:
                            return ElectionType.PREFERENCE;
                        default:
                            break;
                    }
                }
            }
        }
        ErrorLogger.log("unsupported inputType of the Election");
        return null;
    }
}
