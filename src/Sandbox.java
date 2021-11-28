import engine.Colour;
import engine.Game;
import engine.Graphics;
import engine.containers.Cell;
import engine.containers.FixedWorld;
import engine.math.V2D;
import engine.math.XMath;
import processing.core.PConstants;
import ui.ElementBar;

public class Sandbox extends Game {

    public ElementBar elementBar;
    private int brushSize = 10;

    public static void main(String[] args) {
        FixedWorld world = new FixedWorld(10,5);
        Sandbox s = new Sandbox(world, 1280, 720, false, false);
        s.start();
        world.setCellWidth();
    }

    public Sandbox(FixedWorld WORLD, int WIDTH, int HEIGHT, boolean FULLSCREEN, boolean USE_P2D) {
        super(WORLD, WIDTH, HEIGHT, FULLSCREEN, USE_P2D);
    }

    @Override
    public void start() {
        super.start();
        elementBar = new ElementBar();
    }

    @Override
    public void setup() {
        Graphics.G().noStroke();
    }

    @Override
    public void updateLoop(double dt) {
        WORLD.update(dt);

        if(Graphics.G().mousePressed) mousePressed();
    }

    @Override
    public void drawLoop() {
        Graphics.G().background(0);
        WORLD.render();
        elementBar.render();
        renderMouse();
        renderFPS();
    }

    public void renderMouse(){
        V2D ij = WORLD.getIJFromXY(new V2D(Graphics.G().mouseX, Graphics.G().mouseY)).asInt();
        Graphics.G().noFill();
        Graphics.G().strokeWeight(1);
        Graphics.G().stroke(Colour.WHITE.asInt());
        Graphics.G().rectMode(PConstants.CORNER);
        ij = ij.multiply(Cell.getWidth());
        Graphics.G().rect((float) ij.X, (float) ij.Y, (float) Cell.getWidth(), (float) Cell.getWidth());
    }

    void renderFPS(){
        Graphics.G().noStroke();
        Graphics.G().fill(0, 155, 0);
        Graphics.G().textAlign(PConstants.RIGHT);
        Graphics.G().text("# of Particles: " + WORLD.HEIGHT_CELLS * WORLD.WIDTH_CELLS, Graphics.G().width - 5, 15);
        Graphics.G().text("FPS: " + XMath.roundDP(Graphics.G().frameRate, 1), Graphics.G().width - 5, 35);
    }

    @Override
    public void mousePressed() {
        V2D ij = WORLD.getIJFromXY(new V2D(Graphics.G().mouseX, Graphics.G().mouseY));
        V2D ijStart = ij.add(-brushSize / 2);
        for(int i = 0; i < brushSize; i++){
            ij = ijStart;
            for(int j = 0; j < brushSize; j++){
                Cell c = WORLD.getCell(ij);
                if(c != null && elementBar.getActiveSpawnButton() != null) c.setElement(elementBar.getActiveSpawnButton().spawn());
                ij = ij.addY(1);
            }
            ijStart = ijStart.addX(1);
        }
    }

    @Override
    public void mouseClicked() {
        if(!elementBar.clickOn(new V2D(Graphics.G().mouseX, Graphics.G().mouseY))){
            mousePressed();
        }
    }
}
