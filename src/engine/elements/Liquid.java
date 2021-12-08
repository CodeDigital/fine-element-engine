package engine.elements;

import engine.math.ChanceThreshold;
import engine.math.XMath;

public abstract class Liquid extends Fluid{

    public Liquid(String TYPE) {
        super(ElementData.MATTER_LIQUID, TYPE);
    }
}
