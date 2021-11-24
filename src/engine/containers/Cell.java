package engine.containers;

import engine.Graphics;
import engine.Renderable;
import engine.Steppable;
import engine.elements.Element;
import engine.elements.ElementData;
import engine.math.MAT22;
import engine.math.V2D;
import processing.core.PConstants;

public class Cell implements Renderable, Steppable {

    public static final double WIDTH = 5;

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
        if(element != null) element.stepPre(dt);
    }

    @Override
    public void stepPhysics(double dt) {
        if(element != null) element.stepPhysics(dt);
    }

    @Override
    public void stepFSS(double dt) {
        if(element != null) element.stepFSS(dt);
    }

    @Override
    public void stepPost(double dt) {
        if(element != null) element.stepPost(dt);
    }

    public void swap(Cell with){
        Element prev = this.element;
        prev.setCell(with);
        this.element = with.getElement();
        this.element.setCell(this);
        with.setUpdated(true);
        setUpdated(true);
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

    @Override
    public void render() {

        System.out.println("test");

        if(updated){
            if(element == null){
                CHUNK.setPixel(LOCATION, Graphics.G().color(255));
            }else{
                CHUNK.setPixel(LOCATION, element.getColour().get());
            }
        }

    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
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
}
