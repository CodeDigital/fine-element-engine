package ui;

import engine.Colour;
import engine.Graphics;
import engine.containers.Cell;
import engine.containers.Chunk;
import engine.containers.Grid;
import engine.math.V2D;
import engine.math.XMath;
import processing.core.PConstants;

public class Brush {

    private static double brushSize = 10;

    public static void render(Grid world){
        V2D ij = world.toCoordinate(new V2D(Graphics.G().mouseX, Graphics.G().mouseY)).asInt();
        Graphics.G().noFill();
        Graphics.G().strokeWeight(1);
        Graphics.G().stroke(Colour.WHITE.asInt());
        Graphics.G().rectMode(PConstants.CORNER);
//        ij = ij.multiply(Cell.getWidth());
//        Graphics.G().rect((float) ij.X, (float) ij.Y, (float) Cell.getWidth(), (float) Cell.getWidth());

//        ij = world.toCoordinate(new V2D(Graphics.G().mouseX, Graphics.G().mouseY)).asInt();
        V2D ijStart = ij.add((int) (-brushSize / 2)).multiply(Cell.getWidth()).asInt();
//        Graphics.G().stroke(Colour.BLACK.asInt());
        Graphics.G().rect((float) ijStart.X, (float) ijStart.Y,
                (float) (brushSize * Cell.getWidth()),
                (float) (brushSize * Cell.getWidth()));
    }

    public static double getBrushSize() {
        return brushSize;
    }

    public static void setBrushSize(double brushSize) {
        Brush.brushSize = XMath.clamp(brushSize, 1, Chunk.WIDTH * 2);

    }
}
