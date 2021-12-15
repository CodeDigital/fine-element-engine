package engine.elements.reactions;

import engine.Colour;
import engine.containers.Cell;
import engine.elements.Element;
import engine.elements.ElementData;
import engine.elements.Reaction;
import engine.math.XMath;

import java.util.ArrayList;
import java.util.Collections;

public class Fire extends Reaction {

    public Fire() {
        super(ElementData.FIRE);

        setSource(ElementData.spawn(ElementData.AIR));

        // set the colour of the element
        double noiseR = Math.random();
        double noiseG = Math.random();
        double r = XMath.map(noiseR, 0, 1, 150, 255);
        double g = XMath.map(noiseG, 0, 1, 0, 150);

        setColour(new Colour(r, g, 0));
    }

    @Override
    public void react(double dt) {

        // increase newtemperature and burn fuel/self
        setMass(getMass() - dt);
        setNewTemperature(getNewTemperature() + dt * getSource().getConductivityHeat());

        if(getTemperature() < getSource().getBurnTemperature()){
            getSource().setMass(getMass());
            getSource().setTemperature(getNewTemperature());
            getCell().setElement(getSource());
        }

        if(getMass() <= 0){
            getCell().setElement(ElementData.spawn(getSource().TYPE));
            return;
        }

        // iterate over the cell's borders in a random order.
        ArrayList<Cell> borders = getCell().CELL_BORDERS;
        Collections.shuffle(borders);

        for(Cell c:borders){
            if(c == null) continue;
            Element e = c.getElement();
            if(e == null) continue;
            if(e.TYPE == TYPE) continue;

            if(canBurn(e)){
                Fire f = new Fire();
                f.setTemperature(e.getNewTemperature());
                f.setSource(e);
                c.setElement(f);
            }

        }
    }

    public boolean canBurn(Element e){
        if(e.getTemperature() > e.getBurnTemperature()){
            return e.getBurnChance().check();
        }
        return false;
    }

    @Override
    public void setSource(Element source) {
        super.setSource(source);
        setMassData(source.getDensity());
        setMass(source.getMass());
        setConductivityHeat(source.getConductivityHeat());
    }
}
