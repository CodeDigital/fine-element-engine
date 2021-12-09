package engine;

/**
 * When something is steppable in FSS.
 */
public interface Steppable {

    /**
     * The pre-step function
     *
     * @param dt time delta
     */
    void stepPre(double dt);

    /**
     * The physics step function
     *
     * @param dt time delta
     */
    void stepPhysics(double dt);

    /**
     * The Falling Sand Simulation (FSS) step function
     *
     * @param dt time delta
     */
    void stepFSS(double dt);

    /**
     * The post-step function.
     *
     * @param dt time delta
     */
    void stepPost(double dt);

}
