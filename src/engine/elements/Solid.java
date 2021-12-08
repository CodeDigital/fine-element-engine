package engine.elements;

import engine.math.ChanceThreshold;

public abstract class Solid extends Element{

    public Solid(String TYPE) {
        super(ElementData.MATTER_SOLID, TYPE);
        setChanceDissolve(
                new ChanceThreshold<Double>(
                        0.1,
                        0.025,
                        temp -> (temp > 100)
                )
        );
        setTypeDissolved(ElementData.STEAM);
    }

}
