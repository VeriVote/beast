package edu.pse.beast.api.runner.threadpool;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public enum WorkUnitState {
    NO_WORK_UNIT, CREATED, ON_QUEUE, RUNNING, STOPPED, FINISHED, INITIALIZED;
}