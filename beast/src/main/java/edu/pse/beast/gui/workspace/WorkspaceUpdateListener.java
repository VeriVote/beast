package edu.pse.beast.gui.workspace;

import java.util.List;

import edu.pse.beast.api.codegen.cbmc.SymbolicCBMCVar;
import edu.pse.beast.api.descr.c_electiondescription.CElectionDescription;
import edu.pse.beast.api.descr.c_electiondescription.function.CElectionDescriptionFunction;
import edu.pse.beast.api.descr.c_electiondescription.function.SimpleTypeFunction;
import edu.pse.beast.api.descr.c_electiondescription.function.VotingSigFunction;
import edu.pse.beast.api.descr.property_description.PreAndPostConditionsDescription;
import edu.pse.beast.api.testrunner.propertycheck.symbolic_vars.CBMCTestRunWithSymbolicVars;
import edu.pse.beast.gui.testconfigeditor.testconfig.TestConfiguration;
import edu.pse.beast.gui.testconfigeditor.testconfig.cbmc.CBMCTestConfiguration;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public interface WorkspaceUpdateListener {
    default void handleWorkspaceUpdateGeneric() {
    }

    default void handleWorkspaceUpdateAddedCBMCRuns(CBMCTestConfiguration config,
                                                    List<CBMCTestRunWithSymbolicVars>
                                                        createdTestRuns) {
    }

    default void handleWorkspaceErrorNoCBMCProcessStarter() {
    }

    default void handleWorkspaceUpdateAddedVarToPropDescr(PreAndPostConditionsDescription
                                                                currentPropDescr,
                                                          SymbolicCBMCVar var) {
    }

    default void handleDescrChangeAddedVotingSigFunction(CElectionDescription descr,
                                                         VotingSigFunction func) {
    }

    default void handleDescrChangeUpdatedFunctionCode(CElectionDescription descr,
                                                      CElectionDescriptionFunction function,
                                                      String code) {
    }

    default void handleDescrChangeRemovedFunction(CElectionDescription descr,
                                                  CElectionDescriptionFunction func) {
    }

    default void handleExtractedFunctionLoops(CElectionDescription descr,
                                              CElectionDescriptionFunction func) {
    }

    default void handleAddedPropDescr(PreAndPostConditionsDescription propDescr) {
    }

    default void handleAddedTestConfig(TestConfiguration tc) {
    }

    default void handlePropDescrChangedCode(PreAndPostConditionsDescription propDescr) {
    }

    default void handlePropDescrRemovedVar(PreAndPostConditionsDescription propDescr,
                                           SymbolicCBMCVar selectedVar) {
    }

    default void handleDescrChangeAddedSimpleFunction(CElectionDescription descr,
                                                      SimpleTypeFunction f) {
    }

    default void handleDescrChangeInOutName(CElectionDescription descr) {
    }

    default void handleCBMCRunDeleted(CBMCTestRunWithSymbolicVars run) {
    }

    default void handleTestConfigDeleted(TestConfiguration tc) {
    }

    default void handleCBMConfigUpdatedFiles(CBMCTestConfiguration currentConfig) {
    }

}
