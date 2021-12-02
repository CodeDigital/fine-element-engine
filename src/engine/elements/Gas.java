package engine.elements;

import engine.containers.Cell;

public abstract class Gas extends Fluid{

    public Gas(String TYPE) {
        super(ElementData.MATTER_GAS, TYPE);
    }

    @Override
    public void stepFSS(double dt) {

    }
}
