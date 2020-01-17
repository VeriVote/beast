package edu.pse.beast.toolbox;

/**
 * A simple generic tuple.
 *
 * @author Holger Klein
 * @param <FIRST>
 *            the generic type
 * @param <SECOND>
 *            the generic type
 */
public class Tuple<FIRST, SECOND> {

    /** The first. */
    private final FIRST first;

    /** The second. */
    private final SECOND second;

    /**
     * The constructor.
     *
     * @param x
     *            the x
     * @param y
     *            the y
     */
    public Tuple(final FIRST x, final SECOND y) {
        this.first = x;
        this.second = y;
    }

    /**
     * First.
     *
     * @return the first
     */
    public FIRST first() {
        return first;
    }

    /**
     * Second.
     *
     * @return the second
     */
    public SECOND second() {
        return second;
    }
}
