
package edu.pse.beast.highlevel;

/**
 * The CheckListener starts/stops checks.
 * @author Jonas
 */
public interface CheckListener {
    /**
     * Starts a check of the election for the properties given.
     */
    void startCheck();
    /**
     * Stops a running check.
     */
    void stopCheck();
}
