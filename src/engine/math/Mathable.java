package engine.math;

public interface Mathable<T> {
    T add(T rhs);
    T subtract(T rhs);
    T multiply(double scalar);
    T round();
    T negative();
    boolean equal(T rhs);
}
