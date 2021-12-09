package engine.elements.gases;

import engine.Colour;
import engine.elements.ElementData;
import engine.elements.Gas;
import engine.math.Chance;
import engine.math.ChanceThreshold;
import engine.math.XMath;

// TODO: Commenting
public class Steam extends Gas {

    public Steam() {
        super(ElementData.STEAM);
        setMassData(ElementData.STEAM_DENSITY);

        setFluidFSSSpread(
                new Chance(ElementData.STEAM_FSS_SPREAD)
        );
        setFluidFSSRange(ElementData.STEAM_FSS_RANGE);

        setTemperature(110);

        setChanceLowTemperature(new ChanceThreshold<Double>(
            0.5,
                0,
                temp -> temp < 100
        ));
        setTypeLowTemperature(ElementData.WATER);

        double noiseG = Math.random();
        double noiseB = Math.random();
        double noiseA = Math.random();

        double g = XMath.map(noiseG, 0, 1, 210, 225);
        double b = XMath.map(noiseB, 0, 1, 250, 255);
        double a = XMath.map(noiseA, 0, 1, 100, 200);

//        colour = new Colour(0, g, b, a);
        colour = new Colour(200, g, b);

    }

    @Override
    public void stepPre(double dt) {
    }

    @Override
    public void stepPhysics(double dt) {
    }
}
