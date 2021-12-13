package engine.elements.liquids;

import engine.Colour;
import engine.elements.ElementData;
import engine.elements.Liquid;
import engine.math.Chance;
import engine.math.XMath;

/**
 * The Water element.
 */
public class Lava extends Liquid {

    /**
     * Instantiates Water.
     */
    public Lava(){
        super(ElementData.LAVA);

        // set the element's mass data
        setMassData(ElementData.LAVA_DENSITY);

        // set the fluid's spread chance and range
        setFluidFSSSpread(new Chance(ElementData.LAVA_FSS_SPREAD));
        setFluidFSSRange(ElementData.LAVA_FSS_RANGE);

        // the lava must have a temperature above 1000 to exist.
        setTemperature(XMath.toKelvin(1000));

        // set the elements heat conductivity
        setConductivityHeat(ElementData.LAVA_CONDUCTIVITY_HEAT);

        // set the element's low temperature conversion
        setLowTemperature(ElementData.LAVA_TEMPERATURE_LOW);
        setLowTemperatureChance(ElementData.DEFAULT_TEMPERATURE_CHANCE);
        setLowTemperatureType(ElementData.LAVA_TEMPERATURE_LOW_TYPE);

        // set the colour of the element
        double noiseR = Math.random();
        double noiseG = Math.random();
        double r = XMath.map(noiseR, 0, 1, 150, 255);
        double g = XMath.map(noiseG, 0, 1, 0, 140);

        setColour(new Colour(r, g, 0));
    }

}
