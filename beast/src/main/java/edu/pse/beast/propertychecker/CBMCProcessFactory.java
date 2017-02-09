package edu.pse.beast.propertychecker;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.highlevel.ElectionDescriptionSource;
import edu.pse.beast.highlevel.ParameterSource;
import edu.pse.beast.toolbox.ErrorLogger;
import edu.pse.beast.toolbox.FileLoader;
import edu.pse.beast.toolbox.FileSaver;
import edu.pse.beast.toolbox.SuperFolderFinder;

public class CBMCProcessFactory extends CheckerFactory {

    private final OperatingSystems os;

    // this file can be used for all checkers. So if it isn't null, it won't be
    // created, but the file that is already there will be reused
    private File toCheck = null;

    // this is the last line in the cbmc output, if the verification was
    // successful
    private final String successLine = "VERIFICATION SUCCESSFUL";

    // this is the last line in the cbmc output, if the assertion
    // failed
    private final String failureLine = "VERIFICATION FAILED";

    private final String pathToTempFolder = "/core/c_tempfiles/";

    /**
     * creates a new CBMC checker factory, that determines what operating system
     * you
     * 
     * @param controller
     *            the controller that controls this processfactory and that has
     *            to be reported to, if all the checking for this file has
     *            finished
     * @param electionDescSrc the source that describes the election
     * @param postAndPrepPropDesc the source that describes the specific property
     * @param paramSrc the source that describes all other parameters
     * @param result the result object that the end result should be written to
     */
    protected CBMCProcessFactory(FactoryController controller, ElectionDescriptionSource electionDescSrc,
            PostAndPrePropertiesDescription postAndPrepPropDesc, ParameterSource paramSrc, Result result) {
        super(controller, electionDescSrc, postAndPrepPropDesc, paramSrc, result);
        os = determineOS();
    }

    @Override
    protected Checker startProcess(ElectionDescriptionSource electionDescSrc,
            PostAndPrePropertiesDescription postAndPrepPropDesc, String advanced, int voters, int candidates, int seats,
            CheckerFactory parent) {

        String userOptions = advanced.trim().replaceAll(" +", " ");

        // remove all unnecessary whitespaces

        // create the file in which the code is saved if it doesn't exist
        // already
        if (toCheck == null) {
            // create the file only once for one factory and reuse it then

            // ErrorLogger.log("(CBMCProcessFactory) to generate code again and
            // not use the example unccoment line 52");
            toCheck = createCodeFile(electionDescSrc, postAndPrepPropDesc);
        }

        Checker startedChecker = null;

        switch (os) {
        case Linux:
            startedChecker = new LinuxProcess(voters, candidates, seats, userOptions, toCheck, parent);
            break;
        case Windows:
            startedChecker = new WindowsProcess(voters, candidates, seats, userOptions, toCheck, parent);
            break;
        default:
            ErrorLogger.log("Warning, your OS couldn't be determined or is not supported yet.");
        }

        return startedChecker;
    }

    @Override
    public boolean checkAssertionSuccess(List<String> toCheck) {
        if (toCheck.size() > 0) {
            return toCheck.get(toCheck.size() - 1).contains(successLine);
        } else {
            return false;
        }
    }

    @Override
    public boolean checkAssertionFailure(List<String> toCheck) {
        if (toCheck.size() > 0) {
            return toCheck.get(toCheck.size() - 1).contains(failureLine);
        } else {
            return false;
        }
    }

    private OperatingSystems determineOS() {
        String environment = System.getProperty("os.name");
        OperatingSystems determinedOS = null;
        if (environment.toLowerCase().contains("linux")) {
            determinedOS = OperatingSystems.Linux;
        } else if (environment.toLowerCase().contains("windows") && os == null) {
            determinedOS = OperatingSystems.Windows;
        } else if (environment.toLowerCase().contains("mac") && os == null) {
            determinedOS = OperatingSystems.Mac;
        } else {
            ErrorLogger.log("Sorry, your OS " + environment + " is not supported");
        }
        return determinedOS;
    }

    @Override
    public CheckerFactory getNewInstance(FactoryController controller, ElectionDescriptionSource electionDescSrc,
            PostAndPrePropertiesDescription postAndPrepPropDesc, ParameterSource paramSrc, Result result) {
        return new CBMCProcessFactory(controller, electionDescSrc, postAndPrepPropDesc, paramSrc, result);
    }

    @Override
    public List<Result> getMatchingResult(int amount) {
        List<Result> fittingResults = new ArrayList<Result>(amount);
        for (int i = 0; i < amount; i++) {
            fittingResults.add(new CBMCResult());
        }
        return fittingResults;
    }

    public File createCodeFile(ElectionDescriptionSource electionDescSrc,
            PostAndPrePropertiesDescription postAndPrepPropDesc) {

        // create a code generator, that creates a code file for this call only
        // one time in this factory factory;
        CBMCCodeGenerator generator = new CBMCCodeGenerator(electionDescSrc.getElectionDescription(),
                postAndPrepPropDesc);

        ArrayList<String> code = generator.getCode();

        String absolutePath = SuperFolderFinder.getSuperFolder() + pathToTempFolder;

        File file = new File(new File(absolutePath), FileLoader.getNewUniqueName(absolutePath) + ".c");

        if (file.getParentFile() == null) {
            ErrorLogger.log("Can't find a parent to your file!");
        } else if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        FileSaver.writeStringLinesToFile(code, file);
        // FileSaver.writeStringLinesToFile(generator.getCode(), file);
        return file;
    }
}
