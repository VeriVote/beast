package edu.pse.beast.propertychecker;

import java.io.File;
import java.util.ArrayList;

import edu.pse.beast.datatypes.electioncheckparameter.ElectionCheckParameter;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.electionsimulator.ElectionSimulationData;
import edu.pse.beast.highlevel.javafx.GUIController;
import edu.pse.beast.toolbox.ErrorForUserDisplayer;
import edu.pse.beast.toolbox.ErrorLogger;
import edu.pse.beast.toolbox.FileLoader;
import edu.pse.beast.toolbox.FileSaver;
import edu.pse.beast.toolbox.SuperFolderFinder;

/**
 * A factory for creating CBMCProcess objects.
 *
 * <p>TODO clean up this file.
 *
 * @author Lukas Stapelbroek
 */
public final class CBMCProcessFactory extends CheckerFactory {
    /** The Constant WHITESPACE. */
    private static final String WHITESPACE = " ";

    /** The Constant TO_REPLACE. */
    private static final String TO_REPLACE = WHITESPACE + "+";

    /** The Constant CANNOT_FIND_PARENT. */
    private static final String CANNOT_FIND_PARENT =
            "Cannot find a parent to your file!";

    /** The Constant MAC_OS_NOT_SUPPORTED. */
    private static final String MAC_OS_NOT_SUPPORTED =
            "MacOS is not supported yet, please implement the "
            + "class CBMCProcess and add it then here in the "
            + "CBMCProcessFactory to be created.";

    /** The Constant OS_COULD_NOT_BE_DETERMINED. */
    private static final String OS_COULD_NOT_BE_DETERMINED =
            "Warning, your OS could not be determined or is not supported yet.";

    /** The Constant PATH_TO_TEMP_FOLDER. */
    private static final String PATH_TO_TEMP_FOLDER = "/core/generated_c_files/";

    /** The os. */
    private final OperatingSystems os;

    /**
     * The file to check:
     * This file can be used for all checkers.
     * So if it is not null, it will not be created,
     * but the file that is already there will be reused. */
    private File toCheck;

