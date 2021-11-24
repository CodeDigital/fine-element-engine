package engine.containers;

import engine.math.V2D;
import engine.math.XMath;

public class FixedWorld extends Grid{

    public FixedWorld(int WIDTH, int HEIGHT) {
        super(WIDTH, HEIGHT);
        clear();
    }

    @Override
    public Cell getCell(V2D at) {
        if(inBounds(at)){
            V2D ij = getChunkLocation(at);
            return chunks[(int) ij.Y][(int) ij.X].getCell(at);
        }
        return null;
    }

    @Override
    public V2D getChunkLocation(V2D cellLocation) {
        return new V2D(cellLocation.X / Chunk.WIDTH, cellLocation.Y / Chunk.WIDTH);
    }

    public void clear(){
        chunks = new Chunk[HEIGHT][WIDTH];
        for(int j = 0; j < HEIGHT; j++){
            for(int i = 0; i < WIDTH; i++){
                chunks[j][i] = new Chunk(this, new V2D(i, j));
            }
        }
    }

    private boolean inBounds(V2D at) {
        return (at.X >= 0 && at.X < WIDTH * Chunk.WIDTH &&
                at.Y >= 0 && at.Y < WIDTH * Chunk.WIDTH);
    }
}