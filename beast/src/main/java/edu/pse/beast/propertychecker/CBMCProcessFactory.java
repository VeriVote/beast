package edu.pse.beast.propertychecker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.highlevel.ElectionDescriptionSource;
import edu.pse.beast.highlevel.ParameterSource;
import edu.pse.beast.options.ParametereditorOptions.ParametereditorOptions;
import edu.pse.beast.toolbox.ErrorForUserDisplayer;
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
    private final String SUCCESSLINE = "VERIFICATION SUCCESSFUL";

    // this is the last line in the cbmc output, if the assertion
    // failed
    private final String FAILURELINE = "VERIFICATION FAILED";

    private final String pathToTempFolder = "/core/generated_c_files/";

    /**
     * creates a new CBMC checker factory, that determines what operating system
     * you
     * 
     * @param controller
     *            the controller that controls this processfactory and that has
     *            to be reported to, if all the checking for this file has
     *            finished
     * @param electionDescSrc
     *            the source that describes the election
     * @param postAndPrepPropDesc
     *            the source that describes the specific property
     * @param paramSrc
     *            the source that describes all other parameters
     * @param result
     *            the result object that the end result should be written to
     */
    protected CBMCProcessFactory(FactoryController controller, ElectionDescriptionSource electionDescSrc,PreAndPostConditionsDescription postAndPrepPropDesc, ParameterSource paramSrc, Result result, boolean isMargin) {
        super(controller, electionDescSrc, postAndPrepPropDesc, paramSrc, result, isMargin);
        os = determineOS();
    }

    public CBMCProcessFactory(FactoryController controller, File toCheck, ParameterSource paramSrc, Result result, boolean isMargin) {
    	super(controller, paramSrc, result, isMargin);
        os = determineOS();
	}

