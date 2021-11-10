import engine.Game;

public class Painter extends Game {

    public Painter(int WIDTH, int HEIGHT, boolean FULLSCREEN, boolean USE_P2D) {
        super(WIDTH, HEIGHT, FULLSCREEN, USE_P2D);
    }

    public static void main(String[] args) {

        Painter p = new Painter(1280, 720, false, false);

    }

    @Override
    public void updateLoop(double dt) {

    }

    @Override
    public void drawLoop() {

    }
}
