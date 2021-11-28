package engine.containers;

import engine.Debug;
import engine.Graphics;
import engine.Renderable;
import engine.Steppable;
import engine.elements.Element;
import engine.elements.ElementData;
import engine.math.MAT22;
import engine.math.V2D;
import engine.math.XMath;
import processing.core.PConstants;

public class Cell implements Steppable {

    private static double width = 1;

    // Associations
    public final Chunk CHUNK;
    public final V2D LOCATION;
    private Element element;
    private MAT22 direction = MAT22.CARDINALS[2];

    private boolean updated = false;

    public Cell(Chunk CHUNK, V2D LOCATION) {
        this.CHUNK = CHUNK;
        this.LOCATION = LOCATION;
    }

    @Override
    public void stepPre(double dt) {
        updated = false;
        if(element == null) return;
        element.stepPre(dt);
    }

    @Override
    public void stepPhysics(double dt) {
        if(!CHUNK.isActive()) return;
        if(element == null) return;
        if(updated) return;
        element.stepPhysics(dt);
    }

    @Override
    public void stepFSS(double dt) {
        if(!CHUNK.isActive()) return;
        if(element == null) return;
        if(updated) return;
        element.stepFSS(dt);
    }

    @Override
    public void stepPost(double dt) {
        if(element == null) return;
        element.stepPost(dt);
    }

    public void swap(Cell with){
        Element prev = this.element;
        Element next = with.getElement();
        with.setElement(prev);
        setElement(next);
        prev.setCell(with);
    }

    public void swap(V2D with){
        Cell cellWith = CHUNK.getCell(with);
        swap(cellWith);
    }

    public boolean canSwap(Cell with){
        if(with == null) return false;
        if(with.isUpdated()) return false;
        if(with.element != null){
            if(with.element.isStatic()) return false;
            if(with.element.MATTER == element.MATTER &&
                    element.MATTER == ElementData.MATTER_SOLID) return false;
            if(with.element.getDensity() < element.getDensity()) return true;
        }
        return true;
    }

    public boolean canSwap(V2D with){
        Cell cellWith = CHUNK.getCell(with);
        return canSwap(cellWith);
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        System.out.println(element.TYPE);
        this.element = element;
        this.element.setCell(this);
        setUpdated(true);
        CHUNK.setActive(true);
        CHUNK.resetUpdated();
    }

    public MAT22 getDirection() {
        return direction;
    }

    public void setDirection(MAT22 direction) {
        this.direction = direction;
    }

    public boolean isUpdated() {
        return updated;
    }

    public void setUpdated(boolean updated) {
        this.updated = updated;
    }

    public static double getWidth() {
        return width;
    }

    public static void setWidth(double width) {
        Cell.width = width;
    }
}
