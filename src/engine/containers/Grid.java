package engine.containers;

import engine.Debug;
import engine.Renderable;
import engine.math.V2D;
import engine.math.XMath;

public abstract class Grid implements Renderable {
    public final int WIDTH, HEIGHT, SIZE, WIDTH_CELLS, HEIGHT_CELLS;
    protected Chunk[][] chunks;
    protected V2D gravity = V2D.CARDINALS[2].multiply(9.8);

    public Grid(int WIDTH, int HEIGHT) {
        this.WIDTH = WIDTH;
        WIDTH_CELLS = WIDTH * Chunk.WIDTH;
        this.HEIGHT = HEIGHT;
        HEIGHT_CELLS = HEIGHT * Chunk.WIDTH;
        SIZE = WIDTH * HEIGHT;
        chunks = new Chunk[HEIGHT][WIDTH];
    }

    public void update(double dt) {

        for(Chunk[] row:chunks){
            for(Chunk c:row){
                c.stepPre(dt);
            }
        }

        for(int i = 0; i < 4; i++){
            updateStep(dt, i);
        }

        for(Chunk[] row:chunks){
            for(Chunk c:row){
                c.stepPost(dt);
            }
        }
    }

    private int getIndexStart(int d, int offset, int max, boolean alternate){
        int o = (alternate ? offset:0);

        if(d > 0){
            return o;
        }else{
            return max - (o + 1);
        }
    }

    public void updateStep(double dt, int step) {
        int ox = (int) XMath.random(0, Chunk.WIDTH);
        int oy = (int) XMath.random(0, Chunk.WIDTH);

        boolean startOnX = XMath.randomBoolean();
        boolean alternate = true;

        int dx = XMath.randomBoolean() ? 1:-1;
        int dy = XMath.randomBoolean() ? 1:-1;

        if(startOnX){

            int cx = getIndexStart(dx, ox, HEIGHT_CELLS, false);

            // iterate over x
            while(cx >= 0 && cx < HEIGHT_CELLS){

                int cy = getIndexStart(dy, oy, WIDTH_CELLS, alternate);

                // iterate over y
                while(cy >= 0 && cy < WIDTH_CELLS){

                    Cell c = getCell(new V2D(cy, cx));

                    switch (step){
                        case 0:
                            c.stepPre(dt);
                            break;
                        case 1:
                            c.stepPhysics(dt);
                            break;
                        case 2:
                            c.stepFSS(dt);
                            break;
                        case 3:
                            c.stepPost(dt);
                            break;
                    }

                    cy += dy;
                    if((cy - dy * oy) % Chunk.WIDTH == 0) cy += dy * Chunk.WIDTH;
                }

                alternate = !alternate;
                cx += dx;
                if((cx - dx * ox) % Chunk.WIDTH == 0) alternate = !alternate;
            }

            cx = getIndexStart(dx, ox, HEIGHT_CELLS, false);
            alternate = false;

            // iterate over x
            while(cx >= 0 && cx < HEIGHT_CELLS){

                int cy = getIndexStart(dy, oy, WIDTH_CELLS, alternate);

                // iterate over y
                while(cy >= 0 && cy < WIDTH_CELLS){

                    Cell c = getCell(new V2D(cy, cx));

                    switch (step){
                        case 0:
                            c.stepPre(dt);
                            break;
                        case 1:
                            c.stepPhysics(dt);
                            break;
                        case 2:
                            c.stepFSS(dt);
                            break;
                        case 3:
                            c.stepPost(dt);
                            break;
                    }

                    cy += dy;
                    if((cy - dy * oy) % Chunk.WIDTH == 0) cy += dy * Chunk.WIDTH;
                }

                alternate = !alternate;
                cx += dx;
                if((cx - dx * ox) % Chunk.WIDTH == 0) alternate = !alternate;
            }

        }else{

            int cy = getIndexStart(dy, oy, HEIGHT_CELLS, false);

            // iterate over y
            while(cy >= 0 && cy < HEIGHT_CELLS){

                int cx = getIndexStart(dx, ox, WIDTH_CELLS, alternate);

                // iterate over x
                while(cx >= 0 && cx < WIDTH_CELLS){

                    Cell c = getCell(new V2D(cx, cy));

                    switch (step){
                        case 0:
                            c.stepPre(dt);
                            break;
                        case 1:
                            c.stepPhysics(dt);
                            break;
                        case 2:
                            c.stepFSS(dt);
                            break;
                        case 3:
                            c.stepPost(dt);
                            break;
                    }

                    cx += dx;
                    if((cx - dx * ox) % Chunk.WIDTH == 0) cx += dx * Chunk.WIDTH;
                }

                alternate = !alternate;
                cy += dy;
                if((cy - dy * oy) % Chunk.WIDTH == 0) alternate = !alternate;
            }

            cy = getIndexStart(dy, oy, HEIGHT_CELLS, false);
            alternate = false;

            // iterate over y
            while(cy >= 0 && cy < HEIGHT_CELLS){

                int cx = getIndexStart(dx, ox, WIDTH_CELLS, alternate);

                // iterate over x
                while(cx >= 0 && cx < WIDTH_CELLS){

                    Cell c = getCell(new V2D(cx, cy));

                    switch (step){
                        case 0:
                            c.stepPre(dt);
                            break;
                        case 1:
                            c.stepPhysics(dt);
                            break;
                        case 2:
                            c.stepFSS(dt);
                            break;
                        case 3:
                            c.stepPost(dt);
                            break;
                    }

                    cx += dx;
                    if((cx - dx * ox) % Chunk.WIDTH == 0) cx += dx * Chunk.WIDTH;
                }

                alternate = !alternate;
                cy += dy;
                if((cy - dy * oy) % Chunk.WIDTH == 0) alternate = !alternate;
            }

        }
    }

