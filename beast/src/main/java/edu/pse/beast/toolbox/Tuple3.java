package edu.pse.beast.toolbox;

/**
 * The Class Tuple3.
 *
 * @author taken with small changes from Vitaly Zinchenko
 *         (https://stackoverflow.com/a/32484678)
 * @param <F>
 *            the generic type
 * @param <S>
 *            the generic type
 * @param <T>
 *            the generic type
 */
public class Tuple3<F, S, T> {

    /** The first. */
    private final F first;

    /** The second. */
    private final S second;

    /** The third. */
    private final T third;

    /**
     * The constructor.
     *
     * @param fst
     *            the fst
     * @param snd
     *            the snd
     * @param thrd
     *            the thrd
     */
    public Tuple3(final F fst, final S snd, final T thrd) {
        this.first = fst;
        this.second = snd;
        this.third = thrd;
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof Tuple3)) {
            return false;
        }
        Tuple3<?, ?, ?> p = (Tuple3<?, ?, ?>) o;
        return first().equals(p.first()) && second().equals(p.second())
                && third().equals(p.third());
    }

    /**
     * Equals.
     *
     * @param x
     *            the x
     * @param y
     *            the y
     * @return true, if successful
     */
    public static boolean equals(final Object x, final Object y) {
        return (x == null && y == null) || (x != null && x.equals(y));
    }

    @Override
    public int hashCode() {
        return (first() == null ? 0 : first().hashCode())
                ^ (second() == null ? 0 : second().hashCode())
                ^ (third() == null ? 0 : third().hashCode());
    }

    /**
     * Creates the.
     *
     * @param <F>
     *            the generic type
     * @param <S>
     *            the generic type
     * @param <T>
     *            the generic type
     * @param f
     *            the f
     * @param s
     *            the s
     * @param t
     *            the t
     * @return the tuple 3
     */
    public static <F, S, T> Tuple3<F, S, T>
            create(final F f, final S s, final T t) {
        return new Tuple3<F, S, T>(f, s, t);
    }

    /**
     * First.
     *
     * @return the f
     */
    public F first() {
        return first;
    }

    /**
     * Second.
     *
     * @return the s
     */
    public S second() {
        return second;
    }

    /**
     * Third.
     *
     * @return the t
     */
    public T third() {
        return third;
    }
}
