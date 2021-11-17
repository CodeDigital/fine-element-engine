package engine;

public interface Steppable {

    void stepPre(double dt);
    void stepPhysics(double dt);
    void stepFSS(double dt);
    void stepPost(double dt);

}
