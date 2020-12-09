package edu.pse.beast.api.testrunner;

import edu.pse.beast.api.BEASTCallback;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;

public abstract class BEASTResult implements Runnable {

	private ElectionDescription electionDescription;
	private PreAndPostConditionsDescription preAndPostConditionsDescription;
	private int V, C, S;
	private BEASTCallback cb;
	private Thread thread;

	protected boolean finished = false;
	protected boolean verificationSuccess = false;

	protected abstract void test();

	public BEASTResult(ElectionDescription electionDescription,
			PreAndPostConditionsDescription preAndPostConditionsDescription, int v, int c, int s, BEASTCallback cb) {
		this.electionDescription = electionDescription;
		this.preAndPostConditionsDescription = preAndPostConditionsDescription;
		V = v;
		C = c;
		S = s;
		this.cb = cb;
	}

	protected void startThread() {
		this.thread = new Thread(this);
		this.thread.start();
	}

	@Override
	public void run() {
		test();
		cb.onPropertyTestResult(electionDescription, preAndPostConditionsDescription, V, C, S, verificationSuccess);
		this.finished = true;
	}
	
	public boolean verificationFinished() {
		return finished;
	}
}
