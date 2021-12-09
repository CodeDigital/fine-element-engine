package engine;

/**
 * This should go for any graphics system that is used here (which may) be implemented.
 */
public interface GraphicSystem {

    /**
     * Draw the function for the draw/update loop.
     */
    void draw();

    /**
     * Settings function (runs first once).
     */
    void settings();

    /**
     * Setup function (runs second once).
     */
    void setup();

    /**
     * Disables stroke surrounding an image/shape.
     */
    void noStroke();

    /**
     * Color int.
     *
     * @param gray the gray amt
     * @return colour as an integer
     */
    int color(int gray);

}
