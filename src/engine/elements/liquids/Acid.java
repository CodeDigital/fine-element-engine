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

/**
 * The Acid element.
 * Can burn through elements with a dissolve rate above 0.
 */
public class Acid extends Liquid {

    /**
     * Instantiates Acid.
     */
    public Acid(){
        super(ElementData.ACID);

        // set the element's mass data
        setMassData(ElementData.ACID_DENSITY);

        // set the fluid's spread chance and range
        setFluidFSSSpread(new Chance(ElementData.ACID_FSS_SPREAD));
        setFluidFSSRange(ElementData.ACID_FSS_RANGE);

        // set the element's high temperature conversion
        setHighTemperature(ElementData.WATER_TEMPERATURE_HIGH);
        setHighTemperatureChance(ElementData.DEFAULT_TEMPERATURE_CHANCE);
        setHighTemperatureType(ElementData.WATER_TEMPERATURE_HIGH_TYPE);

        // set the colour of the element
        double noiseG = Math.random();
        double noiseB = Math.random();
        double g = XMath.map(noiseG, 0, 1, 100, 255);
        double b = XMath.map(noiseB, 0, 1, 10, 50);
        setColour(new Colour(0, g, b));
    }

    @Override
    public void stepPost(double dt) {
        dissolveSurrounding(dt); // perform acid's functionality
        super.stepPost(dt);
    }

    /**
     * Dissolve surrounding.
     *
     * @param dt time delta
     */
    public void dissolveSurrounding(double dt){

        // iterate over the cell's borders in a random order.
        ArrayList<Cell> borders = getCell().CELL_BORDERS;
        Collections.shuffle(borders);

        for(Cell c:borders){
            if(c == null) continue;
            Element e = c.getElement();
            if(e == null) continue;

            // check if can be dissolved
            double tempAvg = (getTemperature() + e.getTemperature()) / 2;
            if(tempAvg > e.getDissolveTemperature()){
                if(e.getDissolveChance().check()){

                    // get the temperature change.
                    double dTemp = (getConductivityHeat() + e.getConductivityHeat()) / 2;
                    dTemp *= dt;

                    // propagate temperature
                    double newTemp = e.getTemperature() - dTemp;
                    setTemperature(getTemperature() + dTemp);

                    // set the dissolved element.
                    c.setElement(Element.spawn(e.getDissolveType()));
                    c.getElement().setTemperature(newTemp);
                    return;
                }
            }
        }
    }
}
