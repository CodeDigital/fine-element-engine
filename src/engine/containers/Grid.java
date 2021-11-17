package engine.containers;

import engine.Renderable;

public abstract class Grid implements Renderable {
    public final int WIDTH, HEIGHT, SIZE;
    protected Chunk[][] chunks;

    public Grid(int WIDTH, int HEIGHT) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        SIZE = WIDTH * HEIGHT;
        chunks = new Chunk[HEIGHT][WIDTH];
    }


}
