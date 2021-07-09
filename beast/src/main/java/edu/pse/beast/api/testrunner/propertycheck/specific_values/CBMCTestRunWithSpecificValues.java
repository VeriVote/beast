package edu.pse.beast.api.testrunner.propertycheck.specific_values;

import edu.pse.beast.api.CBMCTestCallback;
import edu.pse.beast.api.cbmc_run_with_specific_values.VotingParameters;
import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.paths.PathHandler;
import edu.pse.beast.api.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.api.testrunner.code_files.CBMCCodeFileData;
import edu.pse.beast.api.testrunner.propertycheck.CBMCPropertyCheckWorkUnit;
import edu.pse.beast.api.testrunner.propertycheck.jsonoutput.CBMCJsonMessage;
import edu.pse.beast.api.testrunner.propertycheck.jsonoutput.CBMCJsonRunningDataExtractor;

/**
 * This class can be expanded to implemented functionality to generate testruns
 * With specific parameters
 */
public class CBMCTestRunWithSpecificValues implements CBMCTestCallback {
    private CBMCCodeFileData cbmcCodeFile;
    private VotingParameters parameters;

    private CBMCJsonRunningDataExtractor cbmcJsonRunningDataExtractor;
    private CBMCPropertyCheckWorkUnit workUnit;

    private String loopboundList;
    private CodeGenOptions codeGenOptions;

    private int V;
    private int S;
    private int C;

    private CElectionDescription descr;
    private PreAndPostConditionsDescription propDescr;

    public CBMCTestRunWithSpecificValues(CBMCCodeFileData cbmcCodeFile,
            VotingParameters parameters, String loopboundList,
            CodeGenOptions codeGenOptions, CElectionDescription descr) {
        this.cbmcCodeFile = cbmcCodeFile;
        this.parameters = parameters;
        this.loopboundList = loopboundList;
        this.codeGenOptions = codeGenOptions;
        this.descr = descr;
        V = parameters.getV();
        C = parameters.getC();
        S = parameters.getS();
        propDescr = new PreAndPostConditionsDescription("false");
        propDescr.getPreConditionsDescription().setCode("");
        propDescr.getPostConditionsDescription().setCode("FALSE;");

        cbmcJsonRunningDataExtractor = new CBMCJsonRunningDataExtractor(descr,
                propDescr, S, C, V, cbmcCodeFile.getCodeInfo());
    }

    public void setAndInitializeWorkUnit(CBMCPropertyCheckWorkUnit workUnit,
            PathHandler pathHandler) {
        if (workUnit.getProcessStarterSource() == null)
            return;
        workUnit.initialize(V, S, C, codeGenOptions, loopboundList,
                cbmcCodeFile, descr, propDescr, this, pathHandler);
        this.workUnit = workUnit;
    }

    @Override
    public void onPropertyTestRawOutput(String sessionUUID,
            CElectionDescription description,
            PreAndPostConditionsDescription propertyDescr, int s, int c, int v,
            String uuid, String output) {
        CBMCJsonMessage msg = cbmcJsonRunningDataExtractor.appendOutput(output);
        System.out.println(msg);
    }

}
