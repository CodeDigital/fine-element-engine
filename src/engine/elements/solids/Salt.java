package engine.elements.solids;

import engine.Colour;
import engine.elements.ElementData;
import engine.elements.Powder;
import engine.math.Chance;
import engine.math.XMath;

// TODO: Commenting
public class Salt extends Powder {

    public Salt() {
        super(ElementData.SALT);
        setMassData(ElementData.SALT_DENSITY);

        setFssSpread(new Chance(ElementData.SALT_FSS_SPREAD));

        double noiseGrey = Math.random();
        // println("sand set");

        double grey = XMath.map(noiseGrey, 0, 1, 230, 255);

        colour = new Colour(grey);
    }

}
