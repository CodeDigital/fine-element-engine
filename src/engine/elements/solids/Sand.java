package engine.elements.solids;

import engine.Colour;
import engine.Debug;
import engine.elements.ElementData;
import engine.elements.Powder;
import engine.math.XMath;

public class Sand extends Powder {

    public Sand() {
        super(ElementData.ELEMENT_SAND, ElementData.ELEMENT_SAND_FSS_SPREAD);
        setMassData(ElementData.ELEMENT_SAND_DENSITY);

        double noiseR = Math.random();
        double noiseG = Math.random();
        double noiseB = Math.random();
        // println("sand set");

        double r = XMath.map(noiseR, 0, 1, 193, 194);
        double g = XMath.map(noiseG, 0, 1, 154, 178);
        double b = XMath.map(noiseB, 0, 1, 107, 128);

        colour = new Colour(r, g, b);
    }

}