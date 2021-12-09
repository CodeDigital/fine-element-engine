package engine.elements.gases;

import engine.Colour;
import engine.elements.ElementData;
import engine.elements.Gas;
import engine.math.Chance;

// TODO: Commenting
public class Air extends Gas {

    public Air() {
        super(ElementData.AIR);
        setFluidFSSSpread(
                new Chance(ElementData.AIR_FSS_SPREAD)
        );
        setMassData(ElementData.AIR_DENSITY);
        setFluidFSSRange(ElementData.AIR_FSS_RANGE);

        double r = 210 + 5 * Math.random();
        double g = 216 + 5 * Math.random();
        double b = 228 + 5 * Math.random();

        colour = new Colour(r, g, b);
    }

    @Override
    public void stepPre(double dt) {
    }

    @Override
    public void stepPhysics(double dt) {
    }

    @Override
    public void stepPost(double dt) {
    }
}
