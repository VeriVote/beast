package edu.pse.beast.gui.workspace;

import java.util.List;

import edu.pse.beast.api.codegen.SymbolicCBMCVar;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.electiondescription.function.CElectionDescriptionFunction;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.gui.testruneditor.testconfig.cbmc.CBMCPropertyTestConfiguration;
import edu.pse.beast.gui.testruneditor.testconfig.cbmc.runs.CBMCTestRun;

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

	public default void handleCodeChangeInDescr(CElectionDescription currentDescr,
			CElectionDescriptionFunction function, String code) {
		
	}

}
