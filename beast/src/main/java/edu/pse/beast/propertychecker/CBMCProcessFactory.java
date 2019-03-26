package edu.pse.beast.propertychecker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.datatypes.electioncheckparameter.ElectionCheckParameter;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.highlevel.javafx.GUIController;
import edu.pse.beast.toolbox.ErrorForUserDisplayer;
import edu.pse.beast.toolbox.ErrorLogger;
import edu.pse.beast.toolbox.FileLoader;
import edu.pse.beast.toolbox.FileSaver;
import edu.pse.beast.toolbox.SuperFolderFinder;

public class CBMCProcessFactory extends CheckerFactory {
    private static final String WHITESPACE = " ";

    // this is the last line in the cbmc output, if the verification was
    // successful
    private static final String SUCCESS_LINE = "VERIFICATION SUCCESSFUL";

    // this is the last line in the cbmc output, if the assertion
    // failed
    private static final String FAILURE_LINE = "VERIFICATION FAILED";

    private static final String PATH_TO_TEMP_FOLDER = "/core/generated_c_files/";

    private final OperatingSystems os;

    // this file can be used for all checkers. So if it is not null, it will not be
    // created, but the file that is already there will be reused
    private File toCheck = null;

    /**
     * creates a new CBMC checker factory, that determines what operating system you
     *
     * @param controller          the controller that controls this processfactory
     *                            and that has to be reported to, if all the
     *                            checking for this file has finished
     * @param electionDesc        the source that describes the election
     * @param result              the result object that the end result should be
     *                            written to
     * @param parameter           the source that describes all other parameters
     */
    protected CBMCProcessFactory(FactoryController controller, ElectionDescription electionDesc,
                                 Result result, ElectionCheckParameter parameter) {
        super(controller, electionDesc, result, parameter);
        os = determineOS();
    }
    //
    // public CBMCProcessFactory(FactoryController controller, File toCheck,
    // ParameterSource paramSrc, Result result, boolean isMargin) {
    // super(controller, paramSrc, result, isMargin);
    // os = determineOS();
    // }

