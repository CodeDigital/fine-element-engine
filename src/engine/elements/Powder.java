package engine.elements;

import engine.Debug;
import engine.elements.rules.Chance;
import engine.math.V2D;
import engine.math.XMath;

public abstract class Powder extends Solid{

    // Powder specific science info
    public boolean fssFreefalling = true;
    public Chance fssSpread = new Chance(1);

    public Powder(String TYPE) {
        super(TYPE);
    }

    @Override
    public void stepFSS(double dt) {

        assert cell != null;

        V2D fssDown = V2D.CARDINALS[2];
        V2D down = cell.getDirection().multiply(fssDown).add(cell.LOCATION);

        if(checkAndSwap(down)) return;

        if(!fssFreefalling){
            if(cell.isUpdated()) fssFreefalling = true;
            return;
        }

        if(!fssSpread.check()) return;

        V2D fssRight = V2D.CARDINALS[1];
        V2D downRight = cell.getDirection().multiply(fssRight).add(down);
        V2D fssLeft = V2D.CARDINALS[3];
        V2D downLeft = cell.getDirection().multiply(fssLeft).add(down);

        if(XMath.randomBoolean()){
            if(checkAndSwap(downLeft)) return;
            if(checkAndSwap(downRight)) return;
        }else{
            if(checkAndSwap(downRight)) return;
            if(checkAndSwap(downLeft)) return;
        }

    }
}
