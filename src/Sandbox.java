import engine.Game;
import engine.Graphics;
import engine.containers.FixedWorld;

public class Sandbox extends Game {

    public static void main(String[] args) {
        FixedWorld world = new FixedWorld(20,10);
        Sandbox s = new Sandbox(world, 1280, 720, false, false);
        s.start();
        world.setCellWidth();
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
        Graphics.G().background(0);
        WORLD.render();
    }
}
