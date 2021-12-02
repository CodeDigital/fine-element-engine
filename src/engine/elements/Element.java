package engine.elements;

import engine.Colour;
import engine.Steppable;
import engine.containers.Cell;
import engine.elements.gases.Air;
import engine.elements.liquids.Water;
import engine.elements.solids.Coal;
import engine.elements.solids.Salt;
import engine.elements.solids.Sand;
import engine.elements.solids.Wood;
import engine.math.ChanceThreshold;
import engine.math.V2D;
import engine.math.XMath;

import java.util.ArrayList;

public abstract class Element implements Steppable {

    public final String MATTER, TYPE;

    // Cell and showing info
    protected Cell cell;
    protected Colour colour;

    // Translational Info
    protected V2D acceleration = V2D.ZERO;
    protected V2D velocity = V2D.ZERO;

    // Scientific Info
    public static final double METRIC_WIDTH = 0.0625; // metres
    public static final double METRIC_VOLUME = METRIC_WIDTH * METRIC_WIDTH * 0.25; // metres^3

    // Mass Info
    protected double density;
    protected double mass;
    protected double iMass;
    protected double restitution = 0.5;

    // Other Science Info
    protected boolean isStatic = false;
    protected boolean isCharged = false;
    protected double temperature = 24; // in Celsius
    protected double gravityEffect = 1;
    protected double conductivityHeat = 0;
    protected double conductivityElectrical = 0;
    protected double electricalCharge = 0;
    protected double frictionDynamic = 0;
    protected double frictionStatic = 0;

    // Other states
    protected String type_melted;
    protected ChanceThreshold<Double> chance_melt;

    protected String type_evaporated;
    protected ChanceThreshold<Double> chance_evaporate;

    protected String type_burned;
    protected ChanceThreshold<Double> chance_burn;

    public Element(String MATTER, String TYPE) {
        this.MATTER = MATTER;
        this.TYPE = TYPE;
    }

    @Override
    public void stepPre(double dt) {
        velocity = velocity.add(cell.getTotalForce().multiply(dt));
    }

    @Override
    public void stepPhysics(double dt) {
        V2D newLocation = cell.LOCATION.add(velocity.multiply(dt / Element.METRIC_WIDTH));
        for(V2D step: XMath.bresenham(cell.LOCATION, newLocation)){
            if(!checkAndSwap(step)) return;
        }
    }

    @Override
    public void stepPost(double dt) {

    }

    public boolean checkAndSwap(V2D to){
        if(cell.canSwap(to)){
            cell.swap(to);
            return true;
        }
        return false;
    }

    protected void setMassData(double density){
        this.density = density;
        mass = METRIC_VOLUME * density;
        iMass = (mass == 0 ? 0:1/mass);
    }

    public static Element spawn(String type){
        switch (type){
            case ElementData.ELEMENT_SAND: return new Sand();
            case ElementData.ELEMENT_AIR: return new Air();
            case ElementData.ELEMENT_SALT: return new Salt();
            case ElementData.ELEMENT_COAL: return new Coal();
            case ElementData.ELEMENT_WATER: return new Water();
            case ElementData.ELEMENT_WOOD: return new Wood();
            default: return null;
        }
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public Colour getColour() {
        return colour;
    }

    public V2D getAcceleration() {
        return acceleration;
    }

    public V2D getVelocity() {
        return velocity;
    }

    public double getDensity() {
        return density;
    }

    public double getMass() {
        return mass;
    }

    public double getiMass() {
        return iMass;
    }

    public double getRestitution() {
        return restitution;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public boolean isCharged() {
        return isCharged;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getGravityEffect() {
        return gravityEffect;
    }

    public double getConductivityHeat() {
        return conductivityHeat;
    }

    public double getConductivityElectrical() {
        return conductivityElectrical;
    }

    public double getElectricalCharge() {
        return electricalCharge;
    }

    public double getFrictionDynamic() {
        return frictionDynamic;
    }

    public double getFrictionStatic() {
        return frictionStatic;
    }

    public String getType_melted() {
        return type_melted;
    }

    public ChanceThreshold<Double> getChance_melt() {
        return chance_melt;
    }

    public String getType_evaporated() {
        return type_evaporated;
    }

    public ChanceThreshold<Double> getChance_evaporate() {
        return chance_evaporate;
    }

    public String getType_burned() {
        return type_burned;
    }

    public ChanceThreshold<Double> getChance_burn() {
        return chance_burn;
    }
}
