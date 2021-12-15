package engine.elements.solids;

import engine.Colour;
import engine.containers.Cell;
import engine.elements.ElementData;
import engine.elements.StaticElement;
import engine.math.XMath;

/**
 * The Wood element.
 */
public class Wood extends StaticElement {

    /**
     * Instantiates Wood.
     */
    public Wood(){
        super(ElementData.MATTER_SOLID, ElementData.WOOD);

        // set the element's mass data
        setMassData(ElementData.WOOD_DENSITY);

        setConductivityHeat(ElementData.WOOD_CONDUCTIVITY_HEAT);

        setBurnTemperature(ElementData.WOOD_BURN_TEMPERATURE);
        setBurnChance(ElementData.DEFAULT_BURN_CHANCE);
        setBurnType(ElementData.WOOD_BURN_TYPE);

        // set the colour of the element
        double noise = Math.random();
        double r = XMath.map(noise, 0, 1, 60, 110);
        double g = XMath.map(noise, 0, 1, 19, 50);
        double b = XMath.map(noise, 0, 1, 0, 19);
        setColour(new Colour(r, g, b));
    }

    @Override
    public void setCell(Cell cell) {
        super.setCell(cell);

        // redo the colour to add horizontal streaks for wood.
        if(cell.LOCATION.Y % 3 == 0){
            setColour(getColour().darken(Math.random() * 30));
        }
    }
}
