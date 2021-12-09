package engine.containers;

import engine.Renderable;
import engine.Steppable;
import engine.math.V2D;
import engine.math.XMath;

import java.util.ArrayList;

/**
 * A grid of chunks. This can be infinite (theoretically) or fixed size.
 */
public abstract class Grid implements Renderable, Steppable {


    public final int    WIDTH, // width in # chunks
                        HEIGHT, // height in # chunks
                        SIZE, // # of chunks in the 2d array
                        WIDTH_CELLS, // width in # cells
                        HEIGHT_CELLS; // height in # cells.


    // the array of chunks.
    protected Chunk[][] chunks;


    // world gravity.
    protected V2D gravity = V2D.OCTALS[5].multiply(XMath.GRAVITY);

    /**
     * Instantiates a new Grid.
     *
     * @param WIDTH  the width of the world in chunks (actively shown)
     * @param HEIGHT the height of the world in chunks (actively shown)
     */
    public Grid(int WIDTH, int HEIGHT) {
        this.WIDTH = WIDTH;
        WIDTH_CELLS = WIDTH * Chunk.WIDTH;
        this.HEIGHT = HEIGHT;
        HEIGHT_CELLS = HEIGHT * Chunk.WIDTH;
        SIZE = WIDTH * HEIGHT;
        chunks = new Chunk[HEIGHT][WIDTH];
    }

    /**
     * Finds the starting index used in stepAlgorithmicOrder().
     * @param d whether going forwards or backwards (will always be -1 or 1);
     * @param offset offset of the index
     * @param max maximum of the dimension
     * @param alternate whether we are at an alternated point on the checker board
     * @return the starting index.
     */
    private int getIndexStart(int d, int offset, int max, boolean alternate){

        // start at the offset if alternate or zero if not
        int o = (alternate ? offset:0);

        if(d > 0){
            return o;
        }else{
            return max - (o + 1);
        }
    }

    /**
     * Create the order of the cells to iterate over for a grid step.
     * Like: https://youtu.be/5Ka3tbbT-9E
     * See the multithreading portion of the video.
     * Here this isn't used for multithreading, but for updating semi-randomly.
     * Avoids step formations as described in the video.
     * Here, the offset isn't only on X, but also on Y
     * (so imagine a checkerboard of update steps).
     * XOXOXOXOXOXOXOXO
     * OXOXOXOXOXOXOXOX
     * XOXOXOXOXOXOXOXO
     * OXOXOXOXOXOXOXOX
     * XOXOXOXOXOXOXOXO
     * OXOXOXOXOXOXOXOX
     * @return the array list
     */
    public ArrayList<Cell> stepAlgorithmicOrder(){

        // output order
        ArrayList<Cell> output = new ArrayList<>();

        // find the offsets
        int ox = (int) XMath.random(0, Chunk.WIDTH);
        int oy = (int) XMath.random(0, Chunk.WIDTH);

        // determine whether we go row by row (true) or col by col (false).
        boolean startOnX = XMath.randomBoolean();
        boolean alternate = true;

        // TODO: Commenting from this point here.
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
                    output.add(c);

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
                    output.add(c);

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
                    output.add(c);

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
                    output.add(c);

                    cx += dx;
                    if((cx - dx * ox) % Chunk.WIDTH == 0) cx += dx * Chunk.WIDTH;
                }

                alternate = !alternate;
                cy += dy;
                if((cy - dy * oy) % Chunk.WIDTH == 0) alternate = !alternate;
            }

        }

        return output;
    }

    @Override
    public void stepPre(double dt) {
        for(Chunk[] row:chunks){
            for(Chunk c:row){
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
        for(Chunk[] row:chunks){
            for(Chunk c:row){
                c.stepPost(dt);
            }
        }
    }

    /**
     * Perform the grid's update step.
     * @param dt time to step over
     */
    public void update(double dt) {

        // pre step over each chunk
        stepPre(dt);

        // determine the step order for the cells.
        ArrayList<Cell> order = stepAlgorithmicOrder();

        // perform cell pre-steps
        for(Cell c:order){
            c.stepPre(dt);
        }

        // perform cell physics steps.
        for(Cell c:order){
            c.stepPhysics(dt);
        }

        // perform cell fss steps.
        for(Cell c:order){
            c.stepFSS(dt);
        }

        // perform cell post-steps.
        for(Cell c:order){
            c.stepPost(dt);
        }

        // perform chunk post-steps.
        stepPost(dt);
    }

    @Override
    public void render() {
        for(Chunk[] row:chunks){
            for(Chunk c:row){
                c.render();
            }
        }
    }

    /**
     * Gets the cell at a certain location (implementation depends on type of grid / size).
     *
     * @param at a location
     * @return the cell
     */
    public abstract Cell getCell(V2D at);

    /**
     * Gets the chunk at a certain location (implementation depends on type of grid / size).
     *
     * @param at a location
     * @return the chunk
     */
    public abstract Chunk getChunk(V2D at);

    /**
     * Gets the cell coordinate translation of a pixel point on the screen.
     * (implementation depends on type of grid / size).
     * @param point the pixel location
     * @return cell coordinate vector
     */
    public abstract V2D toCoordinate(V2D point);

    /**
     * Gets gravity on the grid.
     * @return the gravity
     */
    public V2D getGravity() {
        return gravity;
    }

    /**
     * Sets gravity.
     * @param gravity the gravity
     */
    public void setGravity(V2D gravity) {
        this.gravity = gravity;
    }

    /**
     * Gets the force on a cell at a certain location.
     * @param location the location
     * @return the force
     */
    public V2D forceOn(V2D location) {
        return gravity;
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