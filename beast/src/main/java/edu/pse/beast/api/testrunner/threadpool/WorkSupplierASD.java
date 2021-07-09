package edu.pse.beast.api.testrunner.threadpool;

public interface WorkSupplierASD {
    public WorkUnit getWorkUnit();

    public boolean isFinished();

    public void waitSync() throws InterruptedException;

    public void interruptAll();

    public void interruptSpecific(String uuid);
}
