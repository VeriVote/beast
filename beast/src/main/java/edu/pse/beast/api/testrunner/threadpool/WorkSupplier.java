package edu.pse.beast.api.testrunner.threadpool;

public interface WorkSupplier {
	public WorkUnit getWorkUnit();
	public boolean isFinished();
	public void waitSync() throws InterruptedException;
}
