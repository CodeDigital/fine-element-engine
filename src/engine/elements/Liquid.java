package engine.elements;

import engine.containers.Cell;

public abstract class Liquid extends Fluid{

    public Liquid(String TYPE, Cell cell) {
        super(ElementData.MATTER_LIQUID, TYPE, cell);
    }
}
