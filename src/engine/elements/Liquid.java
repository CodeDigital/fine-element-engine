package engine.elements;

import engine.math.Chance;
import engine.math.V2D;
import engine.math.XMath;

import java.util.ArrayList;
import java.util.Collections;

public abstract class Liquid extends Fluid{

    private Chance fssSpread = new Chance(1);

    public Liquid(String TYPE) {
        super(ElementData.MATTER_LIQUID, TYPE);
    }

    public Liquid(String TYPE, Chance fssSpread) {
        super(ElementData.MATTER_LIQUID, TYPE);
        this.fssSpread = fssSpread;
    }

    public Liquid(String TYPE, double fssSpreadProb) {
        super(ElementData.MATTER_LIQUID, TYPE);
        this.fssSpread = new Chance(fssSpreadProb);
    }

    @Override
    public void stepFSS(double dt) {

        assert cell != null;

        V2D fssDown = V2D.CARDINALS[2];
        V2D down = cell.applyDirection(fssDown).add(cell.LOCATION);
        V2D fssRight = cell.applyDirection(V2D.CARDINALS[1]);
        V2D right = fssRight.add(cell.LOCATION);
        V2D downRight = fssRight.add(down);
        V2D fssLeft = cell.applyDirection(V2D.CARDINALS[3]);
        V2D left = fssLeft.add(cell.LOCATION);
        V2D downLeft = fssLeft.add(down);

        ArrayList<V2D> order = new ArrayList<>();
        order.add(right);
        order.add(downRight);
        order.add(left);
        order.add(downLeft);
        Collections.shuffle(order);

        if(fssSpread.check()){
            order.add(down);
        }else{
            order.add(0, down);
        }

        for(V2D to:order){
            if(checkAndSwap(to)) return;
        }
    }
}
