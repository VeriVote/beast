package edu.pse.beast.api;

import edu.pse.beast.api.testrunner.propertycheck.PropertyCheckWorkSupplier;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;

public class BEASTTestSession {	
	
	private PropertyCheckWorkSupplier propertyCheckWorkSupplier;
	
	public BEASTTestSession() {
	}

	public BEASTTestSession(PropertyCheckWorkSupplier propertyCheckWorkSupplier) {
		this.propertyCheckWorkSupplier = propertyCheckWorkSupplier;
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
