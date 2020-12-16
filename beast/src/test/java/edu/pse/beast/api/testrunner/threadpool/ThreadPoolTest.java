package edu.pse.beast.api.testrunner.threadpool;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;

import edu.pse.beast.api.BEASTCallback;
import edu.pse.beast.api.CreationHelper;
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
						//System.out.println("hello " + getUUID());						
					}

					@Override
					public String getUUID() {
						// TODO Auto-generated method stub
						return String.valueOf(i);
					}

					@Override
					public boolean isFinished() {
						// TODO Auto-generated method stub
						return true;
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
		});
	}
	
	@Test
	public void testPropertyWorkSupplier() throws InterruptedException {
		ThreadPool pool = new ThreadPool(8);
		ElectionDescription descr = CreationHelper.createSimpleDescription("return 0;\n");
		List<PreAndPostConditionsDescription> propDescr = CreationHelper.createSimpleCondList("", "ELECT1 == ELECT2;");
		ElectionCheckParameter params = CreationHelper.createParams(2, 2, 2);
		pool.setWorkSupplier(new PropertyCheckWorkSupplier(descr, propDescr, params, new BEASTCallback() {
			@Override
			public void onPropertyTestResult(ElectionDescription electionDescription,
					PreAndPostConditionsDescription preAndPostConditionsDescription, int v, int c, int s,
					PropertyTestResult result) {
				// TODO Auto-generated method stub
				System.out.println("result " + v + " " + c + " " + s + " " + result.isSuccess());
			}
		}));
		pool.waitForCurrentWorkSupplierSync();
		Thread.sleep(1000);
	}

}
