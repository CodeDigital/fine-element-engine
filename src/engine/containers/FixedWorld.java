package engine.containers;

import engine.Graphics;
import engine.math.V2D;

/**
 * A fixed size world. this exists just for sandboxing / testing purposes.
 */
public class FixedWorld extends Grid{

    /**
     * Instantiates a new Fixed world.
     *
     * @param WIDTH  the width of the world in chunks
     * @param HEIGHT the height of the world in chunks
     */
    public FixedWorld(int WIDTH, int HEIGHT) {
        super(WIDTH, HEIGHT);
        clear();
    }

    /**
     * Calculate the cell width based on the size.
     */
    public void setCellWidth(){
        double newCellWidth = Math.min(
                Graphics.G().WIDTH / (Chunk.WIDTH * WIDTH),
                Graphics.G().HEIGHT / (Chunk.WIDTH * HEIGHT)
        );
        Cell.setWidth(newCellWidth);
    }

    @Override
    public Cell getCell(V2D at) {
        if(inBounds(at)){
            V2D ij = toChunkCoordinate(at);
            return chunks[(int) ij.Y][(int) ij.X].getCell(at);
        }
        return null;
    }

    @Override
    public Chunk getChunk(V2D at) {
        if(inBounds(at)){
            V2D ij = toChunkCoordinate(at);
            return chunks[(int) ij.Y][(int) ij.X];
        }
        return null;
    }

    @Override
    public V2D toCoordinate(V2D point) {
        return point.multiply(1 / Cell.getWidth());
    }

    /**
     * Convert a cell or pixel location to chunk coordinates.
     * @param point the cell/pixel location
     * @return the chunk coordinates
     */
    public V2D toChunkCoordinate(V2D point) {
        return new V2D((point.X / Chunk.WIDTH), (point.Y / Chunk.WIDTH));
    }

    /**
     * Clear the whole world (recreate chunks).
     */
    public void clear(){

        // recreate the whole chunk vector.
        chunks = new Chunk[HEIGHT][WIDTH];
        for(int j = 0; j < HEIGHT; j++){
            for(int i = 0; i < WIDTH; i++){
                chunks[j][i] = new Chunk(this, new V2D(i, j));
            }
        }

        // initialize chunks.
        for(Chunk[] row:chunks){
            for(Chunk c:row){
                c.initialize();
            }
        }
    }

    /**
     * Check whether a given vector is within the world bounds.
     * @param at the location to check
     * @return whether it's in bounds
     */
    public boolean inBounds(V2D at) {
        return (at.X >= 0 && at.X < WIDTH * Chunk.WIDTH &&
                at.Y >= 0 && at.Y < HEIGHT * Chunk.WIDTH);
    }
}
