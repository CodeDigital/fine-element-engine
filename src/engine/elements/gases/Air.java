package engine.elements.gases;

import engine.Colour;
import engine.elements.ElementData;
import engine.elements.Gas;

public class Air extends Gas {

    public Air() {
        super(ElementData.ELEMENT_AIR);
        setMassData(ElementData.ELEMENT_AIR_DENSITY);

        colour = new Colour(210, 216, 228);
    }

    @Override
    public void stepPre(double dt) {
        checkFluidParents();
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
