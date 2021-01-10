package edu.pse.beast.api;

import edu.pse.beast.api.testrunner.propertycheck.PropertyCheckWorkSupplier;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;

public class BEASTPropertyCheckSession {	
	
	private PropertyCheckWorkSupplier propertyCheckWorkSupplier;
	private ThreadPoolInterface tpi;
	
	public BEASTPropertyCheckSession() {
	}

	public BEASTPropertyCheckSession(PropertyCheckWorkSupplier propertyCheckWorkSupplier, ThreadPoolInterface tpi) {
		this.propertyCheckWorkSupplier = propertyCheckWorkSupplier;
		this.tpi = tpi;
		tpi.startOnceReady(propertyCheckWorkSupplier);
	}
	
	public void interruptAllTests() {
		propertyCheckWorkSupplier.interruptAll();
	}
	
	public void interruptSpecificTest(String uuid) {
		propertyCheckWorkSupplier.interruptSpecific(uuid);
	}
	
	/**
	 * blocks the calling thread until the beast test session is finished
	 * @throws InterruptedException
	 */
	public void await() throws InterruptedException {
		propertyCheckWorkSupplier.waitSync();
	}

}
