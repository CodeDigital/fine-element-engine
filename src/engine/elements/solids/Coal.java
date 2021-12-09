package engine.elements.solids;

import engine.Colour;
import engine.elements.ElementData;
import engine.elements.Powder;
import engine.math.Chance;
import engine.math.XMath;

// TODO: Commenting
public class Coal extends Powder {

    public Coal() {
        super(ElementData.COAL);
        setMassData(ElementData.COAL_DENSITY);

        setFssSpread(new Chance(ElementData.COAL_FSS_SPREAD));

        double noiseGrey = Math.random();
        // println("sand set");

        double grey = XMath.map(noiseGrey, 0, 1, 10, 100);

        colour = new Colour(grey);
    }

}
