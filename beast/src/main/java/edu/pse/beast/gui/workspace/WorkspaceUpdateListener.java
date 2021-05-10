package edu.pse.beast.gui.workspace;

import java.util.List;

import edu.pse.beast.api.codegen.cbmc.SymbolicCBMCVar;
import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.electiondescription.function.CElectionDescriptionFunction;
import edu.pse.beast.api.electiondescription.function.VotingSigFunction;
import edu.pse.beast.api.testrunner.propertycheck.CBMCTestRun;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.gui.testconfigeditor.testconfig.cbmc.CBMCPropertyTestConfiguration;

public interface WorkspaceUpdateListener {
	public default void handleWorkspaceUpdateGeneric() {

	}

	public default void handleWorkspaceUpdateAddedCBMCRuns(
			CBMCPropertyTestConfiguration config,
			List<CBMCTestRun> createdTestRuns) {
	}

	public default void handleWorkspaceErrorNoCBMCProcessStarter() {
	}

	public default void handleWorkspaceUpdateAddedVarToPropDescr(
			PreAndPostConditionsDescription currentPropDescr,
			SymbolicCBMCVar var) {

	}

	public default void handleDescrChangeAddedVotingSigFunction(
			CElectionDescription descr, VotingSigFunction func) {
	}

	public default void handleDescrChangeUpdatedFunctionCode(
			CElectionDescription descr, CElectionDescriptionFunction function,
			String code) {

	}

	public default void handleDescrChangeRemovedFunction(
			CElectionDescription descr, CElectionDescriptionFunction func) {

	}

	public default void handleDescrChangeAddedLoopBound(CElectionDescription descr,
			CElectionDescriptionFunction func, LoopBound lb) {
		
	}

	public default void handleDescrChangeRemovedLoopBound(CElectionDescription descr,
			CElectionDescriptionFunction func, LoopBound toRemove) {
		
	}

}
