package edu.kit.kastel.formal.beast.api.runner.threadpool;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public interface WorkUnit {
    void doWork();

    void addedToQueue();

    String getUUID();

    void interrupt();

    WorkUnitState getState();
}
