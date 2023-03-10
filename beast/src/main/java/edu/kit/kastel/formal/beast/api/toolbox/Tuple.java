package edu.kit.kastel.formal.beast.api.toolbox;

/**
 * A simple generic tuple.
 *
 * @author Holger Klein
 *
 * @param <F> the generic type
 * @param <S> the generic type
 */
public class Tuple<F, S> {

    /** The first. */
    private final F first;

    /** The second. */
    private final S second;

    /**
     * The constructor.
     *
     * @param x  the x
     * @param y  the y
     */
    public Tuple(final F x, final S y) {
        this.first = x;
        this.second = y;
    }

    /**
     * First.
     *
     * @return the first
     */
    public F first() {
        return first;
    }

    /**
     * Second.
     *
     * @return the second
     */
    public S second() {
        return second;
    }
}
