package edu.pse.beast.api;

import edu.pse.beast.api.testrunner.threadpool.ThreadPool;
import edu.pse.beast.api.testrunner.threadpool.WorkSupplier;

public class ThreadPoolInterface {
	private ThreadPool tp;

	public ThreadPoolInterface(ThreadPool tp) {
		this.tp = tp;
	}
	
	public void startOnceReady(WorkSupplier ws) {
		tp.setWorkSupplier(ws);
	}
	
}
