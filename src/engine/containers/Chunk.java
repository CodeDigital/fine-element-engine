package engine.containers;

import engine.Colour;
import engine.Graphics;
import engine.Renderable;
import engine.Steppable;
import engine.elements.Element;
import engine.elements.ElementData;
import engine.math.V2D;
import engine.math.XMath;
import processing.core.PConstants;
import processing.core.PImage;

public class Chunk implements Renderable, Steppable {

    public static final int WIDTH = 32;
    public static final int SIZE = WIDTH * WIDTH;
    public final V2D LOCATION;
    private Cell[][] cells;
    private PImage texture;
    public final Grid WORLD;

    private final int TOTAL_FRAMES_UPDATED = 10;
    private int residualFramesUpdated = TOTAL_FRAMES_UPDATED;
    private boolean updated = true;

    public Chunk(Grid WORLD, V2D LOCATION) {
        cells = new Cell[WIDTH][WIDTH];
        this.LOCATION = LOCATION;
        this.WORLD = WORLD;
        clear();
    }

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
        if(!updated){
            residualFramesUpdated--;
        }

        if(residualFramesUpdated < 0){
            residualFramesUpdated = -1;
        }

        for(Cell[] row:cells){
            for(Cell c:row){
                c.stepPost(dt);
            }
        }
    }

    public void resetUpdated(){
        residualFramesUpdated = TOTAL_FRAMES_UPDATED;
        updated = true;
    }

    public V2D getRelativeIJ(V2D cellLocation){
        return cellLocation.subtract(LOCATION.multiply(WIDTH));
    }

    @Override
    public void render() {
        double showSize = WIDTH * Cell.getWidth();
        V2D showLocation = LOCATION.multiply(showSize);

        if(updated || texture == null){
            texture = Graphics.G().createImage(WIDTH, WIDTH, PConstants.ARGB);
            texture.loadPixels();
            for(int j = 0; j < WIDTH; j++){
                for(int i = 0; i < WIDTH; i++){
                    texture.pixels[j*WIDTH + i] = cells[j][i].getElement().getColour().asInt();
//                    texture.pixels[j*WIDTH + i] = cells[j][i].getElement().getColour().setR(
//                            XMath.map(cells[j][i].getElement().getTemperature(), 0, 110, 0, 255)
//                    ).asInt();
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

    public Cell getCell(V2D at) {
        if(inBounds(at)){
            V2D ij = getRelativeIJ(at);
            return cells[(int) ij.Y][(int) ij.X];
        }else{
            return WORLD.getCell(at);
        }
    }

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

        V2D ij = getRelativeIJ(at);

        return (ij.X >= 0 && ij.X < WIDTH &&
                ij.Y >= 0 && ij.Y < WIDTH);
    }

    public Cell[][] getCells() {
        return cells;
    }

    public PImage getTexture() {
        return texture;
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

    public void setUpdated(boolean updated) {
        this.updated = updated;
    }

    public boolean isActive() {
        return residualFramesUpdated > 0;
    }
}
