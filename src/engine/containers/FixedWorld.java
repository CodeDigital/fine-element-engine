package engine.containers;

import engine.math.V2D;
import engine.math.XMath;

public class FixedWorld extends Grid{

    public FixedWorld(int WIDTH, int HEIGHT) {
        super(WIDTH, HEIGHT);
        clear();
    }

    @Override
    public void update(double dt) {
        int offsetX = (int) XMath.random(0, Chunk.WIDTH);
        int offsetY = (int) XMath.random(0, Chunk.WIDTH);

        boolean lrtbGrid = XMath.randomBoolean();
        boolean lrGrid = XMath.randomBoolean();
        boolean tbGrid = XMath.randomBoolean();
        boolean lrtbChunk = XMath.randomBoolean();
        boolean lrChunk = XMath.randomBoolean();
        boolean tbChunk = XMath.randomBoolean();

        // On X left to right
        for(int i = 0; i < 2; i++){
            int cx = i * offsetX;
            while(cx < WIDTH_CELLS){

                ////////

                cx ++;
                if((cx - offsetX) % Chunk.WIDTH == 0) cx += Chunk.WIDTH;
            }
        }

        // On X right to left
        for(int i = 0; i < 2; i++){
            int cx = WIDTH_CELLS - i * offsetX - 1;
            while(cx >= 0){

                ////////

                cx --;
                if((cx + offsetX) % Chunk.WIDTH == 0) cx -= Chunk.WIDTH;
            }
        }

        // On X left to right
        for(int j = 0; j < 2; j++){
            int cy = j * offsetY;
            while(cy < HEIGHT_CELLS){

                ////////

                cy ++;
                if((cy - offsetY) % Chunk.WIDTH == 0) cy += Chunk.WIDTH;
            }
        }

        // On X right to left
        for(int j = 0; j < 2; j++){
            int cy = HEIGHT_CELLS - j * offsetY - 1;
            while(cy >= 0){

                ////////

                cy --;
                if((cy + offsetY) % Chunk.WIDTH == 0) cy -= Chunk.WIDTH;
            }
        }

        


//        for(int i = 0; i < 2; i++){
//
//            if(lrtbGrid){
//
//                if(lrGrid){
//
//
//
//                }else{
//
//                }
//
//            }else{
//
//                if(tbGrid){
//
//                }else{
//
//                }
//
//            }
//
//        }
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
