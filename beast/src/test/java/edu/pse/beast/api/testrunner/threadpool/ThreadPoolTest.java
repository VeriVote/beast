package edu.pse.beast.api.testrunner.threadpool;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;

import edu.pse.beast.api.BEASTCallback;
import edu.pse.beast.api.CreationHelper;
import edu.pse.beast.api.testrunner.propertycheck.CBMCProcessStarterWindows;
import edu.pse.beast.api.testrunner.propertycheck.CBMCXmlOutputHandler;
import edu.pse.beast.api.testrunner.propertycheck.PropertyCheckWorkSupplier;
import edu.pse.beast.api.testrunner.propertycheck.PropertyTestResult;
import edu.pse.beast.datatypes.electioncheckparameter.ElectionCheckParameter;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;

class ThreadPoolTest {
	@Test
	public void testPropertyWorkSupplierSuccess() throws InterruptedException {
		ThreadPool pool = new ThreadPool(4);
		ElectionDescription descr = CreationHelper.createSimpleDescription("return 0;\n");
		List<PreAndPostConditionsDescription> propDescr = CreationHelper.createSimpleCondList("ELECT1 == ELECT2", "",
				"ELECT1 == ELECT2;");
		ElectionCheckParameter params = CreationHelper.createParams(1, 2, 2);
		CBMCXmlOutputHandler xmlOutputHandler = new CBMCXmlOutputHandler();
		pool.setWorkSupplier(new PropertyCheckWorkSupplier(descr, propDescr, params, new BEASTCallback() {
			@Override
			public void onPropertyTestRawOutputComplete(ElectionDescription description,
					PreAndPostConditionsDescription propertyDescr, int s, int c, int v, String uuid,
					List<String> cbmcOutput) {
				System.out.println("finished for s = " + s + " c = " + c + " v = " + v);
				xmlOutputHandler.onCompleteOutput(description, propertyDescr, s, c, v, uuid, cbmcOutput);

			}

		}, new CBMCProcessStarterWindows()));
		pool.waitForCurrentWorkSupplierSync();
		pool.shutdown();
		assertEquals(4, xmlOutputHandler.getResults().size());
		xmlOutputHandler.getResults().forEach(res -> {
			assertTrue(res.isSuccess());
		});
	}

	@Test
	public void testPropertyWorkSupplierFailure() throws InterruptedException {
		ThreadPool pool = new ThreadPool(4);
		ElectionDescription descr = CreationHelper.createSimpleDescription("return 0;\n");
		List<PreAndPostConditionsDescription> propDescr = CreationHelper.createSimpleCondList("ELECT1 != ELECT2", "",
				"ELECT1 != ELECT2;");
		ElectionCheckParameter params = CreationHelper.createParams(1, 2, 2);
		CBMCXmlOutputHandler xmlOutputHandler = new CBMCXmlOutputHandler();
		pool.setWorkSupplier(new PropertyCheckWorkSupplier(descr, propDescr, params, new BEASTCallback() {
			@Override
			public void onPropertyTestRawOutputComplete(ElectionDescription description,
					PreAndPostConditionsDescription propertyDescr, int s, int c, int v, String uuid,
					List<String> cbmcOutput) {
				System.out.println("finished for s = " + s + " c = " + c + " v = " + v);
				xmlOutputHandler.onCompleteOutput(description, propertyDescr, s, c, v, uuid, cbmcOutput);

			}

		}, new CBMCProcessStarterWindows()));
		pool.waitForCurrentWorkSupplierSync();
		pool.shutdown();
		assertEquals(4, xmlOutputHandler.getResults().size());
		xmlOutputHandler.getResults().forEach(res -> {
			assertFalse(res.isSuccess());
		});
	}

}
