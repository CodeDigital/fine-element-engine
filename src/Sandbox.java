import engine.Game;
import engine.Graphics;

public class Sandbox extends Game {

    public static void main(String[] args) {

        Sandbox p = new Sandbox(1280, 720, false, false);

    }

    public Sandbox(int WIDTH, int HEIGHT, boolean FULLSCREEN, boolean USE_P2D) {
        super(WIDTH, HEIGHT, FULLSCREEN, USE_P2D);
    }

    @Override
    public void setup() {
        Graphics.G().noStroke();
    }

    @Override
    public void updateLoop(double dt) {

    }

    @Override
    public void drawLoop() {

    }
}
