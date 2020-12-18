package edu.pse.beast.api.testrunner.threadpool;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;

import edu.pse.beast.api.BEASTCallback;
import edu.pse.beast.api.CreationHelper;
import edu.pse.beast.api.testrunner.propertycheck.CBMCProcessStarterWindows;
import edu.pse.beast.api.testrunner.propertycheck.PropertyCheckWorkSupplier;
import edu.pse.beast.api.testrunner.propertycheck.PropertyTestResult;
import edu.pse.beast.datatypes.electioncheckparameter.ElectionCheckParameter;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;

class ThreadPoolTest {

	@Test
	void testTP() throws InterruptedException {
		ThreadPool pool = new ThreadPool(8);
		pool.setWorkSupplier(new WorkSupplier() {
			int i = 0;

			@Override
			public synchronized WorkUnit getWorkUnit() {
				// TODO Auto-generated method stub
				++i;
				return new WorkUnit() {

					@Override
					public void doWork() {
						// System.out.println("hello " + getUUID());
					}

					@Override
					public String getUUID() {
						// TODO Auto-generated method stub
						return String.valueOf(i);
					}

					@Override
					public boolean isFinished() {
						return true;
					}

					@Override
					public void interrupt() {						
					}
				};
			}

			@Override
			public boolean isFinished() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void waitSync() throws InterruptedException {

			}

			@Override
			public void interruptAll() {				
			}

			@Override
			public void interruptSpecific(String uuid) {				
			}
		});
	}

	@Test
	public void testPropertyWorkSupplierSuccess() throws InterruptedException {
		ThreadPool pool = new ThreadPool(4);
		ElectionDescription descr = CreationHelper.createSimpleDescription("return 0;\n");
		List<PreAndPostConditionsDescription> propDescr = CreationHelper.createSimpleCondList("ELECT1 == ELECT2", "",
				"ELECT1 == ELECT2;");
		ElectionCheckParameter params = CreationHelper.createParams(1, 2, 2);
		pool.setWorkSupplier(new PropertyCheckWorkSupplier(descr, propDescr, params, new BEASTCallback() {
			@Override
			public void onPropertyTestResult(ElectionDescription electionDescription,
					PreAndPostConditionsDescription preAndPostConditionsDescription, int v, int c, int s,
					PropertyTestResult result) {
				System.out.println(preAndPostConditionsDescription.getName() + " V=" + v + " C=" + c + " S=" + s + " "
						+ result.isSuccess());
				assertTrue(result.isSuccess());
			}
			
			@Override
			public void onPropertyTestRawOutput(ElectionDescription description,
					PreAndPostConditionsDescription propertyDescr, int s, int c, int v, String uuid, String output) {
				// TODO Auto-generated method stub
				//System.out.println(output);
			}
		}, new CBMCProcessStarterWindows()));
		pool.waitForCurrentWorkSupplierSync();
		pool.shutdown();
	}
	
	@Test
	public void testPropertyWorkSupplierFailure() throws InterruptedException {
		ThreadPool pool = new ThreadPool(4);
		ElectionDescription descr = CreationHelper.createSimpleDescription("return 0;\n");
		List<PreAndPostConditionsDescription> propDescr = CreationHelper.createSimpleCondList("ELECT1 != ELECT2", "",
				"ELECT1 != ELECT2;");
		ElectionCheckParameter params = CreationHelper.createParams(1, 2, 2);
		pool.setWorkSupplier(new PropertyCheckWorkSupplier(descr, propDescr, params, new BEASTCallback() {
			@Override
			public void onPropertyTestResult(ElectionDescription electionDescription,
					PreAndPostConditionsDescription preAndPostConditionsDescription, int v, int c, int s,
					PropertyTestResult result) {
				System.out.println(preAndPostConditionsDescription.getName() + " V=" + v + " C=" + c + " S=" + s + " "
						+ result.isSuccess());
				assertFalse(result.isSuccess());
			}
		}, new CBMCProcessStarterWindows()));
		pool.waitForCurrentWorkSupplierSync();
		pool.shutdown();
	}

}
