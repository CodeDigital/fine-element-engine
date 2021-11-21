package engine.elements;

import engine.containers.Cell;

public abstract class Gas extends Fluid{

    public Gas(String TYPE, Cell cell) {
        super(ElementData.MATTER_GAS, TYPE, cell);
    }

}
