package edu.pse.beast.api.testrunner.threadpool;

public interface WorkUnit {
    void doWork();

    void addedToQueue();

    String getUUID();

    void interrupt();

    WorkUnitState getState();
}
