package engine.math;

/**
 * For mathematical classes.
 *
 * @param <T> The type of element with which the element is mathable
 */
public interface Mathable<T> {
    /**
     * Add the class and the rhs.
     *
     * @param rhs rhs of the operation
     * @return the new class
     */
    T add(T rhs);

    /**
     * Add a scalar to the class.
     *
     * @param rhs rhs of the operation
     * @return the new class
     */
    T add(double rhs);

    /**
     * Subtract the rhs from the class.
     *
     * @param rhs rhs of the operation
     * @return the new class
     */
    T subtract(T rhs);

    /**
     * Multiply the class by a scalar.
     *
     * @param scalar the scalar multiplier
     * @return the new class
     */
    T multiply(double scalar);

    /**
     * Round the class.
     *
     * @return the new class
     */
    T round();

    /**
     * A negative (or reversed) version of the class.
     *
     * @return the new class
     */
    T negative();

    /**
     * To compare rhs to the class.
     *
     * @param rhs rhs of the operation
     * @return whether they're equal
     */
    boolean equal(T rhs);
}
