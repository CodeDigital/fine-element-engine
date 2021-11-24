package engine.elements;

import engine.containers.Cell;
import engine.elements.rules.Chance;
import engine.math.V2D;

public abstract class Powder extends Solid{

    // Powder specific science info
    public boolean fssFreefalling = true;
    public Chance fssSpread = new Chance(1);

    public Powder(String TYPE, Cell cell) {
        super(TYPE, cell);
    }

    @Override
    public void stepFSS(double dt) {

        V2D fssDown = V2D.CARDINALS[2];
        V2D down = cell.getDirection().multiply(fssDown).add(cell.LOCATION);
        V2D fssRight = V2D.CARDINALS[1];
        V2D right = cell.getDirection().multiply(fssRight).add(cell.LOCATION);

        if(cell.canSwap(down)){
            cell.swap(down);
            return;
        }

        if(!fssFreefalling){
            if(cell.isUpdated()) fssFreefalling = true;
            return;
        }

        if(!fssSpread.check()) return;



    }
}
