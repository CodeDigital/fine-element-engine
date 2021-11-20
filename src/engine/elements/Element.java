package engine.elements;

import engine.Steppable;
import engine.containers.Cell;
import engine.math.V2D;

public abstract class Element implements Steppable {

    public final String matter, type;

    private Cell cell;

    private V2D acceleration = V2D.ZERO;
    private V2D velocity = V2D.ZERO;

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

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }
}
