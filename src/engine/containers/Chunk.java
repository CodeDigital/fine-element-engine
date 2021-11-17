package engine.containers;

public class Chunk{

    public final int WIDTH, SIZE;
    private Cell[][] cells;

    public Chunk(int WIDTH) {
        this.WIDTH = WIDTH;
        SIZE = WIDTH * WIDTH;
        cells = new Cell[WIDTH][WIDTH];
    }


}
