package engine;

import engine.containers.Grid;
import engine.math.XMath;

public abstract class Game {
    public final Grid WORLD;
    private final double FPS = 60;
    private final double DT = 1 / FPS;
    private double frameStart;
    private double accumulator = 0;
    private double timeDilation = 1;
    public final int WIDTH, HEIGHT;
    public final boolean FULLSCREEN, USE_P2D;

    public Game(Grid world, int WIDTH, int HEIGHT,
                boolean FULLSCREEN, boolean USE_P2D) {
        this.WORLD = world;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.FULLSCREEN = FULLSCREEN;
        this.USE_P2D = USE_P2D;
    }

    public void start(){
        Graphics.start(this, WIDTH, HEIGHT, FULLSCREEN, USE_P2D);
    }

    public abstract void setup();

    public void loop(){

        double currFrameStart = getCurrentTime();
        accumulator += currFrameStart - frameStart;
        accumulator = XMath.clamp(accumulator, 0, 0.2);
        frameStart = currFrameStart;

        // update in steps of DT
        while(accumulator > DT){
            updateLoop(DT);
            accumulator -= (DT / timeDilation);
        }

        drawLoop();
    }

    public abstract void updateLoop(double dt);
    public abstract void drawLoop();

    public double getCurrentTime(){
        return (double) System.currentTimeMillis() / 1000;
    }

    public double getFPS() {
        return FPS;
    }

    public double getDT() {
        return DT;
    }

    public double getAccumulator() {
        return accumulator;
    }

    public double getTimeDilation() {
        return timeDilation;
    }

    public void setTimeDilation(double timeDilation) {
        this.timeDilation = timeDilation;
    }
}
