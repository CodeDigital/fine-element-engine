package engine.elements;

import engine.containers.Cell;
import engine.math.V2D;
import engine.math.XMath;

public abstract class Reaction extends Element{

    private Element source;

    public Reaction(String TYPE) {
        super(ElementData.MATTER_REACTION, TYPE);
    }

    @Override
    public void stepPhysics(double dt){
        propagateTemperature(dt);
        react(dt);
    }

    @Override
    public void stepFSS(double dt) {
        final boolean inFSS = true;

        Cell cell = getCell();
        assert cell != null;

        V2D fssDown = V2D.CARDINALS[2];
        V2D down = cell.applyDirection(fssDown).add(cell.LOCATION);

        if(steppingCheckAndSwap(down, inFSS)) return;
    }

    public abstract void react(double dt);

    public Element getSource() {
        return source;
    }

    public void setSource(Element source) {
        this.source = source;
    }
}
