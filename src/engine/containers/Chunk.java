package engine.containers;

import engine.Colour;
import engine.Graphics;
import engine.Renderable;
import engine.Steppable;
import engine.elements.Element;
import engine.elements.ElementData;
import engine.math.V2D;
import processing.core.PConstants;
import processing.core.PImage;

/**
 * The type Chunk.
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
    private final int TOTAL_FRAMES_UPDATED = 10; // how long to check for updated after an update occurred.
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
        for(Cell[] row:cells){
            for(Cell c:row){
                c.stepPre(dt);
            }
        }
    }

    @Override
    public void stepPhysics(double dt) {/* DOES NOTHING */}

    @Override
    public void stepFSS(double dt) {/* DOES NOTHING */}

    @Override
    public void stepPost(double dt) {

        // perform post-step iterations.
        for(Cell[] row:cells){
            for(Cell c:row){
                c.stepPost(dt);
            }
        }

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
     * Get the cell location as ij 2d array coordinates.
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
            texture.loadPixels();
            for(int j = 0; j < WIDTH; j++){
                for(int i = 0; i < WIDTH; i++){
                    texture.pixels[j*WIDTH + i] = cells[j][i].getElement().getColour().asInt();
                }
            }
            texture.updatePixels();
        }

        Graphics.G().imageMode(PConstants.CORNER);
        Graphics.G().image(texture, (float) showLocation.X, (float) showLocation.Y, (float) showSize, (float) showSize);


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
     * Gets cell.
     *
     * @param at the at
     * @return the cell
     */
    public Cell getCell(V2D at) {
        if(inBounds(at)){
            V2D ij = toCoordinate(at);
            return cells[(int) ij.Y][(int) ij.X];
        }else{
            return WORLD.getCell(at);
        }
    }

    /**
     * Clear.
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

    private boolean inBounds(V2D at) {

        V2D ij = toCoordinate(at);

        return (ij.X >= 0 && ij.X < WIDTH &&
                ij.Y >= 0 && ij.Y < WIDTH);
    }

    /**
     * Get cells cell [ ] [ ].
     *
     * @return the cell [ ] [ ]
     */
    public Cell[][] getCells() {
        return cells;
    }

    /**
     * Gets texture.
     *
     * @return the texture
     */
    public PImage getTexture() {
        return texture;
    }

    /**
     * Gets total frames updated.
     *
     * @return the total frames updated
     */
    public int getTOTAL_FRAMES_UPDATED() {
        return TOTAL_FRAMES_UPDATED;
    }

    /**
     * Gets residual frames updated.
     *
     * @return the residual frames updated
     */
    public int getResidualFramesUpdated() {
        return residualFramesUpdated;
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
     * Is active boolean.
     *
     * @return the boolean
     */
    public boolean isActive() {
        return residualFramesUpdated > 0;
    }
}
