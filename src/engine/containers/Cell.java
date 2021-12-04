package engine.containers;

import engine.Steppable;
import engine.elements.Element;
import engine.elements.ElementData;
import engine.math.MAT22;
import engine.math.V2D;
import engine.math.XMath;

import java.util.ArrayList;
import java.util.HashSet;

public class Cell implements Steppable {

    private static double width = 1;

    // Associations
    public final Chunk CHUNK;
    public final V2D LOCATION;
    public final HashSet<Chunk> CHUNK_BORDERS = new HashSet<>();
    public final ArrayList<Cell> CELL_BORDERS = new ArrayList<>();
    private Element element;

    // forces and gravity
    private MAT22 direction;
    private ArrayList<V2D> forces = new ArrayList<>();
    private double pressure = ElementData.REST_PRESSURE + XMath.random(-1000, 1000);
    private double newPressure = pressure;

    private boolean updated = false;

    public Cell(Chunk CHUNK, V2D LOCATION) {
        this.CHUNK = CHUNK;
        this.LOCATION = LOCATION;

        direction = CHUNK.WORLD.getGravity().getRotation();
    }

    public void initialize(){
        // get the bordering chunks
        for(V2D oct:V2D.OCTALS){
            V2D loc = oct.multiply(Chunk.WIDTH/2).add(LOCATION);
            Chunk ch = CHUNK.WORLD.getChunk(loc);
            if(ch != null) CHUNK_BORDERS.add(ch);

            loc = oct.add(LOCATION);
            Cell ce = CHUNK.getCell(loc);
            CELL_BORDERS.add(ce);
        }
        CHUNK_BORDERS.add(CHUNK);
    }

    @Override
    public void stepPre(double dt) {
        updated = false;
        if(element == null) return;
        updatePressure(dt);
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
        pressure = newPressure;
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
        if(with == null) return false;
        if(with.isUpdated()) return false;
        if(with.getElement() != null){
            if(with.getElement().isStatic()) return false;
            if(with.getElement().MATTER == element.MATTER &&
                    element.MATTER == ElementData.MATTER_SOLID) return false;
            if(with.getElement().getDensity() < element.getDensity()) return true;
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
        this.element = element;
        this.element.setCell(this);
        setUpdated(true);

        for(Chunk c:CHUNK_BORDERS){
            c.resetUpdated();
        }
        CHUNK.resetUpdated();
    }

    public void updatePressure(double dt){
        for(int i = 0; i < 8; i++){
            Cell c = CELL_BORDERS.get(i);
            if(c == null) continue;
            double dp = pressure - c.getPressure();
            double flow = element.getPressureFlow(c) * dp;
            flow = XMath.clamp(flow, pressure / 8, -c.getPressure() / 8);
            addPressure(-dt * flow);
            c.addPressure(dt * flow);
        }
    }

    public void addPressure(double amount){
        newPressure += amount;
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

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }
}