    // @Override
    // protected Checker startProcessCheck(ElectionDescriptionSource
    // electionDescSrc,
    // PostAndPrePropertiesDescription postAndPrepPropDesc, String advanced, int
    // voters, int candidates,
    // int seats, CheckerFactory parent) {
    //
    // String userOptions = advanced.trim().replaceAll(" +", WHITESPACE);
    //
    // // remove all unnecessary white spaces
    //
    // // create the file in which the code is saved if it does not exist
    // // already
    // if (toCheck == null) {
    // // create the file only once for each factory and reuse it then
    // toCheck = createCodeFileCheck(electionDescSrc, postAndPrepPropDesc);
    // }
    //
    // Checker startedChecker = null;
    //
    // switch (os) {
    // case Linux:
    // startedChecker = new LinuxProcess(voters, candidates, seats, userOptions,
    // toCheck, parent);
    // break;
    // case Windows:
    // startedChecker = new WindowsProcess(voters, candidates, seats, userOptions,
    // toCheck, parent);
    // break;
    // case Mac:
    // ErrorForUserDisplayer.displayError(
    // "MacOS is not supported yet, please implement the class CBMCProcess and add
    // it then here in the "
    // + "CBMCProcessFactory to be created");
    // break;
    // default:
    // ErrorLogger.log("Warning, your OS could not be determined or is not supported
    // yet.");
    // }
    //
    // return startedChecker;
    // }
    //
    // @Override
    // protected Checker startProcessMargin(ElectionDescriptionSource
    // electionDescSrc,
    // PostAndPrePropertiesDescription postAndPrepPropDesc, String advanced,
    // CheckerFactory parent) {
    // String userOptions = advanced.trim().replaceAll(" +", WHITESPACE);
    //
    // // remove all unnecessary white spaces
    //
    // // create the file in which the code is saved if it does not exist
    // // already
    // if (toCheck == null) {
    // // create the file only once for each factory and reuse it then
    // toCheck = createCodeFileMargin(electionDescSrc, postAndPrepPropDesc);
    // }
    //
    // Checker startedChecker = null;
    //
    // switch (os) {
    // case Linux:
    // startedChecker = new LinuxProcess(ElectionSimulation.getNumVoters(),
    // ElectionSimulation.getNumCandidates(), ElectionSimulation.getNumSeats(),
    // userOptions, this.toCheck, parent);
    // break;
    // case Windows:
    // startedChecker = new WindowsProcess(ElectionSimulation.getNumVoters(),
    // ElectionSimulation.getNumCandidates(), ElectionSimulation.getNumSeats(),
    // userOptions, this.toCheck, parent);
    // break;
    // case Mac:
    // ErrorForUserDisplayer.displayError(
    // "MacOS is not supported yet, please implement the class CBMCProcess and add
    // it then here in the "
    // + "CBMCProcessFactory to be created");
    // break;
    // default:
    // ErrorLogger.log("Warning, your OS could not be determined or is not supported
    // yet.");
    // }
    //
    // return startedChecker;
    // }
    //
    //
    // @Override
    // protected Checker startProcessTest(ElectionDescriptionSource electionDescSrc,
    // PostAndPrePropertiesDescription postAndPrepPropDesc, String advanced,
    // CheckerFactory parent) {
    // String userOptions = advanced.trim().replaceAll(" +", WHITESPACE);
    //
    // // remove all unnecessary whitespaces
    //
    // // create the file in which the code is saved if it does not exist
    // // already
    // if (toCheck == null) {
    // // create the file only once for each factory and reuse it then
    // toCheck = createCodeFileTest(electionDescSrc, postAndPrepPropDesc);
    // }
    //
    // Checker startedChecker = null;
    //
    // switch (os) {
    // case Linux:
    // startedChecker = new LinuxProcess(ElectionSimulation.getNumVoters(),
    // ElectionSimulation.getNumCandidates(), ElectionSimulation.getNumSeats(),
    // userOptions, this.toCheck, parent);
    // break;
    // case Windows:
    // startedChecker = new WindowsProcess(ElectionSimulation.getNumVoters(),
    // ElectionSimulation.getNumCandidates(), ElectionSimulation.getNumSeats(),
    // userOptions, this.toCheck, parent);
    // break;
    // case Mac:
    // ErrorForUserDisplayer.displayError(
    // "MacOS is not supported yet, please implement the class CBMCProcess and add
    // it then here in the "
    // + "CBMCProcessFactory to be created");
    // break;
    // default:
    // ErrorLogger.log("Warning, your OS could not be determined or is not supported
    // yet.");
    // }
    //
    // return startedChecker;
    // }
    //
    // @Override
    // protected Checker startProcess(ElectionDescriptionSource electionDescSrc,
    // PreAndPostConditionsDescription postAndPrepPropDesc, String advanced, int
    // voters, int candidates,
    // int seats, CheckerFactory parent) {
    //
    // String userOptions = advanced.trim().replaceAll(" +", WHITESPACE);
    //
    // // remove all unnecessary white spaces
    //
    // // create the file in which the code is saved if it does not exist
    // // already
    // if (toCheck == null) {
    // // create the file only once for each factory and reuse it then
    // toCheck = createCodeFile(electionDescSrc, postAndPrepPropDesc);
    // }
    //
    // Checker startedChecker = null;
    //
    // switch (os) {
    // case Linux:
    // startedChecker = new LinuxProcess(voters, candidates, seats, userOptions,
    // toCheck, parent);
    // break;
    // case Windows:
    // startedChecker = new WindowsProcess(voters, candidates, seats, userOptions,
    // toCheck, parent);
    // break;
    // case Mac:
    // ErrorForUserDisplayer.displayError(
    // "MacOS is not supported yet, please implement the class CBMCProcess and add
    // it then here in the "
    // + "CBMCProcessFactory to be created");
    // break;
    // default:
    // ErrorLogger.log("Warning, your OS could not be determined or is not supported
    // yet.");
    // }
    //
    // return startedChecker;
    // }
    //
    // @Override
    // protected Checker startProcess(File toCheck, String advanced, int voters, int
    // candidates, int seats,
    // CheckerFactory parent) {
    // String userOptions = advanced.trim().replaceAll(" +", WHITESPACE);
    //
    // // remove all unnecessary white spaces
    //
    // // create the file in which the code is saved if it does not exist
    // // already
    // if (this.toCheck == null) {
    // // create the file only once for each factory and reuse it then
    // this.toCheck = toCheck;
    // }
    //
    // Checker startedChecker = null;
    //
    // switch (os) {
    // case Linux:
    // startedChecker = new LinuxProcess(voters, candidates, seats, userOptions,
    // this.toCheck, parent);
    // break;
    // case Windows:
    // startedChecker = new WindowsProcess(voters, candidates, seats, userOptions,
    // this.toCheck, parent);
    // break;
    // case Mac:
    // ErrorForUserDisplayer.displayError(
    // "MacOS is not supported yet, please implement the class CBMCProcess and add
    // it then here in the "
    // + "CBMCProcessFactory to be created");
    // break;
    // default:
    // ErrorLogger.log("Warning, your OS could not be determined or is not supported
    // yet.");
    // }
    //
    // return startedChecker;
    // }

