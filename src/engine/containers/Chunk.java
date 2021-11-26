package engine.containers;

import engine.Graphics;
import engine.Renderable;
import engine.Steppable;
import engine.elements.Sand;
import engine.math.V2D;
import processing.core.PConstants;
import processing.core.PImage;

public class Chunk implements Renderable, Steppable {

    public static final int WIDTH = 32;
    public static final int SIZE = WIDTH * WIDTH;
    private V2D location;
    private Cell[][] cells;
    private PImage texture;
    private Grid world;

    private final int TOTAL_FRAMES_UPDATED = 3;
    private int residualFramesUpdated = TOTAL_FRAMES_UPDATED;
    private boolean updated = true;

    public Chunk(Grid world, V2D location) {
        cells = new Cell[WIDTH][WIDTH];
        this.location = location;
        this.world = world;
        clear();
    }

    public void stepPre(double dt, V2D cellLocation){
        V2D ij = getRelativeIJ(cellLocation);
        cells[(int) ij.Y][(int) ij.X].stepPre(dt);
    }

    public void stepPhysics(double dt, V2D cellLocation){
        V2D ij = getRelativeIJ(cellLocation);
        cells[(int) ij.Y][(int) ij.X].stepPhysics(dt);
    }
    public void stepFSS(double dt, V2D cellLocation){
        V2D ij = getRelativeIJ(cellLocation);
        cells[(int) ij.Y][(int) ij.X].stepFSS(dt);
    }

    public void stepPost(double dt, V2D cellLocation){
        V2D ij = getRelativeIJ(cellLocation);
        cells[(int) ij.Y][(int) ij.X].stepPost(dt);
    }

    @Override
    public void stepPre(double dt) {
        updated = false;
    }

    @Override
    public void stepPhysics(double dt) {/* DOES NOTHING */}

    @Override
    public void stepFSS(double dt) {/* DOES NOTHING */}

    @Override
    public void stepPost(double dt) {
        if(updated){
            resetUpdated();
        }else{
            residualFramesUpdated--;
        }
    }

    public void resetUpdated(){
        residualFramesUpdated = TOTAL_FRAMES_UPDATED;
    }

    public V2D getRelativeIJ(V2D cellLocation){
        return cellLocation.subtract(location.multiply(WIDTH));
    }

    @Override
    public void render() {
        double showSize = WIDTH * Cell.getWidth();
        V2D showLocation = location.multiply(showSize);

        if(updated){
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
    }

    public void setPixel(V2D pixel, int colour) {
        V2D ij = getRelativeIJ(pixel);
        texture.pixels[(int) (ij.Y * WIDTH + ij.X)] = colour;
    }

    public Cell getCell(V2D at) {
        if(inBounds(at)){
            V2D ij = getRelativeIJ(at);
            return cells[(int) ij.Y][(int) ij.X];
        }else{
            return world.getCell(at);
        }
    }

    public void clear(){
        cells = new Cell[WIDTH][WIDTH];
        for(int j = 0; j < WIDTH; j++){
            for(int i = 0; i < WIDTH; i++){
                V2D pixelLocation = location.multiply(WIDTH).addX(i).addY(j);
                cells[j][i] = new Cell(this, pixelLocation);
                cells[j][i].setElement(new Sand());
            }
        }
    }

    private boolean inBounds(V2D at) {

        V2D ij = getRelativeIJ(at);

        return (ij.X >= 0 && ij.X < WIDTH &&
                ij.Y >= 0 && ij.Y < WIDTH);
    }

    public V2D getLocation() {
        return location;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public PImage getTexture() {
        return texture;
    }

    public Grid getWorld() {
        return world;
    }

    public int getTOTAL_FRAMES_UPDATED() {
        return TOTAL_FRAMES_UPDATED;
    }

    public int getResidualFramesUpdated() {
        return residualFramesUpdated;
    }

    public boolean isUpdated() {
        return updated;
    }
}
