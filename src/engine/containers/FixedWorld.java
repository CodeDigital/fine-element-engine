package engine.containers;

import engine.math.V2D;

public class FixedWorld extends Grid{

    public FixedWorld(int WIDTH, int HEIGHT) {
        super(WIDTH, HEIGHT);
    }

    @Override
    public Cell getCell(V2D at) {

        return null;
    }

    @Override
    public V2D getChunkLocation(V2D cellLocation) {
        return new V2D(cellLocation.X / Chunk.WIDTH, cellLocation.Y / Chunk.WIDTH);
    }
}
