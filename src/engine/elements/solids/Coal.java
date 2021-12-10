package engine.elements.solids;

import engine.Colour;
import engine.elements.ElementData;
import engine.elements.Powder;
import engine.math.Chance;
import engine.math.XMath;

/**
 * The Coal element
 * Has a high burn chance.
 */
public class Coal extends Powder {

    /**
     * Instantiates Coal.
     */
    public Coal() {
        super(ElementData.COAL);

        // set the element's mass data
        setMassData(ElementData.COAL_DENSITY);

        // set the powder's spread chance
        setFssSpread(new Chance(ElementData.COAL_FSS_SPREAD));

        // set the colour of the element
        double noiseRed = Math.random();
        double noiseGrey = Math.random();
        double r = XMath.map(noiseRed, 0, 1, 0, 75);
        double grey = XMath.map(noiseGrey, 0, 1, 0, 50);
        setColour(new Colour(r, grey, grey));
    }

}
