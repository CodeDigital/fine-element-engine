package engine;

import processing.core.PApplet;

public class GraphicsProcessing extends PApplet implements GraphicSystem{

    public final int WIDTH, HEIGHT;
    public final boolean FULLSCREEN, USE_P2D;

    protected GraphicsProcessing(int WIDTH, int HEIGHT, boolean FULLSCREEN, boolean USE_P2D) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.FULLSCREEN = FULLSCREEN;
        this.USE_P2D = USE_P2D;

        String[] appletArgs = new String[]{"engine.Graphics"};
        PApplet.runSketch(appletArgs, this);
    }

    public void settings(){

    }
}
