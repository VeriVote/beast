package edu.pse.beast.propertychecker;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.highlevel.ElectionDescriptionSource;
import edu.pse.beast.highlevel.ParameterSource;
import edu.pse.beast.highlevel.PostAndPrePropertiesDescriptionSource;
import edu.pse.beast.toolbox.FileSaver;

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

    protected CheckerFactory(FactoryController controller, ElectionDescriptionSource electionDescSrc,
            PostAndPrePropertiesDescription postAndPrepPropDesc, ParameterSource paramSrc, Result result) {

        this.controller = controller;
        this.electionDescSrc = electionDescSrc;
        this.postAndPrepPropDesc = postAndPrepPropDesc;
        this.paramSrc = paramSrc;
        this.result = result;
    }

    public void run() {

        ArrayList<String> code = new CBMCCodeGenerator(null, null).getCode();

        File file = new File("/Beast/src/main/resources/tmp/" + CheckerFactoryFactory.newUniqueName());

        FileSaver.writeStringLinesToFile(code, file);

        String advanced = paramSrc.getParameter().getAdvanced();

        outerLoop: for (Iterator<Integer> voteIterator = paramSrc.getParameter().getAmountVoters()
                .iterator(); voteIterator.hasNext();) {
            int voters = (int) voteIterator.next();
            for (Iterator<Integer> candidateIterator = paramSrc.getParameter().getAmountVoters()
                    .iterator(); candidateIterator.hasNext();) {
                int candidates = (int) candidateIterator.next();
                for (Iterator<Integer> seatsIterator = paramSrc.getParameter().getAmountVoters()
                        .iterator(); seatsIterator.hasNext();) {
                    int seats = (int) seatsIterator.next();

                    if (!stopped) {
                        startProcess(file, advanced + " -D V=" + voters + " -D C=" + candidates + " -D S=" + seats,
                                this);
                    }

                    while (!finished && !stopped) {
                        try {
                            // polling in 1 second steps to save cpu time
                            Thread.sleep(pollInterval);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    if (stopped) {
                        result.setFinished();
                        break outerLoop;
                    } else {
                        // the checker finished checking for these specific
                        // parameters;

                        if (checkResult(lastResult)) {
                            finished = false;
                        } else {
                            result.setResult(lastResult);
                            break outerLoop;
                        }

                    }

                }
            }
        }

        if (checkResult(lastResult)) {
            result.setSuccess();
        }
        controller.notifyThatFinished();
    }

    public void stopChecking() {
        stopped = true;
        currentlyRunning.stopChecking();
    }

    public void notifyThatFinished(List<String> lastResult) {
        finished = true;
        this.lastResult = lastResult;
    }

    protected abstract Result createCounterExample(List<String> result);

    /**
     * 
     * @param toCheck
     * @param arguments
     * @param parent
     * @return
     */
    protected abstract Checker startProcess(File toCheck, String arguments, CheckerFactory parent);

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
    public abstract boolean checkResult(List<String> toCheck);
}
