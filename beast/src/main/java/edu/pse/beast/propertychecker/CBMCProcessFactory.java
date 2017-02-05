package edu.pse.beast.propertychecker;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.highlevel.ElectionDescriptionSource;
import edu.pse.beast.highlevel.ParameterSource;
import edu.pse.beast.highlevel.PostAndPrePropertiesDescriptionSource;
import edu.pse.beast.toolbox.ErrorLogger;
import edu.pse.beast.toolbox.FileLoader;
import edu.pse.beast.toolbox.FileSaver;

public class CBMCProcessFactory extends CheckerFactory {

	private final OperatingSystems OS;

	private File toCheck = null;

	// this is the last line in the cbmc output, if the verification was
	// successful
	private final String successLine = "VERIFICATION SUCCESSFUL";
	
	// this is the last line in the cbmc output, if the assertion 
    // failed
	private final String failureLine = "VERIFICATION FAILED";

	private final String pathToTempFolder = "./src/main/resources/c_tempfiles/";

	protected CBMCProcessFactory(FactoryController controller, ElectionDescriptionSource electionDescSrc,
			PostAndPrePropertiesDescription postAndPrepPropDesc, ParameterSource paramSrc, Result result) {
		super(controller, electionDescSrc, postAndPrepPropDesc, paramSrc, result);
		OS = determineOS();
	}

	@Override
	protected Result createCounterExample(List<String> result) {
		// TODO
		return null;
	}

	@Override
	protected Checker startProcess(ElectionDescriptionSource electionDescSrc,
			PostAndPrePropertiesDescription postAndPrepPropDesc, String advanced, int voters, int candidates, int seats,
			CheckerFactory parent) {

	    //remove all unnecessary whitespaces
	    advanced = advanced.trim().replaceAll(" +", " ");
	    
		// create the file in which the code is saved if it doesn't exist already
		if (toCheck == null) {
			//create the file only once for one factory and reuse it then
			
			// ErrorLogger.log("(CBMCProcessFactory) to generate code again and not use the example unccoment line 52");
			toCheck = createCodeFile(electionDescSrc, postAndPrepPropDesc);
		}
		
		Checker startedChecker = null;
		
		switch (OS) {
		case Linux:
			startedChecker = new LinuxProcess(voters, candidates, seats, advanced, toCheck, parent);
			break;
		case Windows:
			startedChecker = new WindowsProcess(voters, candidates, seats, advanced, toCheck, parent);
			break;
		default:
			ErrorLogger.log("Warning, your OS couldn't be determined.");
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
		} else if (environment.toLowerCase().contains("windows") && OS == null) {
			determinedOS = OperatingSystems.Windows;
		} else if (environment.toLowerCase().contains("mac") && OS == null) {
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
			fittingResults.add(new CBMC_Result());
		}
		return fittingResults;
	}

	public File createCodeFile(ElectionDescriptionSource electionDescSrc,
			PostAndPrePropertiesDescription postAndPrepPropDesc) {

	    
	    CBMCCodeGenerator generator = new CBMCCodeGenerator(electionDescSrc.getElectionDescription(), postAndPrepPropDesc);
	    
	    
	    
	    ArrayList<String> code;
        code = generator.getCode();
        
		File file = new File(new File(pathToTempFolder), FileLoader.getNewUniqueName(pathToTempFolder) + ".c");

		FileSaver.writeStringLinesToFile(code, file);
		// FileSaver.writeStringLinesToFile(generator.getCode(), file);
		return file;
	}
}
