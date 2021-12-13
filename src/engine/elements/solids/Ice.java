package engine.elements.solids;

import engine.Colour;
import engine.elements.ElementData;
import engine.elements.Powder;
import engine.math.Chance;
import engine.math.XMath;

/**
 * The Ice element.
 */
public class Ice extends Powder {
    /**
     * Instantiates Ice.
     */
    public Ice() {
        super(ElementData.ICE);

        // set the element's mass data
        setMassData(ElementData.ICE_DENSITY);

        // set the powder's spread chance
        setFssSpread(new Chance(ElementData.ICE_FSS_SPREAD));

        // the ice must have a temperature below 0 to exist.
        setTemperature(XMath.toKelvin(-10));

        // set the elements heat conductivity
        setConductivityHeat(ElementData.ICE_CONDUCTIVITY_HEAT);

        // set the element's high temperature conversion
        setHighTemperature(ElementData.ICE_TEMPERATURE_HIGH);
        setHighTemperatureChance(ElementData.DEFAULT_TEMPERATURE_CHANCE);
        setHighTemperatureType(ElementData.ICE_TEMPERATURE_HIGH_TYPE);

        // set the colour of the element
        double noiseG = Math.random();
        double noiseB = Math.random();
        double g = XMath.map(noiseG, 0, 1, 240, 250);
        double b = XMath.map(noiseB, 0, 1, 250, 255);
        setColour(new Colour(200, g, b));
    }
}
