package edu.pse.beast.api.testrunner.threadpool;

public interface WorkSupplierASD {
    WorkUnit getWorkUnit();

    boolean isFinished();

    void waitSync() throws InterruptedException;

    void interruptAll();

    void interruptSpecific(String uuid);
}
