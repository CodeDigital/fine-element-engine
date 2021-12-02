package engine.elements;

import engine.math.Chance;
import engine.math.V2D;
import engine.math.XMath;

public abstract class Powder extends Solid{

    // Powder specific science info
    private boolean fssFreefalling = true;
    private Chance fssSpread = new Chance(1);

    public Powder(String TYPE) {
        super(TYPE);
    }

    public Powder(String TYPE, Chance fssSpread) {
        super(TYPE);
        this.fssSpread = fssSpread;
    }

    public Powder(String TYPE, double fssSpreadProb) {
        super(TYPE);
        this.fssSpread = new Chance(fssSpreadProb);
    }

    @Override
    public void stepFSS(double dt) {

        assert cell != null;

        V2D fssDown = V2D.CARDINALS[2];
        V2D down = cell.applyDirection(fssDown).add(cell.LOCATION);

        if(checkAndSwap(down)) return;

        if(!fssFreefalling){
            if(cell.isUpdated()) fssFreefalling = true;
            return;
        }

        if(!fssSpread.check()) return;

        V2D fssRight = V2D.CARDINALS[1];
        V2D downRight = cell.applyDirection(fssRight).add(down);
        V2D fssLeft = V2D.CARDINALS[3];
        V2D downLeft = cell.applyDirection(fssLeft).add(down);

        if(XMath.randomBoolean()){
            if(checkAndSwap(downLeft)) return;
            if(checkAndSwap(downRight)) return;
        }else{
            if(checkAndSwap(downRight)) return;
            if(checkAndSwap(downLeft)) return;
        }

    }
}
