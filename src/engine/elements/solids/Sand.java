package engine.elements.solids;

import engine.Colour;
import engine.Debug;
import engine.elements.ElementData;
import engine.elements.Powder;
import engine.math.Chance;
import engine.math.XMath;

/**
 * The Sand element.
 */
public class Sand extends Powder {

    /**
     * Instantiates Sand.
     */
    public Sand() {
        super(ElementData.SAND);

        // set the element's mass data
        setMassData(ElementData.SAND_DENSITY);

        // set the powder's spread chance
        setFssSpread(new Chance(ElementData.SAND_FSS_SPREAD));

        // set the colour of the element
        double noiseR = Math.random();
        double noiseG = Math.random();
        double noiseB = Math.random();
        double r = XMath.map(noiseR, 0, 1, 193, 194);
        double g = XMath.map(noiseG, 0, 1, 154, 178);
        double b = XMath.map(noiseB, 0, 1, 107, 128);
        setColour(new Colour(r, g, b));
    }

}
