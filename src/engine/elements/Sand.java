package engine.elements;

import engine.Colour;
import engine.Debug;
import engine.math.XMath;

public class Sand extends Powder{

    public Sand() {
        super(ElementData.ELEMENT_SAND);

        double pNoiseR = Math.random();
        double pNoiseG = Math.random();
        double pNoiseB = Math.random();
        // println("sand set");

        double r = XMath.map(pNoiseR, 0, 1, 193, 194);
        double g = XMath.map(pNoiseG, 0, 1, 154, 178);
        double b = XMath.map(pNoiseB, 0, 1, 107, 128);


        colour = new Colour(r, g, b);
    }

}
