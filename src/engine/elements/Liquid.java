package engine.elements;

import engine.math.Chance;

public abstract class Liquid extends Fluid{

    public Liquid(String TYPE) {
        super(ElementData.MATTER_LIQUID, TYPE);
    }

    public Liquid(String TYPE, double fluidFSSSpreadProbability) {
        super(ElementData.MATTER_LIQUID, TYPE, fluidFSSSpreadProbability);
    }

    public Liquid(String TYPE, Chance fluidFSSSpread) {
        super(ElementData.MATTER_LIQUID, TYPE, fluidFSSSpread);
    }
}
