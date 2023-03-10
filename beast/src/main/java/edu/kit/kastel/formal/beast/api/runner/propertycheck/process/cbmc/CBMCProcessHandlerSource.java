package edu.kit.kastel.formal.beast.api.runner.propertycheck.process.cbmc;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public interface CBMCProcessHandlerSource {
    CBMCProcessHandler getProcessHandler();

    boolean hasProcessHandler();
}
