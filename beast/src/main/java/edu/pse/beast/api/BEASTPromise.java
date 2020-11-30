package edu.pse.beast.api;

public class BEASTPromise {
	private BEASTTestRunner beastTestRunner;
	private Thread t;

	public BEASTPromise(BEASTTestRunner beastTestRunner) {
		this.beastTestRunner = beastTestRunner;
		t = new Thread(beastTestRunner);
		t.start();
	}

	public BEASTPromise() {
	}
	
}
