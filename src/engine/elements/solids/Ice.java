package engine.elements.solids;

import engine.Colour;
import engine.elements.ElementData;
import engine.elements.Powder;
import engine.math.Chance;
import engine.math.ChanceThreshold;
import engine.math.XMath;

// TODO: Commenting
public class Ice extends Powder {
    public Ice() {
        super(ElementData.ICE);
        setMassData(ElementData.ICE_DENSITY);

        setFssSpread(new Chance(ElementData.ICE_FSS_SPREAD));

        setTemperature(-10);

        setChanceHighTemperature(
            new ChanceThreshold<Double>(
                ElementData.WATER_LOW_TEMPERATURE_CHANCE,
                0,
                temp -> (temp > ElementData.WATER_LOW_TEMPERATURE)
            )
        );
        setTypeHighTemperature(ElementData.WATER);

        double noiseG = Math.random();
        double noiseB = Math.random();
        double g = XMath.map(noiseG, 0, 1, 240, 250);
        double b = XMath.map(noiseB, 0, 1, 250, 255);

        colour = new Colour(200, g, b);
    }
}
