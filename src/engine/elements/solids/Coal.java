package engine.elements.solids;

import engine.Colour;
import engine.elements.ElementData;
import engine.elements.Powder;
import engine.math.XMath;

public class Coal extends Powder {

    public Coal() {
        super(ElementData.ELEMENT_COAL, ElementData.ELEMENT_COAL_FSS_SPREAD);
        setMassData(ElementData.ELEMENT_COAL_DENSITY);

        double noiseGrey = Math.random();
        // println("sand set");

        double grey = XMath.map(noiseGrey, 0, 1, 10, 100);

        colour = new Colour(grey);
    }

}
