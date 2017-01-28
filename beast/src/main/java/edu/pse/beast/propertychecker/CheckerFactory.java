package edu.pse.beast.propertychecker;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.FileHandler;

//import com.google.common.io.Files;

import edu.pse.beast.datatypes.ElectionCheckParameter;
import edu.pse.beast.datatypes.descofvoting.ElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.highlevel.ElectionDescriptionSource;
import edu.pse.beast.highlevel.ParameterSource;
import edu.pse.beast.highlevel.PostAndPrePropertiesDescriptionSource;
import edu.pse.beast.toolbox.FileSaver;

public abstract class CheckerFactory implements Runnable {

    private final FactoryController controller;
    private final ElectionDescriptionSource electionDescSrc;
    private final PostAndPrePropertiesDescriptionSource postAndPrepPropDescSrc;
    private final ParameterSource paramSrc;
    private final Result result;
    private final long pollInterval = 1000;

    private Checker currentlyRunning;
    private boolean stopped = false;
    private boolean finished = false;
    private List<String> lastResult;

    protected CheckerFactory(FactoryController controller, ElectionDescriptionSource electionDescSrc,
            PostAndPrePropertiesDescriptionSource postAndPrepPropDescSrc, ParameterSource paramSrc, Result result) {

        this.controller = controller;
        this.electionDescSrc = electionDescSrc;
        this.postAndPrepPropDescSrc = postAndPrepPropDescSrc;
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
                        startProcess(file, advanced + " -D V=" + voters + " -D C=" + candidates + " -D S=" + seats, this);
                    }

                    while (!finished && !stopped) {
                        try {
                            // polling in 1 second steps to save cpu time
                            Thread.sleep(pollInterval);
                        } catch (InterruptedException e) {
                            // keep on polling
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
                        }
                        
                    }

                }
            }
        }
    }

    protected abstract Result createCounterExample(List<String> result);

    public void stopChecking() {
        stopped = true;
        currentlyRunning.stopChecking();
    }

    public void notifyThatFinished(List<String> lastResult) {
        finished = true;
        this.lastResult = lastResult;
    }

    protected abstract Checker startProcess(File toCheck, String arguments, CheckerFactory parent);

    /**
     * 
     * @return the result object that belongs to the Checker produced by this
     *         factory
     */
    public abstract List<Result> getFittingResult(int size);
    
    public abstract CheckerFactory getNewInstance(FactoryController controller, ElectionDescriptionSource electionDescSrc,
            PostAndPrePropertiesDescriptionSource postAndPrepPropDescSrc, ParameterSource paramSrc, Result result);
    
    /**
     * checks if the result from the given checker found a counterexample or not
     * @param toCheck the output of the checker to test
     * @return true, if the property wasn't violated, false if that was the case
     */
    public abstract boolean checkResult(List<String> toCheck);
}
