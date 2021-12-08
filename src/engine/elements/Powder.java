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

    @Override
    public void stepFSS(double dt) {
        final boolean inFSS = true;
        assert cell != null;

        V2D fssDown = V2D.CARDINALS[2];
        V2D down = cell.applyDirection(fssDown).add(cell.LOCATION);

        if(steppingCheckAndSwap(down, inFSS)) return;

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
            if(steppingCheckAndSwap(downLeft, inFSS)) return;
            if(steppingCheckAndSwap(downRight, inFSS)) return;
        }else{
            if(steppingCheckAndSwap(downRight, inFSS)) return;
            if(steppingCheckAndSwap(downLeft, inFSS)) return;
        }

    }

    public Chance getFssSpread() {
        return fssSpread;
    }

    public void setFssSpread(Chance fssSpread) {
        this.fssSpread = fssSpread;
    }

    public boolean isFssFreefalling() {
        return fssFreefalling;
    }

    public void setFssFreefalling(boolean fssFreefalling) {
        this.fssFreefalling = fssFreefalling;
    }
}
