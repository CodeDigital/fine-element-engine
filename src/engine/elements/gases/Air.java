package engine.elements.gases;

import engine.Colour;
import engine.elements.ElementData;
import engine.elements.Gas;
import engine.math.Chance;

/**
 * The Air element
 * Has reduced physics functionality to save processing power.
 */
public class Air extends Gas {

    /**
     * Instantiates Air.
     */
    public Air() {
        super(ElementData.AIR);

        // set the element's mass data
        setMassData(ElementData.AIR_DENSITY);

        // set the fluid's spread chance and range
        setFluidFSSSpread(new Chance(ElementData.AIR_FSS_SPREAD));
        setFluidFSSRange(ElementData.AIR_FSS_RANGE);

        // set the elements heat conductivity
        setConductivityHeat(ElementData.AIR_CONDUCTIVITY_HEAT);

        // set the colour of the element
        double r = 210 + 5 * Math.random();
        double g = 216 + 5 * Math.random();
        double b = 228 + 5 * Math.random();
        setColour(new Colour(r, g, b));
    }

    @Override
    public void stepPhysics(double dt) {propagateTemperature(dt);}

}
