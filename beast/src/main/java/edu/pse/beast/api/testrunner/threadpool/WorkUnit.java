package edu.pse.beast.api.testrunner.threadpool;

public interface WorkUnit {
	public void doWork();
	public String getUUID();
	boolean isFinished();
}
