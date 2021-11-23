import engine.Game;
import engine.Graphics;
import engine.containers.FixedWorld;
import engine.containers.Grid;

public class Sandbox extends Game {

    public static void main(String[] args) {
        FixedWorld world = new FixedWorld(10,10);
        Sandbox s = new Sandbox(world, 1280, 720, false, false);
        s.start();
    }

    public Sandbox(FixedWorld WORLD, int WIDTH, int HEIGHT, boolean FULLSCREEN, boolean USE_P2D) {
        super(WORLD, WIDTH, HEIGHT, FULLSCREEN, USE_P2D);
    }

    @Override
    public void setup() {
        Graphics.G().noStroke();
    }

    @Override
    public void updateLoop(double dt) {
        WORLD.update(dt);
    }

    @Override
    public void drawLoop() {
        WORLD.render();
    }
}
