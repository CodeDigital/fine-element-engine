package engine.containers;

import engine.Steppable;
import engine.elements.Element;
import engine.elements.ElementData;
import engine.math.MAT22;
import engine.math.V2D;

import java.util.ArrayList;
import java.util.HashSet;

public class Cell implements Steppable {

    private static double width = 1;

    // Associations
    public final Chunk CHUNK;
    public final V2D LOCATION;
    public final HashSet<Chunk> CHUNK_BORDERS = new HashSet<>();
    private Element element;

    // forces and gravity
    private MAT22 direction = MAT22.CARDINALS[0];
    private ArrayList<V2D> forces = new ArrayList<>();

    private boolean updated = false;

    public Cell(Chunk CHUNK, V2D LOCATION) {
        this.CHUNK = CHUNK;
        this.LOCATION = LOCATION;

        direction = CHUNK.WORLD.getGravity().getRotation();

        // get the bordering chunks
        for(V2D card:V2D.OCTALS){
            V2D loc = card.multiply(Chunk.WIDTH).add(this.LOCATION);
            Chunk c = CHUNK.WORLD.getChunk(loc);
            if(c != null) CHUNK_BORDERS.add(CHUNK.WORLD.getChunk(loc));
        }
        CHUNK_BORDERS.add(CHUNK);
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
    }

    public void swap(V2D with){
        Cell cellWith = CHUNK.getCell(with);
        swap(cellWith);
    }

    public boolean canSwap(Cell with){
        if(element != null){
            if(element.isStatic()) return false;
        }
        if(with == null) return false;
        if(with.isUpdated()) return false;
        if(with.element != null){
            if(with.element.isStatic()) return false;
            if(with.element.MATTER.equals(element.MATTER) &&
                    element.MATTER.equals(ElementData.MATTER_SOLID)) return false;
            if(with.element.getDensity() < element.getDensity()) return true;
        }
        return false;
    }

    public boolean canSwap(V2D with){
        Cell cellWith = CHUNK.getCell(with);
        return canSwap(cellWith);
    }

    public V2D getTotalForce(){
        V2D out = CHUNK.WORLD.getGravity();
        for(V2D f:forces){
            out = out.add(f);
        }
        return out;
    }

    public V2D applyDirection(V2D dir){
        return direction.multiply(dir).round();
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        System.out.println(element.TYPE);
        this.element = element;
        this.element.setCell(this);
        setUpdated(true);

        for(Chunk c:CHUNK_BORDERS){
            c.resetUpdated();
        }
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
