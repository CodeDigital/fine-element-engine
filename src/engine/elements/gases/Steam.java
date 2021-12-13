package engine.elements.gases;

import engine.Colour;
import engine.elements.ElementData;
import engine.elements.Gas;
import engine.math.Chance;
import engine.math.XMath;

/**
 * The Steam element
 */
public class Steam extends Gas {

    /**
     * Instantiates Steam.
     */
    public Steam() {
        super(ElementData.STEAM);

        // set the element's mass data
        setMassData(ElementData.STEAM_DENSITY);

        // set the fluid's spread chance and range
        setFluidFSSSpread(new Chance(ElementData.STEAM_FSS_SPREAD));
        setFluidFSSRange(ElementData.STEAM_FSS_RANGE);

        // the steam must have a temperature above 100 to exist.
        setTemperature(XMath.toKelvin(110));

        // set the elements heat conductivity
        setConductivityHeat(ElementData.STEAM_CONDUCTIVITY_HEAT);

        // set the element's low temperature conversion
        setLowTemperature(ElementData.STEAM_TEMPERATURE_LOW);
        setLowTemperatureChance(ElementData.DEFAULT_TEMPERATURE_CHANCE);
        setLowTemperatureType(ElementData.STEAM_TEMPERATURE_LOW_TYPE);

        // set the colour of the element
        double noiseG = Math.random();
        double noiseB = Math.random();
        double noiseA = Math.random();
        double g = XMath.map(noiseG, 0, 1, 210, 225);
        double b = XMath.map(noiseB, 0, 1, 250, 255);
        double a = XMath.map(noiseA, 0, 1, 100, 200);
        setColour(new Colour(210, g, b));

    }
}
