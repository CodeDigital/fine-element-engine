package ui;

import engine.Colour;
import engine.Graphics;
import engine.Renderable;
import engine.elements.Element;
import engine.math.V2D;
import processing.core.PConstants;

// TODO: Commenting
public class SpawnButton implements Renderable {

    public static final int FONT_SIZE = 16;
    public static final int WIDTH = 80;
    public static final int HEIGHT = 24;

    public final V2D LOCATION;
    public final String TYPE;
    public final Colour COLOUR;
    private boolean active = false;

    public SpawnButton(V2D LOCATION, String TYPE) {
        this.LOCATION = LOCATION;
        this.TYPE = TYPE;
        Element e = Element.spawn(TYPE);
        this.COLOUR = e.getColour();
    }

    public boolean isWithin(V2D point){
        return ((point.X >= LOCATION.X && point.X <= LOCATION.X + WIDTH) &&
                (point.Y >= LOCATION.Y && point.Y <= LOCATION.Y + HEIGHT));
    }

    public Element spawn(){
        return Element.spawn(TYPE);
    }

    public void render(){

        Graphics.G().rectMode(PConstants.CORNER);
        Graphics.G().strokeWeight(3);
        if(active){
            Graphics.G().stroke(Colour.WHITE.asInt());
        }else{

            if(isWithin(new V2D(Graphics.G().mouseX, Graphics.G().mouseY))){
                Graphics.G().stroke(new Colour(0, 100, 0).asInt());
            }else{
                Graphics.G().noStroke();
            }
        }
        Graphics.G().fill(COLOUR.asInt());
        Graphics.G().rect((float) LOCATION.X, (float) LOCATION.Y, WIDTH, HEIGHT);

        if(COLOUR.getDarkness() > 125){
            Graphics.G().fill(Colour.BLACK.asInt());
        }else{
            Graphics.G().fill(Colour.WHITE.asInt());
        }
        Graphics.G().textAlign(PConstants.CENTER, PConstants.CENTER);
        Graphics.G().textSize(FONT_SIZE);
        V2D textLocation = LOCATION.add(new V2D(WIDTH / 2, HEIGHT / 2));
        Graphics.G().text(TYPE, (float) textLocation.X, (float) textLocation.Y);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
