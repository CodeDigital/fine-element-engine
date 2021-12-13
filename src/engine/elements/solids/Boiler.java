package engine.elements.solids;

import engine.Colour;
import engine.elements.ElementData;
import engine.elements.StaticElement;
import engine.math.XMath;

/**
 * The StaticHeater element.
 */
public class Boiler extends StaticElement {

    private static final double HEATER_RATE = 10;

    /**
     * Instantiates StaticHeater.
     */
    public Boiler(){
        super(ElementData.MATTER_SOLID, ElementData.BOILER);

        // set the element's mass data
        setMassData(ElementData.BOILER_DENSITY);

        // set the colour of the element
        double noiseR = Math.random();
        double r = XMath.map(noiseR, 0, 1, 200, 255);

        setColour(new Colour(r, 50, 50));
    }

    @Override
    public void propagateTemperature(double dt) {
        setTemperature(XMath.toKelvin(110));
        super.propagateTemperature(dt);
    }
}
