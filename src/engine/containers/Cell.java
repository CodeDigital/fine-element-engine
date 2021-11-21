package engine.containers;

import engine.Graphics;
import engine.Renderable;
import engine.Steppable;
import engine.elements.Element;
import engine.elements.ElementData;
import engine.math.MAT22;
import engine.math.V2D;

public class Cell implements Renderable, Steppable {

    public static final int BLACK = Graphics.G().color(0);

    // Associations
    private final Chunk chunk;
    private Element element;
    private V2D location;
    private MAT22 direction = MAT22.CARDINALS[2];

    private boolean updated = false;

    public Cell(Chunk chunk, V2D location) {
        this.chunk = chunk;
        this.location = location;
    }

    @Override
    public void stepPre(double dt) {
        updated = false;
        if(element == null) element.stepPre(dt);
    }

    @Override
    public void stepPhysics(double dt) {
        if(element == null) element.stepPhysics(dt);
    }

    @Override
    public void stepFSS(double dt) {
        if(element == null) element.stepFSS(dt);
    }

    @Override
    public void stepPost(double dt) {
        if(element == null) element.stepPost(dt);
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
        Cell cellWith = chunk.getCell(with);
        swap(cellWith);
    }

    public boolean canSwap(Cell with){
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
        Cell cellWith = chunk.getCell(with);
        return canSwap(cellWith);
    }

    @Override
    public void render() {

        if(updated){
            if(element == null){
                chunk.setPixel(location, BLACK);
            }else{
                chunk.setPixel(location, element.getColour());
            }
        }

    }

    public Chunk getChunk() {
        return chunk;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public V2D getLocation() {
        return location;
    }

    public MAT22 getDirection() {
        return direction;
    }

    public boolean isUpdated() {
        return updated;
    }

    public void setUpdated(boolean updated) {
        this.updated = updated;
    }
}
