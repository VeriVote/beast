package edu.kit.kastel.formal.beast.api.runner.threadpool;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public interface WorkSupplierASD {
    WorkUnit getWorkUnit();

    boolean isFinished();

    void waitSync() throws InterruptedException;

    void interruptAll();

    void interruptSpecific(String uuid);
}
