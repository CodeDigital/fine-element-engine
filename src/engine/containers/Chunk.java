package engine.containers;

import engine.Graphics;
import engine.Renderable;
import processing.core.PConstants;
import processing.core.PImage;

public class Chunk implements Renderable {

    public final int WIDTH, SIZE;
    private Cell[][] cells;
    private PImage texture;
    private Grid world;

    public Chunk(Grid world, int WIDTH) {
        this.WIDTH = WIDTH;
        SIZE = WIDTH * WIDTH;
        cells = new Cell[WIDTH][WIDTH];
        texture = Graphics.G().createImage(WIDTH, WIDTH, PConstants.ARGB);
    }



    @Override
    public void render() {

    }
}
