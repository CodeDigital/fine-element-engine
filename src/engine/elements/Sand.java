package engine.elements;

import engine.Colour;
import engine.Graphics;
import engine.containers.Cell;

public class Sand extends Powder{

    public Sand(Cell cell) {

        super(ElementData.ELEMENT_SAND, cell);
        colour = new Colour(100, 100, 100);

    }

}
