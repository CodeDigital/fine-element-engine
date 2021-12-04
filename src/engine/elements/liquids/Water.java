package engine.elements.liquids;

import engine.Colour;
import engine.elements.ElementData;
import engine.elements.Liquid;
import engine.math.Chance;
import engine.math.XMath;

public class Water extends Liquid {

    public Water(){
        super(ElementData.ELEMENT_WATER, ElementData.ELEMENT_WATER_FSS_SPREAD);
        setMassData(ElementData.ELEMENT_WATER_DENSITY);
        setFluidFSSRange(10);
        setFluidEqualise(new Chance(0.5));

        double noiseG = Math.random();
        double noiseB = Math.random();

        double g = XMath.map(noiseG, 0, 1, 100, 175);
        double b = XMath.map(noiseB, 0, 1, 200, 255);

        colour = new Colour(0, g, b);
    }

}