    @Override
    public void render() {
        for(Chunk[] row:chunks){
            for(Chunk c:row){
                c.render();
            }
        }
    }

    public abstract Cell getCell(V2D at);
    public abstract Chunk getChunk(V2D at);

    public V2D toCoordinate(V2D point){
        return point.multiply(1 / Cell.getWidth());
    }

    public Chunk[][] getChunks() {
        return chunks;
    }

    public V2D getGravity() {
        return gravity;
    }

    public void setGravity(V2D gravity) {
        this.gravity = gravity;
    }
}

/**
 //
 //        // On X left to right
 //        for(int i = 0; i < 2; i++){
 //            int cx = i * offsetX;
 //            while(cx < WIDTH_CELLS){
 //
 //                ////////
 //
 //                cx ++;
 //                if((cx - offsetX) % Chunk.WIDTH == 0) cx += Chunk.WIDTH;
 //            }
 //        }
 //
 //        // On X right to left
 //        for(int i = 0; i < 2; i++){
 //            int cx = WIDTH_CELLS - i * offsetX - 1;
 //            while(cx >= 0){
 //
 //                ////////
 //
 //                cx --;
 //                if((cx + offsetX) % Chunk.WIDTH == 0) cx -= Chunk.WIDTH;
 //            }
 //        }
 //
 //        // On Y top to bottom
 //        for(int j = 0; j < 2; j++){
 //            int cy = j * offsetY;
 //            while(cy < HEIGHT_CELLS){
 //
 //                ////////
 //
 //                cy ++;
 //                if((cy - offsetY) % Chunk.WIDTH == 0) cy += Chunk.WIDTH;
 //            }
 //        }
 //
 //        // On Y bottom to top
 //        for(int j = 0; j < 2; j++){
 //            int cy = HEIGHT_CELLS - j * offsetY - 1;
 //            while(cy >= 0){
 //
 //                ////////
 //
 //                cy --;
 //                if((cy + offsetY) % Chunk.WIDTH == 0) cy -= Chunk.WIDTH;
 //            }
 //        }
 //

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
 */