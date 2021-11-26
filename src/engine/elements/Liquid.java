package engine.elements;

public abstract class Liquid extends Fluid{

    public Liquid(String TYPE) {
        super(ElementData.MATTER_LIQUID, TYPE);
    }
}
