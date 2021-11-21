package engine.elements;

import engine.Steppable;
import engine.containers.Cell;
import engine.elements.rules.Chance;
import engine.elements.rules.ChanceThreshold;
import engine.math.V2D;

public abstract class Element implements Steppable {

    public final String MATTER, TYPE;

    // Cell and showing info
    protected Cell cell;
    protected int colour;

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

    public Element(String MATTER, String TYPE, Cell cell) {
        this.MATTER = MATTER;
        this.TYPE = TYPE;
        this.cell = cell;
    }

    @Override
    public void stepPre(double dt) {

    }

    @Override
    public void stepPhysics(double dt) {

    }

    @Override
    public void stepFSS(double dt) {}

    @Override
    public void stepPost(double dt) {

    }

    private void setMassData(double density){
        this.density = density;
        mass = METRIC_VOLUME * density;
        iMass = (mass == 0 ? 0:1/mass);
    }

    public static Element spawn(String type, Cell container){
        switch (type){
            case ElementData.ELEMENT_SAND: return new Sand(container);
            default: return null;
        }
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public int getColour() {
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
