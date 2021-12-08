package engine.elements.liquids;

import engine.Colour;
import engine.elements.ElementData;
import engine.elements.Liquid;
import engine.math.Chance;
import engine.math.XMath;

public class Water extends Liquid {

    public Water(){
        super(ElementData.WATER);
        setMassData(ElementData.WATER_DENSITY);

        setFluidFSSSpread(new Chance(ElementData.WATER_FSS_SPREAD));
        setFluidFSSRange(ElementData.WATER_FSS_RANGE);

        double noiseG = Math.random();
        double noiseB = Math.random();

        double g = XMath.map(noiseG, 0, 1, 100, 175);
        double b = XMath.map(noiseB, 0, 1, 200, 255);

        colour = new Colour(0, g, b);
    }

}
