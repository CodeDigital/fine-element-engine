package engine;

public class Graphics extends GraphicsProcessing{

    private static Graphics g;

    private Graphics(int WIDTH, int HEIGHT, boolean FULLSCREEN, boolean USE_P2D) {
        super(WIDTH, HEIGHT, FULLSCREEN, USE_P2D);
    }

    public void start(int WIDTH, int HEIGHT, boolean FULLSCREEN, boolean USE_P2D){
        g = new Graphics(WIDTH, HEIGHT, FULLSCREEN, USE_P2D);
    }

    public Graphics getG(){
        if(g == null) System.exit(-10);
        return g;
    }
}
