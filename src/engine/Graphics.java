package engine;

public class Graphics extends GraphicsProcessing{

    private static Graphics g;

    private Graphics(Game game, int WIDTH, int HEIGHT, boolean FULLSCREEN, boolean USE_P2D) {
        super(game, WIDTH, HEIGHT, FULLSCREEN, USE_P2D);
    }

    public static void start(Game game, int WIDTH, int HEIGHT, boolean FULLSCREEN, boolean USE_P2D){
        g = new Graphics(game, WIDTH, HEIGHT, FULLSCREEN, USE_P2D);
    }

    public static Graphics getG(){
        if(g == null) System.exit(-10);
        return g;
    }
}
