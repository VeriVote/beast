package edu.pse.beast.toolbox;

/**
 * A simple generic tuple
 *
 * @author Holger-Desktop
 */
public class Tuple<X, Y> {
    public final X x;
    public final Y y;

    public Tuple(X x, Y y) {
        this.x = x;
        this.y = y;
    }
}