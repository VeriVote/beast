package edu.pse.beast.highlevel;

/**
 * The ResultInterface provides the necessary methods to present the result of a
 * check.
 *
 * @author Jonas Wohnig
 */
public interface ResultInterface {

    /**
     * Ready to present.
     *
     * @return if the result is in a state where it can be presented
     */
    boolean readyToPresent();
}
