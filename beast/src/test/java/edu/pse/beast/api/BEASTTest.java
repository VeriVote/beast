package edu.pse.beast.api;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.Test;

import edu.pse.beast.api.testrunner.propertycheck.CBMCProcessStarterWindows;
import edu.pse.beast.api.testrunner.propertycheck.CBMCXmlOutputHandler;
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
		ElectionCheckParameter params = CreationHelper.createParams(2, 2, 2);

		CBMCXmlOutputHandler xmlOutputHandler = new CBMCXmlOutputHandler();
		BEASTPropertyCheckSession testSess = b.startPropertyCheck(new BEASTCallback() {
			@Override
			public void onPropertyTestRawOutputComplete(ElectionDescription description,
					PreAndPostConditionsDescription propertyDescr, int s, int c, int v, String uuid,
					List<String> cbmcOutput) {
				System.out.println("finished for s = " + s + " c = " + c + " v = " + v);
				xmlOutputHandler.onCompleteOutput(description, propertyDescr, s, c, v, uuid, cbmcOutput);
			}
		}, descr, propDescr, params);
		testSess.await();
		assertEquals(8, xmlOutputHandler.getResults().size());
		xmlOutputHandler.getResults().forEach(res -> {
			assertFalse(res.isSuccess());
		});
	}

}
