package edu.pse.beast.api.runner.propertycheck.testrun;

import edu.pse.beast.api.PropertyCheckCallback;
import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.loopbound.CodeGenLoopBoundHandler;
import edu.pse.beast.api.io.PathHandler;
import edu.pse.beast.api.method.CElectionDescription;
import edu.pse.beast.api.property.PropertyDescription;
import edu.pse.beast.api.runner.codefile.CodeFileData;
import edu.pse.beast.api.runner.propertycheck.PropertyCheckWorkUnit;
import edu.pse.beast.api.runner.propertycheck.output.JSONMessage;
import edu.pse.beast.api.runner.propertycheck.output.JSONRunningDataExtractor;
import edu.pse.beast.api.test.VotingParameters;

/**
 * This class can be expanded to implemented functionality to generate test runs
 * with specific parameters.
 *
 * @author Holger Klein
 */
public class CBMCTestRun implements PropertyCheckCallback {
    private static final String FALSE_NAME = "false";
    private static final String FALSE_CODE = "FALSE;";

    private CodeFileData cbmcCodeFile;
    private VotingParameters parameters;

    private JSONRunningDataExtractor cbmcJsonRunningDataExtractor;
    private PropertyCheckWorkUnit workUnit;

    private CodeGenLoopBoundHandler loopBounds;
    private CodeGenOptions codeGenerationOptions;

    private int v;
    private int s;
    private int c;

    private CElectionDescription description;
    private PropertyDescription propertyDescription;

    public CBMCTestRun(final CodeFileData codeFile,
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
        propertyDescription = new PropertyDescription(FALSE_NAME);
        propertyDescription.getPreConditionsDescription().setCode();
        propertyDescription.getPostConditionsDescription().setCode(FALSE_CODE);

        cbmcJsonRunningDataExtractor =
                new JSONRunningDataExtractor(descr,
                                                 propertyDescription, s, c, v,
                                                 codeFile.getCodeInfo());
    }

    public final VotingParameters getParameters() {
        return this.parameters;
    }

    public final PropertyCheckWorkUnit getWorkUnit() {
        return this.workUnit;
    }

    public final void setAndInitializeWorkUnit(final PropertyCheckWorkUnit cbmcWorkUnit,
                                               final PathHandler pathHandler) {
        if (cbmcWorkUnit.getProcessStarterSource() == null) {
            return;
        }
        final BoundValues bounds = new BoundValues(c, s, v);
        final PropertyCheckWorkUnit.ElectionAndProperty elecAndProp =
                new PropertyCheckWorkUnit.ElectionAndProperty(description, propertyDescription);
        cbmcWorkUnit.initialize(bounds, codeGenerationOptions, loopBounds,
                                cbmcCodeFile, elecAndProp, this, pathHandler);
        this.workUnit = cbmcWorkUnit;
    }

    @Override
    public final void onPropertyCheckRawOutput(final String sessionUUID,
                                              final CElectionDescription descr,
                                              final PropertyDescription propertyDescr,
                                              final BoundValues bounds,
                                              final String uuid,
                                              final String output) {
        final JSONMessage msg = cbmcJsonRunningDataExtractor.appendOutput(output);
        System.out.println(msg);
    }

}
