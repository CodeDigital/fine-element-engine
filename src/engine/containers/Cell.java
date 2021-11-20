package engine.containers;

import engine.Renderable;
import engine.elements.Element;
import engine.math.V2D;

public class Cell implements Renderable {

    // Associations
    private final Chunk chunk;
    private Element element;

    private V2D location;

    public Cell(Chunk chunk, V2D location) {
        this.chunk = chunk;
        this.location = location;
    }

    public void swap(Cell with){
        Element prev = this.element;
        prev.setCell(with);
        this.element = with.getElement();
        this.element.setCell(this);
    }

    public Chunk getChunk() {
        return chunk;
    }

    public V2D getLocation() {
        return location;
    }

    public void setLocation(V2D location) {
        this.location = location;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element){
        this.element = element;
    }

    @Override
    public void render() {
        if(element == null){

        }
    }
}