//	@Override
//    protected Checker startProcessCheck(ElectionDescriptionSource electionDescSrc,
//            PostAndPrePropertiesDescription postAndPrepPropDesc, String advanced, int voters, int candidates,
//            int seats, CheckerFactory parent) {
//
//        String userOptions = advanced.trim().replaceAll(" +", " ");
//
//        // remove all unnecessary whitespaces
//
//        // create the file in which the code is saved if it doesn't exist
//        // already
//        if (toCheck == null) {
//            // create the file only once for each factory and reuse it then
//            toCheck = createCodeFileCheck(electionDescSrc, postAndPrepPropDesc);
//        }
//
//        Checker startedChecker = null;
//
//        switch (os) {
//        case Linux:
//            startedChecker = new LinuxProcess(voters, candidates, seats, userOptions, toCheck, parent);
//            break;
//        case Windows:
//            startedChecker = new WindowsProcess(voters, candidates, seats, userOptions, toCheck, parent);
//            break;
//        case Mac:
//            ErrorForUserDisplayer.displayError(
//                    "MacOS is not supported yet, please implement the class CBMCProcess and add it then here in the "
//                            + "CBMCProcessFactory to be created");
//            break;
//        default:
//            ErrorLogger.log("Warning, your OS couldn't be determined or is not supported yet.");
//        }
//
//        return startedChecker;
//    }
//	
//	@Override
//	protected Checker startProcessMargin(ElectionDescriptionSource electionDescSrc,
//			PostAndPrePropertiesDescription postAndPrepPropDesc, String advanced, CheckerFactory parent) {
//        String userOptions = advanced.trim().replaceAll(" +", " ");
//
//        // remove all unnecessary whitespaces
//
//        // create the file in which the code is saved if it doesn't exist
//        // already
//        if (toCheck == null) {
//            // create the file only once for each factory and reuse it then
//            toCheck = createCodeFileMargin(electionDescSrc, postAndPrepPropDesc);
//        }
//
//        Checker startedChecker = null;
//
//        switch (os) {
//        case Linux:
//            startedChecker = new LinuxProcess(ElectionSimulation.getNumVoters(), ElectionSimulation.getNumCandidates(), ElectionSimulation.getNumSeats(), userOptions, this.toCheck, parent);
//            break;
//        case Windows:
//            startedChecker = new WindowsProcess(ElectionSimulation.getNumVoters(), ElectionSimulation.getNumCandidates(), ElectionSimulation.getNumSeats(), userOptions, this.toCheck, parent);
//            break;
//        case Mac:
//            ErrorForUserDisplayer.displayError(
//                    "MacOS is not supported yet, please implement the class CBMCProcess and add it then here in the "
//                            + "CBMCProcessFactory to be created");
//            break;
//        default:
//            ErrorLogger.log("Warning, your OS couldn't be determined or is not supported yet.");
//        }
//
//        return startedChecker;
//	}
//
//	
//	@Override
//	protected Checker startProcessTest(ElectionDescriptionSource electionDescSrc,
//			PostAndPrePropertiesDescription postAndPrepPropDesc, String advanced, CheckerFactory parent) {
//        String userOptions = advanced.trim().replaceAll(" +", " ");
//
//        // remove all unnecessary whitespaces
//
//        // create the file in which the code is saved if it doesn't exist
//        // already
//        if (toCheck == null) {
//            // create the file only once for each factory and reuse it then
//            toCheck = createCodeFileTest(electionDescSrc, postAndPrepPropDesc);
//        }
//
//        Checker startedChecker = null;
//
//        switch (os) {
//        case Linux:
//            startedChecker = new LinuxProcess(ElectionSimulation.getNumVoters(), ElectionSimulation.getNumCandidates(), ElectionSimulation.getNumSeats(), userOptions, this.toCheck, parent);
//            break;
//        case Windows:
//            startedChecker = new WindowsProcess(ElectionSimulation.getNumVoters(), ElectionSimulation.getNumCandidates(), ElectionSimulation.getNumSeats(), userOptions, this.toCheck, parent);
//            break;
//        case Mac:
//            ErrorForUserDisplayer.displayError(
//                    "MacOS is not supported yet, please implement the class CBMCProcess and add it then here in the "
//                            + "CBMCProcessFactory to be created");
//            break;
//        default:
//            ErrorLogger.log("Warning, your OS couldn't be determined or is not supported yet.");
//        }
//
//        return startedChecker;
//	}
//
//	@Override
//    protected Checker startProcess(ElectionDescriptionSource electionDescSrc,
//            PreAndPostConditionsDescription postAndPrepPropDesc, String advanced, int voters, int candidates,
//            int seats, CheckerFactory parent) {
//
//        String userOptions = advanced.trim().replaceAll(" +", " ");
//
//        // remove all unnecessary whitespaces
//
//        // create the file in which the code is saved if it doesn't exist
//        // already
//        if (toCheck == null) {
//            // create the file only once for each factory and reuse it then
//            toCheck = createCodeFile(electionDescSrc, postAndPrepPropDesc);
//        }
//
//        Checker startedChecker = null;
//
//        switch (os) {
//        case Linux:
//            startedChecker = new LinuxProcess(voters, candidates, seats, userOptions, toCheck, parent);
//            break;
//        case Windows:
//            startedChecker = new WindowsProcess(voters, candidates, seats, userOptions, toCheck, parent);
//            break;
//        case Mac:
//            ErrorForUserDisplayer.displayError(
//                    "MacOS is not supported yet, please implement the class CBMCProcess and add it then here in the "
//                            + "CBMCProcessFactory to be created");
//            break;
//        default:
//            ErrorLogger.log("Warning, your OS couldn't be determined or is not supported yet.");
//        }
//
//        return startedChecker;
//    }
//	
//	@Override
//	protected Checker startProcess(File toCheck, String advanced, int voters, int candidates, int seats,
//			CheckerFactory parent) {
//        String userOptions = advanced.trim().replaceAll(" +", " ");
//
//        // remove all unnecessary whitespaces
//
//        // create the file in which the code is saved if it doesn't exist
//        // already
//        if (this.toCheck == null) {
//            // create the file only once for each factory and reuse it then
//            this.toCheck = toCheck;
//        }
//
//        Checker startedChecker = null;
//
//        switch (os) {
//        case Linux:
//            startedChecker = new LinuxProcess(voters, candidates, seats, userOptions, this.toCheck, parent);
//            break;
//        case Windows:
//            startedChecker = new WindowsProcess(voters, candidates, seats, userOptions, this.toCheck, parent);
//            break;
//        case Mac:
//            ErrorForUserDisplayer.displayError(
//                    "MacOS is not supported yet, please implement the class CBMCProcess and add it then here in the "
//                            + "CBMCProcessFactory to be created");
//            break;
//        default:
//            ErrorLogger.log("Warning, your OS couldn't be determined or is not supported yet.");
//        }
//
//        return startedChecker;
//	}

    @Override
    public boolean checkAssertionSuccess(List<String> toCheck) {
        if (toCheck != null && toCheck.size() > 0) {
            return toCheck.get(toCheck.size() - 1).contains(SUCCESSLINE);
        } else {
            return false;
        }
    }

    @Override
    public boolean checkAssertionFailure(List<String> toCheck) {
        if (toCheck != null && toCheck.size() > 0) {
            return toCheck.get(toCheck.size() - 1).contains(FAILURELINE);
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
    		PreAndPostConditionsDescription postAndPrepPropDesc, ParameterSource paramSrc, Result result, boolean isMargin) {
        return new CBMCProcessFactory(controller, electionDescSrc, postAndPrepPropDesc, paramSrc, result, isMargin);
    }
    
    @Override
	public CheckerFactory getNewInstance(FactoryController controller, File toCheck, ParameterSource paramSrc,
			Result result, boolean isMargin) {
    	return new CBMCProcessFactory(controller, toCheck, paramSrc, result, isMargin);
	}

    @Override
    public List<Result> getMatchingResult(int amount) {
        List<Result> fittingResults = new ArrayList<Result>(amount);
        for (int i = 0; i < amount; i++) {
            fittingResults.add(new CBMCResult());
        }
        return fittingResults;
    }
    
    /**
     * creates a new c-Code file that then can be used by all the underlying
     * checkers to check it with cbmc
     * 
     * @param electionDescSrc the source that describes the election
     * @param postAndPrepPropDesc the property that this specific processfactory should check
     * @return a file that contains the generated code from the two above variables
     */

    public File createCodeFileCheck(ElectionDescriptionSource electionDescSrc,
    		PreAndPostConditionsDescription postAndPrepPropDesc, boolean  isMargin) {

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
    
    /**
     * creates a new c-Code file that then can be used by all the underlying
     * checkers to check it with cbmc
     * 
     * @param electionDescSrc the source that describes the election
     * @param postAndPrepPropDesc the property that this specific processfactory should check
     * @return a file that contains the generated code from the two above variables
     */
    public File createCodeFileMargin(ElectionDescriptionSource electionDescSrc,
            PreAndPostConditionsDescription postAndPrepPropDesc, int margin, List<Long> origResult, boolean  isTest) {

        // create a code generator, that creates a code file for this call only
        // one time in this factory factory;
        CBMCCodeGenerator generator = new CBMCCodeGenerator(electionDescSrc.getElectionDescription(),
                postAndPrepPropDesc, margin, origResult, isTest);

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
    
    
//
//    /**
//     * creates a new c-Code file that then can be used by all the underlying
//     * checkers to check it with cbmc
//     * 
//     * @param electionDescSrc the source that describes the election
//     * @param postAndPrepPropDesc the property that this specific processfactory should check
//     * @return a file that contains the generated code from the two above variables
//     */
//    public File createCodeFile(ElectionDescriptionSource electionDescSrc,
//            PostAndPrePropertiesDescription postAndPrepPropDesc) {
//
//        // create a code generator, that creates a code file for this call only
//        // one time in this factory factory;
//        CBMCCodeGenerator generator = new CBMCCodeGenerator(electionDescSrc.getElectionDescription(),
//                postAndPrepPropDesc, false);
//
//        ArrayList<String> code = generator.getCode();
//
//        String absolutePath = SuperFolderFinder.getSuperFolder() + pathToTempFolder;
//
//        File file = new File(new File(absolutePath), FileLoader.getNewUniqueName(absolutePath) + ".c");
//
//        if (file.getParentFile() == null) {
//            ErrorLogger.log("Can't find a parent to your file!");
//        } else if (!file.getParentFile().exists()) {
//            file.getParentFile().mkdirs();
//        }
//
//        FileSaver.writeStringLinesToFile(code, file);
//        // FileSaver.writeStringLinesToFile(generator.getCode(), file);
//        return file;
//    }
    
//  /**
//  * creates a new c-Code file that then can be used by all the underlying
//  * checkers to check it with cbmc
//  * 
//  * @param electionDescSrc the source that describes the election
//  * @param postAndPrepPropDesc the property that this specific processfactory should check
//  * @return a file that contains the generated code from the two above variables
//  */
// public File createCodeFileCheck(ElectionDescriptionSource electionDescSrc,
//         PostAndPrePropertiesDescription postAndPrepPropDesc) {
//
//     // create a code generator, that creates a code file for this call only
//     // one time in this factory factory;
//     CBMCCodeGenerator generator = new CBMCCodeGenerator(electionDescSrc.getElectionDescription(),
//             postAndPrepPropDesc, false);
//
//     ArrayList<String> code = generator.getCode();
//
//     String absolutePath = SuperFolderFinder.getSuperFolder() + pathToTempFolder;
//
//     File file = new File(new File(absolutePath), FileLoader.getNewUniqueName(absolutePath) + ".c");
//
//     if (file.getParentFile() == null) {
//         ErrorLogger.log("Can't find a parent to your file!");
//     } else if (!file.getParentFile().exists()) {
//         file.getParentFile().mkdirs();
//     }
//
//     FileSaver.writeStringLinesToFile(code, file);
//     // FileSaver.writeStringLinesToFile(generator.getCode(), file);
//     return file;
// }
//    
//    /**
//     * creates a new c-Code file that then can be used by all the underlying
//     * checkers to check it with cbmc
//     * 
//     * @param electionDescSrc the source that describes the election
//     * @param postAndPrepPropDesc the property that this specific processfactory should check
//     * @return a file that contains the generated code from the two above variables
//     */
//    public File createCodeFileMargin(ElectionDescriptionSource electionDescSrc,
//            PostAndPrePropertiesDescription postAndPrepPropDesc) {
//
//        // create a code generator, that creates a code file for this call only
//        // one time in this factory factory;
//        CBMCCodeGenerator generator = new CBMCCodeGenerator(electionDescSrc.getElectionDescription(),
//                postAndPrepPropDesc, true);
//
//        ArrayList<String> code = generator.getCode();
//
//        String absolutePath = SuperFolderFinder.getSuperFolder() + pathToTempFolder;
//
//        File file = new File(new File(absolutePath), FileLoader.getNewUniqueName(absolutePath) + ".c");
//
//        if (file.getParentFile() == null) {
//            ErrorLogger.log("Can't find a parent to your file!");
//        } else if (!file.getParentFile().exists()) {
//            file.getParentFile().mkdirs();
//        }
//
//        FileSaver.writeStringLinesToFile(code, file);
//        // FileSaver.writeStringLinesToFile(generator.getCode(), file);
//        return file;
//    }
//    
//    /**
//     * creates a new c-Code file that then can be used by all the underlying
//     * checkers to check it with cbmc
//     * 
//     * @param electionDescSrc the source that describes the election
//     * @param postAndPrepPropDesc the property that this specific processfactory should check
//     * @return a file that contains the generated code from the two above variables
//     */
//    public File createCodeFileTest(ElectionDescriptionSource electionDescSrc,
//            PostAndPrePropertiesDescription postAndPrepPropDesc) {
//
//        // create a code generator, that creates a code file for this call only
//        // one time in this factory factory;
//        CBMCCodeGenerator generator = new CBMCCodeGenerator(electionDescSrc.getElectionDescription(),
//                postAndPrepPropDesc, true);
//
//        ArrayList<String> code = generator.getCode();
//
//        String absolutePath = SuperFolderFinder.getSuperFolder() + pathToTempFolder;
//
//        File file = new File(new File(absolutePath), FileLoader.getNewUniqueName(absolutePath) + ".c");
//
//        if (file.getParentFile() == null) {
//            ErrorLogger.log("Can't find a parent to your file!");
//        } else if (!file.getParentFile().exists()) {
//            file.getParentFile().mkdirs();
//        }
//
//        FileSaver.writeStringLinesToFile(code, file);
//        // FileSaver.writeStringLinesToFile(generator.getCode(), file);
//        return file;
//    }

    @Override
    protected void cleanUp() {
        if (ParametereditorOptions.deleteTmpFiles() && toCheck != null && toCheck.exists()) {
            toCheck.delete();
        }
    }
    
    @Override
    protected Checker startProcessCheck(ElectionDescriptionSource electionDescSrc,
          PreAndPostConditionsDescription postAndPrepPropDesc, String advanced, int voters, int candidates,
          int seats, CheckerFactory parent, boolean isMargin) {

      String userOptions = advanced.trim().replaceAll(" +", " ");

      // remove all unnecessary whitespaces

      // create the file in which the code is saved if it doesn't exist
      // already
      if (toCheck == null) {
          // create the file only once for each factory and reuse it then
          toCheck = createCodeFileCheck(electionDescSrc, postAndPrepPropDesc, isMargin);
      }

      Checker startedChecker = null;

      switch (os) {
      case Linux:
          startedChecker = new LinuxProcess(voters, candidates, seats, userOptions, toCheck, parent);
          break;
      case Windows:
          startedChecker = new WindowsProcess(voters, candidates, seats, userOptions, toCheck, parent);
          break;
      case Mac:
          ErrorForUserDisplayer.displayError(
                  "MacOS is not supported yet, please implement the class CBMCProcess and add it then here in the "
                          + "CBMCProcessFactory to be created");
          break;
      default:
          ErrorLogger.log("Warning, your OS couldn't be determined or is not supported yet.");
      }

      return startedChecker;
  }

	@Override
	protected Checker startProcessTest(ElectionDescriptionSource electionDescSrc,
			PreAndPostConditionsDescription postAndPrepPropDesc, String advanced, int voters, int candidates, int seats,
			CheckerFactory parent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Checker startProcessMargin(ElectionDescriptionSource electionDescSrc,
			PreAndPostConditionsDescription postAndPrepPropDesc, String advanced, int voters, int candidates, int seats,
			CheckerFactory parent, int margin, List<Long> origResult, boolean isTest) {
		String userOptions = advanced.trim().replaceAll(" +", " ");

	      // remove all unnecessary whitespaces

	      // create the file in which the code is saved
	      toCheck = createCodeFileMargin(electionDescSrc, postAndPrepPropDesc, margin, origResult, isTest);

	      Checker startedChecker = null;

	      switch (os) {
	      case Linux:
	          startedChecker = new LinuxProcess(voters, candidates, seats, userOptions, toCheck, parent);
	          break;
	      case Windows:
	          startedChecker = new WindowsProcess(voters, candidates, seats, userOptions, toCheck, parent);
	          break;
	      case Mac:
	          ErrorForUserDisplayer.displayError(
	                  "MacOS is not supported yet, please implement the class CBMCProcess and add it then here in the "
	                          + "CBMCProcessFactory to be created");
	          break;
	      default:
	          ErrorLogger.log("Warning, your OS couldn't be determined or is not supported yet.");
	      }

	      return startedChecker;
	}
}
