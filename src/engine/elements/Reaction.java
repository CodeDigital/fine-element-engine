package engine.elements;

public abstract class Reaction extends Element{

    public Reaction(String TYPE) {
        super(ElementData.MATTER_REACTION, TYPE);
    }

    @Override
    public void stepPost(double dt) {
        react(dt);
        super.stepPost(dt);
    }

    public abstract void react(double dt);
}
