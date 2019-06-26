package edu.pse.beast.toolbox;

/**
 * A simple generic tuple
 * @param <FIRST>
 * @param <SECOND>
 *
 * @author Holger Klein
 */
public class Tuple<FIRST, SECOND> {
    public final FIRST first;
    public final SECOND second;

    public Tuple(FIRST x, SECOND y) {
        this.first = x;
        this.second = y;
    }
}