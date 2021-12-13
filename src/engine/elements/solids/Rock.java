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
public class Rock extends Powder {

    /**
     * Instantiates Coal.
     */
    public Rock() {
        super(ElementData.ROCK);

        // set the element's mass data
        setMassData(ElementData.ROCK_DENSITY);

        // set the powder's spread chance
        setFssSpread(new Chance(ElementData.ROCK_FSS_SPREAD));

        // set the elements heat conductivity
        setConductivityHeat(ElementData.ROCK_CONDUCTIVITY_HEAT);

        // set the colour of the element
        double noiseGrey = Math.random();
        double grey = XMath.map(noiseGrey, 0, 1, 0, 100);
        setColour(new Colour(grey, grey, grey));
    }

}
