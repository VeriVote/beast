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
    private Checker currentlyRunning;
    private Thread workingThread;
    private boolean stopped = false;

    private CheckerFactory(FactoryController controller, ElectionDescriptionSource electionDescSrc,
            PostAndPrePropertiesDescriptionSource postAndPrepPropDescSrc, ParameterSource paramSrc, Result result) {

        this.controller = controller;
        this.electionDescSrc = electionDescSrc;
        this.postAndPrepPropDescSrc = postAndPrepPropDescSrc;
        this.paramSrc = paramSrc;
        this.result = result;
    }

    public void run() {

        ArrayList<String> code = new CBMCCodeGenerator(null, null).getCode();

        // Get the file reference
        Path path = Paths.get("/Beast/src/main/resources/tmp/temp.c");

        // FileSaver.writeStringLinesToFile(text, path, fileName);

        // Use try-with-resource to get auto-closeable writer instance

        paramSrc.getParameter().getAmountVoters();

        paramSrc.getParameter().getAmountCandidates();

        paramSrc.getParameter().getAmountSeats();

        String advanced = paramSrc.getParameter().getAdvanced();

        for (Iterator<Integer> voteIterator = paramSrc.getParameter().getAmountVoters().iterator(); voteIterator
                .hasNext();) {
            int voters = (int) voteIterator.next();
            for (Iterator<Integer> candidateIterator = paramSrc.getParameter().getAmountVoters()
                    .iterator(); candidateIterator.hasNext();) {
                int candidates = (int) candidateIterator.next();
                for (Iterator<Integer> seatsIterator = paramSrc.getParameter().getAmountVoters()
                        .iterator(); seatsIterator.hasNext();) {
                    int seats = (int) seatsIterator.next();

                }
            }
        }

    }

    public void stopChecking() {
        currentlyRunning.interruptChecking();
    }
    
    protected abstract void startProcess(File toCheck, String callParams, Result result);
    
    /**
     * 
     * @return the result object that belongs to the Checker produced by this
     *         factory
     */
    public abstract List<Result> getFittingResult(int size);
}

