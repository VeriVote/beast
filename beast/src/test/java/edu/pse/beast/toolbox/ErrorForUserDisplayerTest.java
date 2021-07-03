package edu.pse.beast.toolbox;

import edu.pse.beast.zzz.toolbox.ErrorForUserDisplayer;

/**
 * The tests for ErrorForUserDisplayer.
 *
 * @author Niels Hanselmann
 */
public final class ErrorForUserDisplayerTest {
    private ErrorForUserDisplayerTest() { }

    /**
     * A small main class Test for the ErrorForUserDisplayer.
     *
     * @param args arguments
     */
    public static void main(final String[] args) {
        ErrorForUserDisplayer.displayError("hi");
        ErrorForUserDisplayer.displayError("hi again");
        ErrorForUserDisplayer.displayError("hi again2");
    }
}
