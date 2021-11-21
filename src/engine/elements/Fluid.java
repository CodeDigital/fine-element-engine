package engine.elements;

import engine.containers.Cell;

public abstract class Fluid extends Element{

    public Fluid(String MATTER, String TYPE, Cell cell) {
        super(MATTER, TYPE, cell);
    }
}
