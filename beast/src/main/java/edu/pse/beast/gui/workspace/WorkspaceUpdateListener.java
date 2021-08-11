package edu.pse.beast.gui.workspace;

import java.util.List;

import edu.pse.beast.api.codegen.cbmc.SymbolicVariable;
import edu.pse.beast.api.method.CElectionDescription;
import edu.pse.beast.api.method.function.CElectionDescriptionFunction;
import edu.pse.beast.api.method.function.SimpleTypeFunction;
import edu.pse.beast.api.method.function.VotingSigFunction;
import edu.pse.beast.api.property.PreAndPostConditions;
import edu.pse.beast.api.runner.propertycheck.run.PropertyCheckRun;
import edu.pse.beast.gui.configurationeditor.configuration.ConfigurationBatch;
import edu.pse.beast.gui.configurationeditor.configuration.cbmc.Configuration;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public interface WorkspaceUpdateListener {
    default void handleWorkspaceUpdateGeneric() {
    }

    default void handleWorkspaceUpdateAddedRuns(Configuration config,
                                                List<PropertyCheckRun> createdRuns) {
    }

    default void handleWorkspaceErrorNoProcessStarter() {
    }

    default void handleWorkspaceUpdateAddedVarToPropDescr(PreAndPostConditions currentPropDescr,
                                                          SymbolicVariable var) {
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

    default void handleAddedPropDescr(PreAndPostConditions propDescr) {
    }

    default void handleAddedConfiguration(ConfigurationBatch tc) {
    }

    default void handlePropDescrChangedCode(PreAndPostConditions propDescr) {
    }

    default void handlePropDescrRemovedVar(PreAndPostConditions propDescr,
                                           SymbolicVariable selectedVar) {
    }

    default void handleDescrChangeAddedSimpleFunction(CElectionDescription descr,
                                                      SimpleTypeFunction f) {
    }

    default void handleDescrChangeInOutName(CElectionDescription descr) {
    }

    default void handleRunDeleted(PropertyCheckRun run) {
    }

    default void handleConfigurationDeleted(ConfigurationBatch tc) {
    }

    default void handleConfigurationUpdatedFiles(Configuration currentConfig) {
    }
}
