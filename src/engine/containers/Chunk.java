package engine.containers;

import engine.*;
import engine.elements.Element;
import engine.elements.ElementData;
import engine.math.V2D;
import engine.math.XMath;
import processing.core.PConstants;
import processing.core.PImage;

/**
 * Chunks within a world (can be active or inactive).
 * Cells within inactive chunks do not perform fss and physics steps to preserve processing time.
 */
public class Chunk implements Renderable, Steppable {

    // Chunk size and location parameters
    public static final int WIDTH = 32;
    public static final int SIZE = WIDTH * WIDTH;
    public final V2D LOCATION;

    // World container and cells contained.
    public final Grid WORLD;
    private Cell[][] cells;
    private PImage texture;

    // Updating trackers
    public static final int TOTAL_FRAMES_UPDATED = (int) Game.FPS; // how long to check for updated after an update occurred.
    private int residualFramesUpdated = TOTAL_FRAMES_UPDATED;
    private boolean updated = true;

    /**
     * Instantiates a new Chunk.
     *
     * @param WORLD    the world
     * @param LOCATION the location
     */
    public Chunk(Grid WORLD, V2D LOCATION) {
        cells = new Cell[WIDTH][WIDTH];
        this.LOCATION = LOCATION;
        this.WORLD = WORLD;
        clear();
    }

    /**
     * Initialize the chunk (needs to be called after the world has been initialized).
     */
    public void initialize() {
        for(Cell[] row:cells){
            for(Cell c:row){
                c.initialize();
            }
        }
    }

    @Override
    public void stepPre(double dt) {
        updated = false;
//        for(Cell[] row:cells){
//            for(Cell c:row){
//                c.stepPre(dt);
//            }
//        }
    }

    @Override
    public void stepPhysics(double dt) {/* DOES NOTHING */}

    @Override
    public void stepFSS(double dt) {/* DOES NOTHING */}

    @Override
    public void stepPost(double dt) {

        // iterate on residual frames left if not updated
        if(!updated){
            residualFramesUpdated--;
        }

        // to minimize the residualFramesUpdated footprint.
        if(residualFramesUpdated < -10){
            residualFramesUpdated = -1;
        }
    }

    /**
     * Reset update tracking parameters.
     */
    public void resetUpdated(){
        residualFramesUpdated = TOTAL_FRAMES_UPDATED;
        updated = true;
    }

    /**
     * Get the cell location as a relative ij 2d array coordinates.
     *
     * @param cellLocation the cell location
     * @return coordinate vector
     */
    public V2D toCoordinate(V2D cellLocation){
        return cellLocation.subtract(LOCATION.multiply(WIDTH));
    }

    @Override
    public void render() {

        // pixel size and location to show the chunk with.
        double showSize = WIDTH * Cell.getWidth();
        V2D showLocation = LOCATION.multiply(showSize);

        // check that the chunk has been updated or has never been rendered before.
        if(updated || texture == null){
            // if so, recreate the texture.
            texture = Graphics.G().createImage(WIDTH, WIDTH, PConstants.ARGB);

            // get all the new colours / pixels.
            texture.loadPixels();
            for(int j = 0; j < WIDTH; j++){
                for(int i = 0; i < WIDTH; i++){
                    Cell c = cells[j][i];
                    texture.pixels[j*WIDTH + i] = c.getElement().getColour().asInt();
//                    texture.pixels[j*WIDTH + i] = c
//                            .getElement()
//                            .getColour()
//                            .setR(
//                                    XMath.map(c.getElement().getTemperature(), XMath.TEMPERATURE_MIN, XMath.toKelvin(120), 0, 255)
//                            )
//                            .asInt();
                }
            }
            texture.updatePixels();
        }

        // display the saved texture
        Graphics.G().imageMode(PConstants.CORNER);
        Graphics.G().image(texture, (float) showLocation.X, (float) showLocation.Y, (float) showSize, (float) showSize);

        // print whether the chunk is active (updating)
        if(!Debug.isDEBUGGING()) return;
        Graphics.G().strokeWeight(1);
        if(isActive()){
            Graphics.G().stroke(Colour.BLACK.asInt());
        }else{
            Graphics.G().noStroke();
        }
        Graphics.G().noFill();
        Graphics.G().rectMode(PConstants.CORNER);
        Graphics.G().rect((float) showLocation.X, (float) showLocation.Y, (float) showSize - 1, (float) showSize - 1);
    }

    /**
     * Gets the cell at specified coordinates.
     *
     * @param at location of the cell (absolute location)
     * @return the requested cell
     */
    public Cell getCell(V2D at) {
        if(inBounds(at)){ // check if the cell is in this chunk
            V2D ij = toCoordinate(at);
            return cells[(int) ij.Y][(int) ij.X];
        }else{ // get the cell from elsewhere in the world
            return WORLD.getCell(at);
        }
    }

    /**
     * Clear / reset the chunk (fill it with air elements).
     */
    public void clear(){
        cells = new Cell[WIDTH][WIDTH];
        for(int j = 0; j < WIDTH; j++){
            for(int i = 0; i < WIDTH; i++){
                V2D pixelLocation = LOCATION.multiply(WIDTH).addX(i).addY(j);
                cells[j][i] = new Cell(this, pixelLocation);
                cells[j][i].setElement(Element.spawn(ElementData.AIR));
            }
        }
    }

    /**
     * Check whether a given vector is within the chunk bounds.
     * @param at the location to check
     * @return whether it's in bounds
     */
    private boolean inBounds(V2D at) {
        V2D ij = toCoordinate(at); // convert to relative index vector.
        return (ij.X >= 0 && ij.X < WIDTH &&
                ij.Y >= 0 && ij.Y < WIDTH);
    }

    /**
     * Check whether the chunk is currently active.
     * @return is active?
     */
    public boolean isActive() {
        return residualFramesUpdated > 0;
    }

    /**
     * Gets residual frames updated.
     * @return the residual frames updated
     */
    public int getResidualFramesUpdated() {
        return residualFramesUpdated;
    }

    /**
     * Is updated boolean.
     * @return the boolean
     */
    public boolean isUpdated() {
        return updated;
    }

    /**
     * Sets updated.
     * @param updated the updated
     */
    public void setUpdated(boolean updated) {
        this.updated = updated;
    }
}
