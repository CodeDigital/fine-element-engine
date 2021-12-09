package engine;

import processing.core.PApplet;
import processing.opengl.PGraphicsOpenGL;

// TODO: Commenting
public class GraphicsProcessing extends PApplet implements GraphicSystem{

    public final int WIDTH, HEIGHT;
    public final boolean FULLSCREEN, USE_P2D;
    private Game game;

    protected GraphicsProcessing(Game game, int WIDTH, int HEIGHT, boolean FULLSCREEN, boolean USE_P2D) {
        this.game = game;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.FULLSCREEN = FULLSCREEN;
        this.USE_P2D = USE_P2D;

        String[] appletArgs = new String[]{"engine.Graphics"};
        PApplet.runSketch(appletArgs, this);
    }

    public void settings(){
        if(FULLSCREEN){
            if(USE_P2D){
                fullScreen(P2D);
            }else{
                fullScreen();
            }
        }else{
            if(USE_P2D){
                size(WIDTH, HEIGHT, P2D);
            }else{
                size(WIDTH, HEIGHT);
            }
        }

        noSmooth();
    }

    public void setup() {
        noSmooth();
        if(USE_P2D){
            hint(DISABLE_TEXTURE_MIPMAPS);
            ((PGraphicsOpenGL)g).textureSampling(2);
        }

        game.setup();
    }

    public void draw(){
        background(255);
        game.loop();
    }

    public void mousePressed(){
        game.mousePressed();
    }
    public void mouseClicked(){
        game.mouseClicked();
    }
}