    @Override
    public boolean checkAssertionSuccess(List<String> toCheck) {
        if (toCheck != null && toCheck.size() > 0) {
            return toCheck.get(toCheck.size() - 1).contains(SUCCESS_LINE);
        } else {
            return false;
        }
    }

    @Override
    public boolean checkAssertionFailure(List<String> toCheck) {
        if (toCheck != null && toCheck.size() > 0) {
            return toCheck.get(toCheck.size() - 1).contains(FAILURE_LINE);
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
    public CheckerFactory getNewInstance(FactoryController controller,
                                         ElectionDescription electionDesc,
                                         Result result,
                                         ElectionCheckParameter parameter) {
        return new CBMCProcessFactory(controller, electionDesc, result, parameter);
    }
    //
    // @Override
    // public CheckerFactory getNewInstance(FactoryController controller, File
    // toCheck, ParameterSource paramSrc,
    // Result result, boolean isMargin) {
    // return new CBMCProcessFactory(controller, toCheck, paramSrc, result,
    // isMargin);
    // }

    @Override
    public Result getMatchingResult() {
        return new CBMCResult();
    }

    /**
     * creates a new c-Code file that then can be used by all the underlying
     * checkers to check it with cbmc
     *
     * @param electionDesc        the source that describes the election
     * @param postAndPrepPropDesc the property that this specific process factory
     *                            should check
     * @return a file that contains the generated code from the two above variables
     */

    public File createCodeFileCheck(ElectionDescription electionDesc,
                                    PreAndPostConditionsDescription postAndPrepPropDesc) {
        // create a code generator, that creates a code file for this call only
        // one time in this factory factory;
        CBMCCodeGenerator generator = new CBMCCodeGenerator(electionDesc, postAndPrepPropDesc);
        ArrayList<String> code = generator.getCode();
        String absolutePath = SuperFolderFinder.getSuperFolder() + PATH_TO_TEMP_FOLDER;
        File file =
            new File(new File(absolutePath),
                     FileLoader.getNewUniqueName(absolutePath) + ".c");
        if (file.getParentFile() == null) {
            ErrorLogger.log("Cannot find a parent to your file!");
        } else if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        FileSaver.writeStringLinesToFile(code, file);
        // FileSaver.writeStringLinesToFile(generator.getCode(), file);
        return file;
    }

    /**
     * creates a new c-Code file that then can be used by all the underlying
     * checkers to check it with cbmc
     *
     * @param electionDesc        the source that describes the election
     * @param postAndPrepPropDesc the property that this specific process factory
     *                            should check
     * @param margin              the margin
     * @param origResult          original result
     * @param inputData           input data
     * @return a file that contains the generated code from the two above variables
     */
    public File createCodeFileMargin(ElectionDescription electionDesc,
                                     PreAndPostConditionsDescription postAndPrepPropDesc,
                                     int margin,
                                     List<String> origResult,
                                     String[][] inputData) {
        // create a code generator, that creates a code file for this call only
        // one time in this factory factory;
        CBMCCodeGenerator generator =
                new CBMCCodeGenerator(electionDesc, postAndPrepPropDesc, margin,
                                      origResult, inputData);
        ArrayList<String> code = generator.getCode();
        String absolutePath = SuperFolderFinder.getSuperFolder() + PATH_TO_TEMP_FOLDER;
        File file =
            new File(new File(absolutePath),
                     FileLoader.getNewUniqueName(absolutePath) + ".c");
        if (file.getParentFile() == null) {
            ErrorLogger.log("Cannot find a parent to your file!");
        } else if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        FileSaver.writeStringLinesToFile(code, file);
        // FileSaver.writeStringLinesToFile(generator.getCode(), file);
        return file;
    }

    /**
     * creates a new c-Code file that then can be used by all the underlying
     * checkers to check it with cbmc
     *
     * @param electionDesc        the source that describes the election
     * @param postAndPrepPropDesc the property that this specific process factory
     *                            should check
     * @param inputData           input data
     * @return a file that contains the generated code from the two above variables
     */
    public File createCodeFileTest(ElectionDescription electionDesc,
            PreAndPostConditionsDescription postAndPrepPropDesc, String[][] inputData) {
        // create a code generator, that creates a code file for this call only
        // one time in this factory factory;
        CBMCCodeGenerator generator =
                new CBMCCodeGenerator(electionDesc, postAndPrepPropDesc, inputData);
        ArrayList<String> code = generator.getCode();
        String absolutePath = SuperFolderFinder.getSuperFolder() + PATH_TO_TEMP_FOLDER;
        File file =
            new File(new File(absolutePath),
                     FileLoader.getNewUniqueName(absolutePath) + ".c");
        if (file.getParentFile() == null) {
            ErrorLogger.log("Cannot find a parent to your file!");
        } else if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        FileSaver.writeStringLinesToFile(code, file);
        // FileSaver.writeStringLinesToFile(generator.getCode(), file);
        return file;
    }

    //
    // /**
    // * creates a new c-Code file that then can be used by all the underlying
    // * checkers to check it with cbmc
    // *
    // * @param electionDescSrc the source that describes the election
    // * @param postAndPrepPropDesc the property that this specific process factory
    // should check
    // * @return a file that contains the generated code from the two above
    // variables
    // */
    // public File createCodeFile(ElectionDescriptionSource electionDescSrc,
    // PostAndPrePropertiesDescription postAndPrepPropDesc) {
    //
    // // create a code generator, that creates a code file for this call only
    // // one time in this factory factory;
    // CBMCCodeGenerator generator = new
    // CBMCCodeGenerator(electionDescSrc.getElectionDescription(),
    // postAndPrepPropDesc, false);
    //
    // ArrayList<String> code = generator.getCode();
    //
    // String absolutePath = SuperFolderFinder.getSuperFolder() + pathToTempFolder;
    //
    // File file = new File(new File(absolutePath),
    // FileLoader.getNewUniqueName(absolutePath) + ".c");
    //
    // if (file.getParentFile() == null) {
    // ErrorLogger.log("Cannot find a parent to your file!");
    // } else if (!file.getParentFile().exists()) {
    // file.getParentFile().mkdirs();
    // }
    //
    // FileSaver.writeStringLinesToFile(code, file);
    // // FileSaver.writeStringLinesToFile(generator.getCode(), file);
    // return file;
    // }

    // /**
    // * creates a new c-Code file that then can be used by all the underlying
    // * checkers to check it with cbmc
    // *
    // * @param electionDescSrc the source that describes the election
    // * @param postAndPrepPropDesc the property that this specific process factory
    // should check
    // * @return a file that contains the generated code from the two above
    // variables
    // */
    // public File createCodeFileCheck(ElectionDescriptionSource electionDescSrc,
    // PostAndPrePropertiesDescription postAndPrepPropDesc) {
    //
    // // create a code generator, that creates a code file for this call only
    // // one time in this factory factory;
    // CBMCCodeGenerator generator = new
    // CBMCCodeGenerator(electionDescSrc.getElectionDescription(),
    // postAndPrepPropDesc, false);
    //
    // ArrayList<String> code = generator.getCode();
    //
    // String absolutePath = SuperFolderFinder.getSuperFolder() + pathToTempFolder;
    //
    // File file = new File(new File(absolutePath),
    // FileLoader.getNewUniqueName(absolutePath) + ".c");
    //
    // if (file.getParentFile() == null) {
    // ErrorLogger.log("Cannot find a parent to your file!");
    // } else if (!file.getParentFile().exists()) {
    // file.getParentFile().mkdirs();
    // }
    //
    // FileSaver.writeStringLinesToFile(code, file);
    // // FileSaver.writeStringLinesToFile(generator.getCode(), file);
    // return file;
    // }
    //
    // /**
    // * creates a new c-Code file that then can be used by all the underlying
    // * checkers to check it with cbmc
    // *
    // * @param electionDescSrc the source that describes the election
    // * @param postAndPrepPropDesc the property that this specific process factory
    // should check
    // * @return a file that contains the generated code from the two above
    // variables
    // */
    // public File createCodeFileMargin(ElectionDescriptionSource electionDescSrc,
    // PostAndPrePropertiesDescription postAndPrepPropDesc) {
    //
    // // create a code generator, that creates a code file for this call only
    // // one time in this factory factory;
    // CBMCCodeGenerator generator = new
    // CBMCCodeGenerator(electionDescSrc.getElectionDescription(),
    // postAndPrepPropDesc, true);
    //
    // ArrayList<String> code = generator.getCode();
    //
    // String absolutePath = SuperFolderFinder.getSuperFolder() + pathToTempFolder;
    //
    // File file = new File(new File(absolutePath),
    // FileLoader.getNewUniqueName(absolutePath) + ".c");
    //
    // if (file.getParentFile() == null) {
    // ErrorLogger.log("Cannot find a parent to your file!");
    // } else if (!file.getParentFile().exists()) {
    // file.getParentFile().mkdirs();
    // }
    //
    // FileSaver.writeStringLinesToFile(code, file);
    // // FileSaver.writeStringLinesToFile(generator.getCode(), file);
    // return file;
    // }
    //
    // /**
    // * creates a new c-Code file that then can be used by all the underlying
    // * checkers to check it with cbmc
    // *
    // * @param electionDescSrc the source that describes the election
    // * @param postAndPrepPropDesc the property that this specific process factory
    // should check
    // * @return a file that contains the generated code from the two above
    // variables
    // */
    // public File createCodeFileTest(ElectionDescriptionSource electionDescSrc,
    // PostAndPrePropertiesDescription postAndPrepPropDesc) {
    //
    // // create a code generator, that creates a code file for this call only
    // // one time in this factory factory;
    // CBMCCodeGenerator generator = new
    // CBMCCodeGenerator(electionDescSrc.getElectionDescription(),
    // postAndPrepPropDesc, true);
    //
    // ArrayList<String> code = generator.getCode();
    //
    // String absolutePath = SuperFolderFinder.getSuperFolder() + pathToTempFolder;
    //
    // File file = new File(new File(absolutePath),
    // FileLoader.getNewUniqueName(absolutePath) + ".c");
    //
    // if (file.getParentFile() == null) {
    // ErrorLogger.log("Cannot find a parent to your file!");
    // } else if (!file.getParentFile().exists()) {
    // file.getParentFile().mkdirs();
    // }
    //
    // FileSaver.writeStringLinesToFile(code, file);
    // // FileSaver.writeStringLinesToFile(generator.getCode(), file);
    // return file;
    // }

    @Override
    protected void cleanUp() {
        if (GUIController.getController().getDeleteTmpFiles()
                && toCheck != null
                && toCheck.exists()) {
            toCheck.delete();
        }
    }

    @Override
    protected Checker startProcessCheck(ElectionDescription electionDesc,
                                        PreAndPostConditionsDescription postAndPrepPropDesc,
                                        String advanced, int voters, int candidates,
                                        int seats, CheckerFactory parent, Result result) {
        String userOptions = advanced.trim().replaceAll(" +", WHITESPACE);
        // remove all unnecessary white spaces
        // create the file in which the code is saved if it does not exist
        // already
        if (toCheck == null) {
            // create the file only once for each factory and reuse it then
            toCheck = createCodeFileCheck(electionDesc, postAndPrepPropDesc);
        }
        Checker startedChecker = null;
        switch (os) {
        case Linux:
            startedChecker =
                new LinuxProcess(voters, candidates, seats, userOptions,
                                 toCheck, parent, result);
            break;
        case Windows:
            startedChecker =
                new WindowsProcess(voters, candidates, seats, userOptions,
                                   toCheck, parent, result);
            break;
        case Mac:
            ErrorForUserDisplayer.displayError(
                    "MacOS is not supported yet, please implement the "
                            + "class CBMCProcess and add it then here in the "
                            + "CBMCProcessFactory to be created");
            break;
        default:
            ErrorLogger.log("Warning, your OS could not be determined or is not supported yet.");
        }
        return startedChecker;
    }

    @Override
    protected Checker startProcessMargin(ElectionDescription electionDesc,
                                         PreAndPostConditionsDescription postAndPrepPropDesc,
                                         String advanced, int voters, int candidates, int seats,
                                         CheckerFactory parent, int margin, List<String> origResult,
                                         String[][] votingData, Result result) {
        String userOptions = advanced.trim().replaceAll(" +", WHITESPACE);
        // remove all unnecessary white spaces
        // create the file in which the code is saved
        toCheck = createCodeFileMargin(electionDesc, postAndPrepPropDesc,
                                       margin, origResult, votingData);

        Checker startedChecker = null;
        switch (os) {
        case Linux:
            startedChecker = new LinuxProcess(voters, candidates, seats, userOptions,
                                              toCheck, parent, result);
            break;
        case Windows:
            startedChecker = new WindowsProcess(voters, candidates, seats, userOptions,
                                                toCheck, parent, result);
            break;
        case Mac:
            ErrorForUserDisplayer.displayError(
                    "MacOS is not supported yet, please implement the class CBMCProcess "
                            + "and add it then here in the "
                            + "CBMCProcessFactory to be created");
            break;
        default:
            ErrorLogger.log("Warning, your OS could not be determined or is not supported yet.");
        }
        return startedChecker;
    }

    @Override
    protected Checker startProcessTest(ElectionDescription electionDesc,
                                       PreAndPostConditionsDescription postAndPrepPropDesc,
                                       String advanced, int voters, int candidates, int seats,
                                       CheckerFactory parent, String[][] votingData,
                                       Result result) {
        String userOptions = advanced.trim().replaceAll(" +", WHITESPACE);
        // remove all unnecessary white spaces
        // create the file in which the code is saved
        toCheck = createCodeFileTest(electionDesc, postAndPrepPropDesc, votingData);
        Checker startedChecker = null;
        switch (os) {
        case Linux:
            startedChecker = new LinuxProcess(voters, candidates, seats, userOptions,
                                              toCheck, parent, result);
            break;
        case Windows:
            startedChecker = new WindowsProcess(voters, candidates, seats, userOptions,
                                                toCheck, parent, result);
            break;
        case Mac:
            ErrorForUserDisplayer.displayError(
                    "MacOS is not supported yet, please implement the class CBMCProcess "
                            + "and add it then here in the "
                            + "CBMCProcessFactory to be created");
            break;
        default:
            ErrorLogger.log("Warning, your OS could not be determined or is not supported yet.");
        }
        return startedChecker;
    }
}