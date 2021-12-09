package engine.containers;

import engine.Steppable;
import engine.elements.Element;
import engine.elements.ElementData;
import engine.math.MAT22;
import engine.math.V2D;
import engine.math.XMath;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * The type Cell.
 */
public class Cell implements Steppable {

    // cell width
    private static double width = 1;

    // container Chunk and location within world
    public final Chunk CHUNK;
    public final V2D LOCATION;

    // bordering chunks and cells
    public final HashSet<Chunk> CHUNK_BORDERS = new HashSet<>();
    public final ArrayList<Cell> CELL_BORDERS = new ArrayList<>();

    // element contained (otherwise null)
    private Element element;

    // forces and gravity
    private MAT22 direction;
    private double pressure = ElementData.REST_PRESSURE + XMath.random(-1000, 1000);
    private double newPressure = pressure;

    // whether the cell has been updated
    private boolean updated = false;

    /**
     * Instantiates a new Cell.
     *
     * @param CHUNK    the chunk
     * @param LOCATION the location
     */
    public Cell(Chunk CHUNK, V2D LOCATION) {
        this.CHUNK = CHUNK;
        this.LOCATION = LOCATION;
    }

    /**
     * Initialize the cell (needs to be called after the world/chunks have been initialized).
     */
    public void initialize(){

        // set the direction of rotation due to gravity / force
        direction = CHUNK.WORLD.forceOn(LOCATION).getRotation();

        // get the bordering chunks
        for(V2D oct:V2D.OCTALS){

            // if a different chunk is within 0.5 chunk width of location, add to chunk borders.
            V2D loc = oct.multiply(Chunk.WIDTH/2).add(LOCATION);
            Chunk ch = CHUNK.WORLD.getChunk(loc);
            if(ch != null) CHUNK_BORDERS.add(ch);

            // Add the bordering cell at oct relative to location to cell borders.
            loc = oct.add(LOCATION);
            Cell ce = CHUNK.getCell(loc);
            CELL_BORDERS.add(ce);

        }
    }

    @Override
    public void stepPre(double dt) {
        updated = false; // set the cell as not updated
        if(element == null) return; // no element means no stepping.
        element.stepPre(dt); // perform step on non-null element.
    }

    @Override
    public void stepPhysics(double dt) {
        if(!CHUNK.isActive()) return; // perform physics step only if chunk is active.
        if(element == null) return; // no element means no stepping.
        if(updated) return; // don't update if already updated.
        element.stepPhysics(dt); // perform step on non-null element.
    }

    @Override
    public void stepFSS(double dt) {
        if(!CHUNK.isActive()) return; // perform physics step only if chunk is active.
        if(element == null) return; // no element means no stepping.
        if(updated) return; // don't update if already updated.
        element.stepFSS(dt); // perform step on non-null element.
    }

    @Override
    public void stepPost(double dt) {
        pressure = newPressure; // update pressure if necessary.
        if(element == null) return; // no element means no stepping.
        element.stepPost(dt); // perform step on non-null element.
    }

    /**
     * Swap the elements between two cells.
     *
     * @param with the cell to swap with
     */
    public void swap(Cell with){
        // set new and prior elements contained here.
        Element prev = this.element;
        Element next = with.getElement();

        // set the elements accordingly.
        with.setElement(prev);
        setElement(next);
    }

    /**
     * Swap the elements between two cells.
     *
     * @param with the location of the cell to swap with
     */
    public void swap(V2D with){
        Cell cellWith = CHUNK.getCell(with);
        swap(cellWith);
    }

    /**
     * Check if the elements between two cells can be swapped.
     *
     * @param with the cell to swap with
     * @return whether the elements can be swapped
     */
    public boolean canSwap(Cell with){
        if(with == null) return false; // can't swap with null.
        if(with.isUpdated()) return false; // can't swap with an updated cell.
        Element e = with.getElement();
        if(e != null){ // can't swap with null element.
            if(e.isStatic()) return false; // can't swap with static.

            // can't swap if both solid.
            if(e.MATTER == element.MATTER &&
                    element.MATTER == ElementData.MATTER_SOLID) return false;

            // can swap if denser than with.
            if(e.getDensity() < element.getDensity()) return true;

            // can swap if equal density (and small chance)
            // NOTE: Exclude air because that's a lot of processing power saved.
            if(e.getDensity() == element.getDensity() &&
                e.TYPE != ElementData.AIR){
                return Math.random() < XMath.EPSILON;
            }
        }

        return false; // can't be swapped otherwise
    }

    /**
     * Check if the elements between two cells can be swapped.
     *
     * @param with the location of the cell to swap with
     * @return whether the elements can be swapped
     */
    public boolean canSwap(V2D with){
        Cell cellWith = CHUNK.getCell(with);
        return canSwap(cellWith);
    }

    /**
     * Get the force on this cell.
     * TODO: Add pressure simulation
     *
     * @return the vector of the force
     */
    public V2D getForce(){
        return CHUNK.WORLD.forceOn(LOCATION);
    }

    /**
     * Apply direction rotation on a certain vector.
     *
     * @param v the vector to rotate with direction.
     * @return the vector of the force
     */
    public V2D applyDirection(V2D v){
        return direction.multiply(v);
    }

    /**
     * Get the element of the cell.
     *
     * @return the element
     */
    public Element getElement() {
        return element;
    }

    /**
     * Sets the element of the cell.
     *
     * @param element the element
     */
    public void setElement(Element element) {
        this.element = element; // set the new element.
        this.element.setCell(this); // set the cell of the element.
        updated = true; // set this cell as updated.

        // reset the update countdown for the bordering and current chunk
        // (since there's been an update, the chunks surrounding should be checked)
        for(Chunk c:CHUNK_BORDERS){
            c.resetUpdated();
        }
        CHUNK.resetUpdated();
    }


    /**
     * Gets direction matrix.
     *
     * @return the direction matrix
     */
    public MAT22 getDirection() {
        return direction;
    }

    /**
     * Sets direction matrix.
     *
     * @param direction the direction matrix
     */
    public void setDirection(MAT22 direction) {
        this.direction = direction;
    }

    /**
     * Is updated boolean.
     *
     * @return the boolean
     */
    public boolean isUpdated() {
        return updated;
    }

    /**
     * Sets updated.
     *
     * @param updated the updated
     */
    public void setUpdated(boolean updated) {
        this.updated = updated;
    }

    /**
     * Gets width.
     *
     * @return the width
     */
    public static double getWidth() {
        return width;
    }

    /**
     * Sets width.
     *
     * @param width the width
     */
    public static void setWidth(double width) {
        Cell.width = width;
    }
}
