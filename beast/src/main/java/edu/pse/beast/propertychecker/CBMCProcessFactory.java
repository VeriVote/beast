package edu.pse.beast.propertychecker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.highlevel.ElectionDescriptionSource;
import edu.pse.beast.highlevel.ParameterSource;
import edu.pse.beast.highlevel.PostAndPrePropertiesDescriptionSource;
import edu.pse.beast.toolbox.ErrorLogger;

public class CBMCProcessFactory extends CheckerFactory {

    private final OperatingSystems OS;

    // this is the last line in the cbmc output, if the verification was
    // successful
    private final String successLine = "VERIFICATION SUCCESSFUL";

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
    protected Checker startProcess(File toCheck, String arguments, CheckerFactory parent) {
        Checker process = null;
        switch (OS) {
        case Linux:
            process = new LinuxProcess(arguments, toCheck, parent);
            break;
        case Windows:
            process = new WindowsProcess(arguments, toCheck, parent);
            break;
        default:
            System.err.println("Warning, your OS couldn't be determined.");
            return null;
        }

        return process;
    }

    @Override
    public boolean checkResult(List<String> toCheck) {
        return toCheck.get(toCheck.size() - 1).contains(successLine);
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
}
