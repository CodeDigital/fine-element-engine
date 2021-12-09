package engine.elements;

// TODO: Commenting
public class StaticElement extends Element{

    public StaticElement(String MATTER, String TYPE) {
        super(MATTER, TYPE);
        isStatic = true;
    }

    @Override
    public void stepPre(double dt) {
    }

    @Override
    public void stepPhysics(double dt) {
    }

    @Override
    public void stepFSS(double dt) {
    }

    @Override
    public void stepPost(double dt) {
    }
}
