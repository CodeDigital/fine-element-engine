package engine.elements;

import engine.containers.Cell;

public abstract class Solid extends Element{

    public Solid(String TYPE, Cell cell) {
        super(ElementData.MATTER_SOLID, TYPE, cell);
    }

}
