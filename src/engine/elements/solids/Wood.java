package engine.elements.solids;

import engine.Colour;
import engine.containers.Cell;
import engine.elements.ElementData;
import engine.elements.StaticElement;
import engine.math.ChanceThreshold;
import engine.math.XMath;

// TODO: Commenting
public class Wood extends StaticElement {

    public Wood(){
        super(ElementData.MATTER_SOLID, ElementData.WOOD);
        setMassData(ElementData.WOOD_DENSITY);

        setChanceDissolve(
                new ChanceThreshold<Double>(
                        0.1,
                        0.025,
                        temp -> (temp > 100)
                )
        );
        setTypeDissolved(ElementData.COAL);

        double noise = Math.random();

        double r = XMath.map(noise, 0, 1, 60, 110);
        double g = XMath.map(noise, 0, 1, 19, 50);
        double b = XMath.map(noise, 0, 1, 0, 19);

        colour = new Colour(r, g, b);
    }

    @Override
    public void setCell(Cell cell) {
        super.setCell(cell);

        if(cell.LOCATION.Y % 3 == 0){
            colour = colour.darken(Math.random() * 30);
        }
    }
}
