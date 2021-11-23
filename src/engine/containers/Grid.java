package engine.containers;

import engine.Renderable;
import engine.math.V2D;

public abstract class Grid implements Renderable {
    public final int CELL_WIDTH = 10;
    public final int WIDTH, HEIGHT, SIZE, WIDTH_CELLS, HEIGHT_CELLS;
    protected Chunk[][] chunks;

    public Grid(int WIDTH, int HEIGHT) {
        this.WIDTH = WIDTH;
        WIDTH_CELLS = WIDTH * Chunk.WIDTH;
        this.HEIGHT = HEIGHT;
        HEIGHT_CELLS = HEIGHT * Chunk.WIDTH;
        SIZE = WIDTH * HEIGHT;
        chunks = new Chunk[HEIGHT][WIDTH];
    }

    public abstract void update(double dt);

    @Override
    public void render() {
        for(Chunk[] row:chunks){
            for(Chunk c:row){
                c.render();
            }
        }
    }

    public abstract Cell getCell(V2D at);
    public abstract V2D getChunkLocation(V2D cellLocation);
}
