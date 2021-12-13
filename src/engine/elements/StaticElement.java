package engine.elements;

// TODO: Commenting
public class StaticElement extends Element{

    public StaticElement(String MATTER, String TYPE) {
        super(MATTER, TYPE);
        setStatic(true);
    }

    @Override
    public void stepPhysics(double dt) {
        propagateTemperature(dt);
    }

    @Override
    public void stepFSS(double dt) {
    }

}
