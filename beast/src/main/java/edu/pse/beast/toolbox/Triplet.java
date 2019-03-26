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
public class Triplet<F, S, T> {

    public final F first;
    public final S second;
    public final T third;

    public Triplet(F first, S second, T third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Triplet)) {
            return false;
        }
        Triplet<?, ?, ?> p = (Triplet<?, ?, ?>) o;
        return first.equals(p.first) && second.equals(p.second) && third.equals(p.third);
    }

    public static boolean equals(Object x, Object y) {
        return (x == null && y == null) || (x != null && x.equals(y));
    }

    @Override
    public int hashCode() {
        return (first == null ? 0 : first.hashCode()) ^ (second == null ? 0 : second.hashCode())
                ^ (third == null ? 0 : third.hashCode());
    }

    public static <F, S, T> Triplet<F, S, T> create(F f, S s, T t) {
        return new Triplet<F, S, T>(f, s, t);
    }
}