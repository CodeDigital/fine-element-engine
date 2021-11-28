package engine.elements;

import engine.Colour;

public class Air extends Gas{

    public Air() {
        super(ElementData.ELEMENT_AIR);
        setMassData(ElementData.ELEMENT_AIR_DENSITY);

        colour = new Colour(210, 216, 228);
    }

    @Override
    public void stepPhysics(double dt) {
        super.stepPhysics(dt);
    }

    @Override
    public void stepFSS(double dt) {
        super.stepFSS(dt);
    }
}
