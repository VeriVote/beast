package edu.pse.beast.api;

import java.util.Iterator;
import java.util.List;

import edu.pse.beast.booleanexpeditor.booleanexpcodearea.errorfinder.BooleanExpEditorGeneralErrorFinder;
import edu.pse.beast.celectiondescriptioneditor.celectioncodearea.errorhandling.CVariableErrorFinder;
import edu.pse.beast.codearea.errorhandling.CodeError;
import edu.pse.beast.datatypes.electioncheckparameter.ElectionCheckParameter;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.highlevel.javafx.ParentTreeItem;

public class BEAST {

	public BEASTPromise startTest(BEASTCallback cb, ElectionDescription description,
			List<PreAndPostConditionsDescription> propertiesToTest, ElectionCheckParameter param) {

		// Test the election description for errors
		final List<CodeError> electionCodeErrors = CVariableErrorFinder.findErrors(description);
		if (!electionCodeErrors.isEmpty()) {
			for (CodeError err : electionCodeErrors) {
				cb.onElectionCodeError(err);
			}
			return new BEASTPromise();
		}

		// Test the Conditiondescriptions for errors
		int idx = 0;
		for (PreAndPostConditionsDescription prop : propertiesToTest) {
			List<CodeError> propCodeErrors = BooleanExpEditorGeneralErrorFinder.getErrors(prop);
			if (!propCodeErrors.isEmpty()) {
				for (CodeError err : propCodeErrors) {
					cb.onPropertyCodeError(err, idx);
				}
			}
			idx++;
			return new BEASTPromise();
		}

		// start the test (for now, all we can do is run a normal cbmc test)
		// TODO pass in which type of test...margin, etc. Currently this
		// is intertwined with the javafx ChildTreeItem specifically which
		// subclass gets passed. Wild
		return new BEASTPromise(new BEASTTestRunner(cb, description, propertiesToTest, param));
	}
}
