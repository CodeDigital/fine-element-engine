package engine;

public abstract class Game {
    private final double FPS = 60;
    private final double DT = 1 / FPS;
    private double frameStart;
    private double accumulator = 0;
    private double timeDilation = 1;

    public Game() {
        this.frameStart = frameStart;
    }

    public void draw(){

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
