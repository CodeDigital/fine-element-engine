package engine.elements.liquids;

import engine.Colour;
import engine.containers.Cell;
import engine.elements.Element;
import engine.elements.ElementData;
import engine.elements.Liquid;
import engine.math.Chance;
import engine.math.V2D;
import engine.math.XMath;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class Acid extends Liquid {

    private double acidTransferRate = 10;

    public Acid(){
        super(ElementData.ACID);
        setMassData(ElementData.ACID_DENSITY);

        setFluidFSSSpread(
                new Chance(ElementData.ACID_FSS_SPREAD)
        );
        setFluidFSSRange(ElementData.ACID_FSS_RANGE);

        double noiseG = Math.random();
        double noiseB = Math.random();

        double g = XMath.map(noiseG, 0, 1, 100, 255);
        double b = XMath.map(noiseB, 0, 1, 10, 50);

        colour = new Colour(0, g, b);
    }

    @Override
    public void stepPost(double dt) {
        super.stepPost(dt);
        dissolveSurrounding(dt);
    }

    public void dissolveSurrounding(double dt){

        ArrayList<Cell> borders = cell.CELL_BORDERS;
        Collections.shuffle(borders);

        for(Cell c:borders){
            if(c == null) continue;
            Element e = c.getElement();
            if(e == null) continue;

            if(e.getChanceDissolve().check(e.getTemperature())){
                double newTemp = e.getTemperature() - acidTransferRate * dt;
                temperature += acidTransferRate * dt;
                c.setElement(Element.spawn(e.getTypeDissolved()));
                c.getElement().setTemperature(newTemp);
                return;
            }
        }
    }
}
