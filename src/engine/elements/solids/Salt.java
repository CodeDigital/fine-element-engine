package engine.elements.solids;

import engine.Colour;
import engine.elements.ElementData;
import engine.elements.Powder;
import engine.math.XMath;

public class Salt extends Powder {

    public Salt() {
        super(ElementData.ELEMENT_SALT, ElementData.ELEMENT_SALT_FSS_SPREAD);
        setMassData(ElementData.ELEMENT_SALT_DENSITY);

        double noiseGrey = Math.random();
        // println("sand set");

        double grey = XMath.map(noiseGrey, 0, 1, 230, 255);

        colour = new Colour(grey);
    }

}
