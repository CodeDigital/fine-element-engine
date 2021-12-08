package engine.elements;

import engine.containers.Cell;
import engine.math.Chance;
import engine.math.ChanceThreshold;

public abstract class Gas extends Fluid{

    public Gas(String TYPE) {
        super(ElementData.MATTER_GAS, TYPE);
    }

}
