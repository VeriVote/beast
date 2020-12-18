package edu.pse.beast.api;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import edu.pse.beast.api.testrunner.propertycheck.CBMCProcessStarterWindows;
import edu.pse.beast.api.testrunner.propertycheck.PropertyCheckWorkSupplier;
import edu.pse.beast.api.testrunner.propertycheck.PropertyTestResult;
import edu.pse.beast.datatypes.electioncheckparameter.ElectionCheckParameter;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;

class BEASTTest {

	@Test
	void testRunSimpleElection() throws InterruptedException {
		BEAST b = new BEAST(new CBMCProcessStarterWindows());

		ElectionDescription descr = CreationHelper.createSimpleDescription("return 0;\n");
		List<PreAndPostConditionsDescription> propDescr = CreationHelper.createSimpleCondList("ELECT1 != ELECT2", "",
				"ELECT1 != ELECT2;");
		ElectionCheckParameter params = CreationHelper.createParams(1, 2, 2);

		BEASTTestSession testSess = b.startTest(new BEASTCallback() {
			@Override
			public void onPropertyTestResult(ElectionDescription electionDescription,
					PreAndPostConditionsDescription preAndPostConditionsDescription, int v, int c, int s,
					PropertyTestResult result) {
				System.out.println(preAndPostConditionsDescription.getName() + " V=" + v + " C=" + c + " S=" + s + " "
						+ result.isSuccess());
				assertFalse(result.isSuccess());
			}
		}, descr, propDescr, params);
		
		testSess.await();
	}

}
