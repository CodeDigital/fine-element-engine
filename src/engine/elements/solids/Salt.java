package engine.elements.solids;

import engine.Colour;
import engine.elements.ElementData;
import engine.elements.Powder;
import engine.math.Chance;
import engine.math.XMath;

/**
 * The Ice element.
 */
public class Salt extends Powder {

    /**
     * Instantiates Salt.
     */
    public Salt() {
        super(ElementData.SALT);

        // set the element's mass data
        setMassData(ElementData.SALT_DENSITY);

        // set the powder's spread chance
        setFssSpread(new Chance(ElementData.SALT_FSS_SPREAD));

        // set the elements heat conductivity
        setConductivityHeat(ElementData.SALT_CONDUCTIVITY_HEAT);

        // set the colour of the element
        double noiseGrey = Math.random();
        double grey = XMath.map(noiseGrey, 0, 1, 230, 255);
        setColour(new Colour(grey));
    }

}
