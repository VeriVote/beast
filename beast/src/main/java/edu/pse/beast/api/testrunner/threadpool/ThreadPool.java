package edu.pse.beast.api.testrunner.threadpool;

import java.util.ArrayList;

public class ThreadPool {
	private ArrayList<Thread> threads = new ArrayList<>();
	private ArrayList<ThreadPoolRunner> runners = new ArrayList<>();
	private WorkSupplier currentSupplier;
	
	public ThreadPool(int numThreads) {
		for(int i = 0; i < numThreads; ++i) {
			ThreadPoolRunner runner = new ThreadPoolRunner("Runner_" + i);
			Thread t = new Thread(runner);
			t.start();
			runners.add(runner);
			threads.add(t);
		}
	}
	
	public void setWorkSupplier(WorkSupplier supplier) {
		currentSupplier = supplier;
		runners.forEach(r -> r.setWorkSupplier(currentSupplier));
	}
	
	public void waitForCurrentWorkSupplierSync() throws InterruptedException {
		currentSupplier.waitSync();
	}
	
}
