package edu.pse.beast.toolbox;

/**
 *
 * @author taken with small changes from Vitaly Zinchenko
 *         (https://stackoverflow.com/a/32484678)
 *
 * @param <F>
 * @param <S>
 * @param <T>
 */
public class Tuple3<F, S, T> {

    private final F first;
    private final S second;
    private final T third;

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
        return first().equals(p.first())
                && second().equals(p.second())
                && third().equals(p.third());
    }

    public static boolean equals(final Object x, final Object y) {
        return (x == null && y == null) || (x != null && x.equals(y));
    }

    @Override
    public int hashCode() {
        return (first() == null ? 0 : first().hashCode())
                ^ (second() == null ? 0 : second().hashCode())
                ^ (third() == null ? 0 : third().hashCode());
    }

    public static <F, S, T> Tuple3<F, S, T> create(final F f, final S s, final T t) {
        return new Tuple3<F, S, T>(f, s, t);
    }

    public F first() {
        return first;
    }

    public S second() {
        return second;
    }

    public T third() {
        return third;
    }
}
