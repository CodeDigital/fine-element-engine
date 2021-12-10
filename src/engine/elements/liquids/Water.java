package engine.elements.liquids;

import engine.Colour;
import engine.elements.ElementData;
import engine.elements.Liquid;
import engine.math.Chance;
import engine.math.XMath;

/**
 * The Water element.
 */
public class Water extends Liquid {

    /**
     * Instantiates Water.
     */
    public Water(){
        super(ElementData.WATER);

        // set the element's mass data
        setMassData(ElementData.WATER_DENSITY);

        // set the fluid's spread chance and range
        setFluidFSSSpread(new Chance(ElementData.WATER_FSS_SPREAD));
        setFluidFSSRange(ElementData.WATER_FSS_RANGE);

        // set the colour of the element
        double noiseG = Math.random();
        double noiseB = Math.random();
        double g = XMath.map(noiseG, 0, 1, 100, 175);
        double b = XMath.map(noiseB, 0, 1, 200, 255);
        setColour(new Colour(0, g, b));
    }

}
