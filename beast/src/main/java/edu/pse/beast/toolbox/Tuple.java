package edu.pse.beast.toolbox;

/**
 * A simple generic tuple.
 * @param <FIRST>
 * @param <SECOND>
 *
 * @author Holger Klein
 */
public class Tuple<FIRST, SECOND> {
    private final FIRST first;
    private final SECOND second;

    public Tuple(final FIRST x, final SECOND y) {
        this.first = x;
        this.second = y;
    }

    public FIRST first() {
        return first;
    }

    public SECOND second() {
        return second;
    }
}
