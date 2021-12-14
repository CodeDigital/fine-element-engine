package engine.elements.reactions;

import engine.Colour;
import engine.elements.Element;
import engine.elements.ElementData;
import engine.elements.Reaction;
import engine.elements.solids.Coal;
import engine.math.XMath;

public class Fire extends Reaction {

    public Fire() {
        super(ElementData.FIRE);

        setSource(ElementData.spawn(ElementData.AIR));

        // set the colour of the element
        double noiseR = Math.random();
        double noiseG = Math.random();
        double r = XMath.map(noiseR, 0, 1, 150, 255);
        double g = XMath.map(noiseG, 0, 1, 0, 150);

        setColour(new Colour(r, g, 0));
    }

    @Override
    public void react(double dt) {

    }

    @Override
    public void setSource(Element source) {
        super.setSource(source);
        setMassData(source.getDensity());
    }
}
