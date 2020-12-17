package edu.pse.beast.api.testrunner.threadpool;

public interface WorkUnit {
	public void doWork();
	public String getUUID();
	public void interrupt();
	boolean isFinished();
}
