package engine.elements.solids;

import engine.Colour;
import engine.elements.Element;
import engine.elements.ElementData;
import engine.elements.StaticElement;
import engine.math.XMath;

public class Wood extends StaticElement {

    public Wood(){
        super(ElementData.MATTER_SOLID, ElementData.ELEMENT_WOOD);
        setMassData(ElementData.ELEMENT_WOOD_DENSITY);

        double noise = Math.random();

        double r = XMath.map(noise, 0, 1, 60, 110);
        double g = XMath.map(noise, 0, 1, 19, 50);
        double b = XMath.map(noise, 0, 1, 0, 19);

        colour = new Colour(r, g, b);
    }

}