    /**
     * Creates a new CBMC checker factory, that determines what operating system
     * is running.
     *
     * @param controller
     *            the controller that controls this processfactory and that has
     *            to be reported to, if all the checking for this file has
     *            finished
     * @param electionDesc
     *            the source that describes the election
     * @param result
     *            the result object that the end result should be written to
     * @param parameter
     *            the source that describes all other parameters
     */
    protected CBMCProcessFactory(final FactoryController controller,
                                 final ElectionDescription electionDesc,
                                 final Result result,
                                 final ElectionCheckParameter parameter) {
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
    // String userOptions = advanced.trim().replaceAll(TO_REPLACE, WHITESPACE);
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
    // startedChecker = new WindowsProcess(voters, candidates, seats,
    // userOptions,
    // toCheck, parent);
    // break;
    // case Mac:
    // ErrorForUserDisplayer.displayError(
    // "MacOS is not supported yet, please implement the class CBMCProcess and
    // add
    // it then here in the "
    // + "CBMCProcessFactory to be created");
    // break;
    // default:
    // ErrorLogger.log("Warning, your OS could not be determined or is not
    // supported
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
    // String userOptions = advanced.trim().replaceAll(TO_REPLACE, WHITESPACE);
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
    // "MacOS is not supported yet, please implement the class CBMCProcess and
    // add
    // it then here in the "
    // + "CBMCProcessFactory to be created");
    // break;
    // default:
    // ErrorLogger.log("Warning, your OS could not be determined or is not
    // supported
    // yet.");
    // }
    //
    // return startedChecker;
    // }
    //
    //
    // @Override
    // protected Checker startProcessTest(ElectionDescriptionSource
    // electionDescSrc,
    // PostAndPrePropertiesDescription postAndPrepPropDesc, String advanced,
    // CheckerFactory parent) {
    // String userOptions = advanced.trim().replaceAll(TO_REPLACE, WHITESPACE);
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
    // "MacOS is not supported yet, please implement the class CBMCProcess and
    // add
    // it then here in the "
    // + "CBMCProcessFactory to be created");
    // break;
    // default:
    // ErrorLogger.log("Warning, your OS could not be determined or is not
    // supported
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
    // String userOptions = advanced.trim().replaceAll(TO_REPLACE, WHITESPACE);
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
    // startedChecker = new WindowsProcess(voters, candidates, seats,
    // userOptions,
    // toCheck, parent);
    // break;
    // case Mac:
    // ErrorForUserDisplayer.displayError(
    // "MacOS is not supported yet, please implement the class CBMCProcess and
    // add
    // it then here in the "
    // + "CBMCProcessFactory to be created");
    // break;
    // default:
    // ErrorLogger.log("Warning, your OS could not be determined or is not
    // supported
    // yet.");
    // }
    //
    // return startedChecker;
    // }
    //
    // @Override
    // protected Checker startProcess(File toCheck, String advanced, int voters,
    // int
    // candidates, int seats,
    // CheckerFactory parent) {
    // String userOptions = advanced.trim().replaceAll(TO_REPLACE, WHITESPACE);
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
    // startedChecker = new WindowsProcess(voters, candidates, seats,
    // userOptions,
    // this.toCheck, parent);
    // break;
    // case Mac:
    // ErrorForUserDisplayer.displayError(
    // "MacOS is not supported yet, please implement the class CBMCProcess and
    // add
    // it then here in the "
    // + "CBMCProcessFactory to be created");
    // break;
    // default:
    // ErrorLogger.log("Warning, your OS could not be determined or is not
    // supported
    // yet.");
    // }
    //
    // return startedChecker;
    // }

    /**
     * Determine OS.
     *
     * @return the operating systems
     */
    public static OperatingSystems determineOS() { // TODO extract to UTIl
        String environment = System.getProperty("os.name");
        OperatingSystems determinedOS = null;
        if (environment.toLowerCase().contains("linux")) {
            determinedOS = OperatingSystems.Linux;
        } else if (environment.toLowerCase().contains("windows")) {
            determinedOS = OperatingSystems.Windows;
        } else if (environment.toLowerCase().contains("mac")) {
            determinedOS = OperatingSystems.Mac;
        } else {
            ErrorLogger.log("Sorry, your OS (" + environment
                            + ") is not supported.");
        }
        return determinedOS;
    }

    @Override
    public CheckerFactory getNewInstance(final FactoryController controller,
                                         final ElectionDescription electionDesc,
                                         final Result result,
                                         final ElectionCheckParameter parameter) {
        return new CBMCProcessFactory(controller, electionDesc, result,
                                      parameter);
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
     * Creates a new c-Code file that then can be used by all the underlying
     * checkers to check it with cbmc.
     *
     * @param electionDesc
     *            the source that describes the election
     * @param postAndPrepPropDesc
     *            the property that this specific process factory should check
     * @return a file that contains the generated code from the two above
     *         variables
     */

    public File createCodeFileCheck(final ElectionDescription electionDesc,
                                    final PreAndPostConditionsDescription postAndPrepPropDesc) {
        // create a code generator, that creates a code file for this call only
        // one time in this factory factory;
        CBMCCodeGenerator generator =
                new CBMCCodeGenerator(electionDesc, postAndPrepPropDesc);
        ArrayList<String> code = generator.getCode();
        String absolutePath = SuperFolderFinder.getSuperFolder()
                                + PATH_TO_TEMP_FOLDER;
        File file = new File(new File(absolutePath),
                             FileLoader.getNewUniqueName(absolutePath)
                             + FileLoader.C_FILE_ENDING);
        if (file.getParentFile() == null) {
            ErrorLogger.log(CANNOT_FIND_PARENT);
        } else if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        FileSaver.writeStringLinesToFile(code, file);
        // FileSaver.writeStringLinesToFile(generator.getCode(), file);
        return file;
    }

    /**
     * Creates a new c-Code file that then can be used by all the underlying
     * checkers to check it with cbmc.
     *
     * @param electionDesc
     *            the source that describes the election
     * @param postAndPrepPropDesc
     *            the property that this specific process factory should check
     * @param margin
     *            the margin
     * @param origResult
     *            original result
     * @param inputData
     *            input data
     * @return a file that contains the generated code from the two above
     *         variables
     */
    public File createCodeFileMargin(final ElectionDescription electionDesc,
                                     final PreAndPostConditionsDescription postAndPrepPropDesc,
                                     final int margin, final ElectionSimulationData origResult,
                                     final ElectionSimulationData inputData) {
        // Create a code generator, that creates a code file for this call only
        // one time in this factory factory;
        CBMCCodeGenerator generator =
                new CBMCCodeGenerator(electionDesc,
                                      postAndPrepPropDesc, margin,
                                      origResult, inputData);
        ArrayList<String> code = generator.getCode();
        String absolutePath = SuperFolderFinder.getSuperFolder()
                                + PATH_TO_TEMP_FOLDER;
        File file = new File(new File(absolutePath),
                             FileLoader.getNewUniqueName(absolutePath)
                             + FileLoader.C_FILE_ENDING);
        if (file.getParentFile() == null) {
            ErrorLogger.log(CANNOT_FIND_PARENT);
        } else if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        FileSaver.writeStringLinesToFile(code, file);
        // FileSaver.writeStringLinesToFile(generator.getCode(), file);
        return file;
    }

    /**
     * Creates a new c-Code file that then can be used by all the underlying
     * checkers to check it with cbmc.
     *
     * @param electionDesc
     *            the source that describes the election
     * @param postAndPrepPropDesc
     *            the property that this specific process factory should check
     * @param inputData
     *            input data
     * @return a file that contains the generated code from the two above
     *         variables
     */
    public File createCodeFileTest(final ElectionDescription electionDesc,
                                   final PreAndPostConditionsDescription postAndPrepPropDesc,
                                   final ElectionSimulationData inputData) {
        // create a code generator, that creates a code file for this call only
        // one time in this factory factory;
        CBMCCodeGenerator generator =
                new CBMCCodeGenerator(electionDesc,
                                      postAndPrepPropDesc, inputData);
        ArrayList<String> code = generator.getCode();
        String absolutePath = SuperFolderFinder.getSuperFolder()
                                + PATH_TO_TEMP_FOLDER;
        File file = new File(new File(absolutePath),
                             FileLoader.getNewUniqueName(absolutePath)
                             + FileLoader.C_FILE_ENDING);
        if (file.getParentFile() == null) {
            ErrorLogger.log(CANNOT_FIND_PARENT);
        } else if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        FileSaver.writeStringLinesToFile(code, file);
        // FileSaver.writeStringLinesToFile(generator.getCode(), file);
        return file;
    }

    //
    // /**
    //  * Creates a new c-Code file that then can be used by all the underlying
    //  * checkers to check it with cbmc.
    //  *
    //  * @param electionDescSrc the source that describes the election
    //  * @param postAndPrepPropDesc the property that this specific process
    //  *                            factory should check
    //  * @return a file that contains the generated code from the two above
    //  *         variables
    //  */
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
    // String absolutePath = SuperFolderFinder.getSuperFolder() +
    // pathToTempFolder;
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
    //  * Creates a new c-Code file that then can be used by all the underlying
    //  * checkers to check it with cbmc.
    //  *
    //  * @param electionDescSrc the source that describes the election
    //  * @param postAndPrepPropDesc the property that this specific process
    //  *                            factory should check
    //  * @return a file that contains the generated code from the two above
    //  *         variables
    //  */
    // public File createCodeFileCheck(ElectionDescriptionSource
    // electionDescSrc,
    // PostAndPrePropertiesDescription postAndPrepPropDesc) {
    //
    // // Create a code generator, that creates a code file for this call only
    // // one time in this factory factory;
    // CBMCCodeGenerator generator = new
    // CBMCCodeGenerator(electionDescSrc.getElectionDescription(),
    // postAndPrepPropDesc, false);
    //
    // ArrayList<String> code = generator.getCode();
    //
    // String absolutePath = SuperFolderFinder.getSuperFolder() +
    // pathToTempFolder;
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
    //  * Creates a new c-Code file that then can be used by all the underlying
    //  * checkers to check it with cbmc.
    //  *
    //  * @param electionDescSrc the source that describes the election
    //  * @param postAndPrepPropDesc the property that this specific process
    //  *                            factory should check
    //  * @return a file that contains the generated code from the two above
    //  *         variables
    //  */
    // public File createCodeFileMargin(ElectionDescriptionSource
    // electionDescSrc,
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
    // String absolutePath = SuperFolderFinder.getSuperFolder() +
    // pathToTempFolder;
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
    //  * Creates a new c-Code file that then can be used by all the underlying
    //  * checkers to check it with cbmc.
    //  *
    //  * @param electionDescSrc the source that describes the election
    //  * @param postAndPrepPropDesc the property that this specific process
    //  *                            factory should check
    //  * @return a file that contains the generated code from the two above
    //  *         variables
    //  */
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
    // String absolutePath = SuperFolderFinder.getSuperFolder() +
    // pathToTempFolder;
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
        if (GUIController.getController().getDeleteTmpFiles() && toCheck != null
                && toCheck.exists()) {
            toCheck.delete();
        }
    }

    @Override
    protected Checker startProcessCheck(final ElectionDescription electionDesc,
                                        final PreAndPostConditionsDescription postAndPrepPropDesc,
                                        final String advanced, final int voters,
                                        final int candidates,
                                        final int seats, final CheckerFactory parent,
                                        final Result result) {
        String userOptions = advanced.trim().replaceAll(TO_REPLACE, WHITESPACE);
        // Remove all unnecessary white spaces.
        // Create the file in which the code is saved if it does not exist
        // already.
        if (toCheck == null) {
            // Create the file only once for each factory and reuse it then.
            toCheck = createCodeFileCheck(electionDesc, postAndPrepPropDesc);
        }
        Checker startedChecker = null;
        switch (os) {
        case Linux:
            startedChecker = new LinuxProcess(voters, candidates, seats,
                    userOptions, toCheck, parent, result);
            break;
        case Windows:
            startedChecker = new WindowsProcess(voters, candidates, seats,
                    userOptions, toCheck, parent, result);
            break;
        case Mac:
            ErrorForUserDisplayer.displayError(MAC_OS_NOT_SUPPORTED);
            break;
        default:
            ErrorLogger.log(OS_COULD_NOT_BE_DETERMINED);
        }
        return startedChecker;
    }

    @Override
    protected Checker startProcessMargin(final ElectionDescription electionDesc,
                                         final PreAndPostConditionsDescription postAndPrepPropDesc,
                                         final String advanced, final int voters,
                                         final int candidates,
                                         final int seats, final CheckerFactory parent,
                                         final int margin,
                                         final ElectionSimulationData origResult,
                                         final ElectionSimulationData votingData,
                                         final Result result) {
        String userOptions = advanced.trim().replaceAll(TO_REPLACE, WHITESPACE);
        // remove all unnecessary white spaces
        // create the file in which the code is saved
        toCheck = createCodeFileMargin(electionDesc, postAndPrepPropDesc,
                                       margin, origResult, votingData);
        Checker startedChecker = null;
        switch (os) {
        case Linux:
            startedChecker = new LinuxProcess(voters, candidates, seats,
                                              userOptions, toCheck, parent, result);
            break;
        case Windows:
            startedChecker = new WindowsProcess(voters, candidates, seats,
                                                userOptions, toCheck, parent, result);
            break;
        case Mac:
            ErrorForUserDisplayer.displayError(MAC_OS_NOT_SUPPORTED);
            break;
        default:
            ErrorLogger.log(OS_COULD_NOT_BE_DETERMINED);
        }
        return startedChecker;
    }

    @Override
    protected Checker startProcessTest(final ElectionDescription electionDesc,
                                       final PreAndPostConditionsDescription postAndPrepPropDesc,
                                       final String advanced, final int voters,
                                       final int candidates,
                                       final int seats, final CheckerFactory parent,
                                       final ElectionSimulationData votingData,
                                       final Result result) {
        final String userOptions = advanced.trim().replaceAll(TO_REPLACE, WHITESPACE);
        // remove all unnecessary white spaces
        // create the file in which the code is saved
        toCheck = createCodeFileTest(electionDesc, postAndPrepPropDesc,
                                     votingData);
        Checker startedChecker = null;
        switch (os) {
        case Linux:
            startedChecker = new LinuxProcess(voters, candidates, seats,
                                              userOptions, toCheck, parent, result);
            break;
        case Windows:
            startedChecker = new WindowsProcess(voters, candidates, seats,
                                                userOptions, toCheck, parent, result);
            break;
        case Mac:
            ErrorForUserDisplayer.displayError(MAC_OS_NOT_SUPPORTED);
            break;
        default:
            ErrorLogger.log(OS_COULD_NOT_BE_DETERMINED);
        }
        return startedChecker;
    }
}
