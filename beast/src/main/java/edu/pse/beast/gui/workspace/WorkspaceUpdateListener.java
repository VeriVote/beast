package edu.pse.beast.gui.workspace;

import java.util.List;

import edu.pse.beast.api.codegen.cbmc.SymbolicCBMCVar;
import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.electiondescription.function.CElectionDescriptionFunction;
import edu.pse.beast.api.electiondescription.function.SimpleTypeFunction;
import edu.pse.beast.api.electiondescription.function.VotingSigFunction;
import edu.pse.beast.api.testrunner.propertycheck.CBMCTestRun;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.gui.testconfigeditor.testconfig.TestConfiguration;
import edu.pse.beast.gui.testconfigeditor.testconfig.cbmc.CBMCTestConfiguration;

public interface WorkspaceUpdateListener {
	public default void handleWorkspaceUpdateGeneric() {

	}

	public default void handleWorkspaceUpdateAddedCBMCRuns(
			CBMCTestConfiguration config,
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

	public default void handleExtractedFunctionLoops(CElectionDescription descr,
			CElectionDescriptionFunction func) {

	}

	public default void handleAddedPropDescr(
			PreAndPostConditionsDescription propDescr) {

	}

	public default void handleAddedTestConfig(TestConfiguration tc) {

	}

	public default void handlePropDescrChangedCode(
			PreAndPostConditionsDescription propDescr) {

	}

	public default void handlePropDescrRemovedVar(
			PreAndPostConditionsDescription propDescr,
			SymbolicCBMCVar selectedVar) {

	}

	public default void handleDescrChangeAddedSimpleFunction(
			CElectionDescription descr, SimpleTypeFunction f) {

	}

	public default void handleDescrChangeInOutName(CElectionDescription descr) {
		
	}

	public default void handleCBMCRunDeleted(CBMCTestRun run) {
		
	}

}
