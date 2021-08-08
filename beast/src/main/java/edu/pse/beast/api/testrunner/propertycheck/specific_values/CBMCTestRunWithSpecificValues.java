package edu.pse.beast.api.testrunner.propertycheck.specific_values;

import edu.pse.beast.api.CBMCTestCallback;
import edu.pse.beast.api.cbmc_run_with_specific_values.VotingParameters;
import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.loopbounds.CodeGenLoopBoundHandler;
import edu.pse.beast.api.descr.c_electiondescription.CElectionDescription;
import edu.pse.beast.api.descr.property_description.PreAndPostConditionsDescription;
import edu.pse.beast.api.paths.PathHandler;
import edu.pse.beast.api.testrunner.code_files.CBMCCodeFileData;
import edu.pse.beast.api.testrunner.propertycheck.CBMCPropertyCheckWorkUnit;
import edu.pse.beast.api.testrunner.propertycheck.jsonoutput.CBMCJsonMessage;
import edu.pse.beast.api.testrunner.propertycheck.jsonoutput.CBMCJsonRunningDataExtractor;

/**
 * This class can be expanded to implemented functionality to generate test runs
 * with specific parameters.
 *
 * @author Holger Klein
 */
public class CBMCTestRunWithSpecificValues implements CBMCTestCallback {
    private static final String FALSE_NAME = "false";
    private static final String FALSE_CODE = "FALSE;";

    private CBMCCodeFileData cbmcCodeFile;
    private VotingParameters parameters;

    private CBMCJsonRunningDataExtractor cbmcJsonRunningDataExtractor;
    private CBMCPropertyCheckWorkUnit workUnit;

    private CodeGenLoopBoundHandler loopBounds;
    private CodeGenOptions codeGenerationOptions;

    private int v;
    private int s;
    private int c;

    private CElectionDescription description;
    private PreAndPostConditionsDescription propertyDescription;

    public CBMCTestRunWithSpecificValues(final CBMCCodeFileData codeFile,
                                         final VotingParameters params,
                                         final CodeGenLoopBoundHandler loopBoundList,
                                         final CodeGenOptions codeGenOptions,
                                         final CElectionDescription descr) {
        this.cbmcCodeFile = codeFile;
        this.parameters = params;
        this.loopBounds = loopBoundList;
        this.codeGenerationOptions = codeGenOptions;
        this.description = descr;
        v = params.getV();
        c = params.getC();
        s = params.getS();
        propertyDescription = new PreAndPostConditionsDescription(FALSE_NAME);
        propertyDescription.getPreConditionsDescription().setCode();
        propertyDescription.getPostConditionsDescription().setCode(FALSE_CODE);

        cbmcJsonRunningDataExtractor =
                new CBMCJsonRunningDataExtractor(descr,
                                                 propertyDescription, s, c, v,
                                                 codeFile.getCodeInfo());
    }

    public final VotingParameters getParameters() {
        return this.parameters;
    }

    public final CBMCPropertyCheckWorkUnit getWorkUnit() {
        return this.workUnit;
    }

    public final void setAndInitializeWorkUnit(final CBMCPropertyCheckWorkUnit cbmcWorkUnit,
                                               final PathHandler pathHandler) {
        if (cbmcWorkUnit.getProcessStarterSource() == null) {
            return;
        }
        final BoundValues bounds = new BoundValues(c, s, v);
        final CBMCPropertyCheckWorkUnit.ElectionAndProperty elecAndProp =
                new CBMCPropertyCheckWorkUnit.ElectionAndProperty(description, propertyDescription);
        cbmcWorkUnit.initialize(bounds, codeGenerationOptions, loopBounds,
                                cbmcCodeFile, elecAndProp, this, pathHandler);
        this.workUnit = cbmcWorkUnit;
    }

    @Override
    public final void onPropertyTestRawOutput(final String sessionUUID,
                                              final CElectionDescription descr,
                                              final PreAndPostConditionsDescription propertyDescr,
                                              final BoundValues bounds,
                                              final String uuid,
                                              final String output) {
        final CBMCJsonMessage msg = cbmcJsonRunningDataExtractor.appendOutput(output);
        System.out.println(msg);
    }

}
