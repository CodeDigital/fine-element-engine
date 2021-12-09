package engine.elements;

import engine.Colour;
import engine.Steppable;
import engine.containers.Cell;
import engine.elements.gases.Air;
import engine.elements.gases.Steam;
import engine.elements.liquids.Acid;
import engine.elements.liquids.Water;
import engine.elements.solids.*;
import engine.math.Chance;
import engine.math.ChanceThreshold;
import engine.math.V2D;
import engine.math.XMath;

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
    protected String typeLowTemperature = ElementData.AIR;
    protected ChanceThreshold<Double> chanceLowTemperature = ChanceThreshold.ALWAYS_FALSE;

    protected String typeHighTemperature = ElementData.AIR;
    protected ChanceThreshold<Double> chanceHighTemperature = ChanceThreshold.ALWAYS_FALSE;

    protected String typeBurned = ElementData.AIR;
    protected ChanceThreshold<Double> chanceBurn = ChanceThreshold.ALWAYS_FALSE;

    protected String typeDissolved = ElementData.AIR;
    protected ChanceThreshold<Double> chanceDissolve = ChanceThreshold.ALWAYS_FALSE;

    private boolean updatedInFSS = false;

    public Element(String MATTER, String TYPE) {
        this.MATTER = MATTER;
        this.TYPE = TYPE;
    }

    @Override
    public void stepPre(double dt) {
        applyCellForce(dt);
    }

    @Override
    public void stepPhysics(double dt) {
        movePhysics(dt);
    }

    @Override
    public void stepPost(double dt) {
        if(!cell.isUpdated()) velocity = velocity.multiply(ElementData.STATIC_FRICTION);

        propagateTemperature(dt);
        checkConversions();

    }

    public void propagateTemperature(double dt){
        for(Cell c:cell.CELL_BORDERS){
            if(c == null) continue;
            Element e = c.getElement();
            if(e == null) continue;

            double dTemp = e.getTemperature() - temperature;
            dTemp *= dt / 8;
            temperature += dTemp;
            e.setTemperature(getTemperature() - dTemp);
        }
    }

    public void checkConversions(){
        if(chanceHighTemperature.check(temperature)){
            Element newElement = Element.spawn(typeHighTemperature);
            newElement.setTemperature(temperature);
            cell.setElement(newElement);
        }else if(chanceLowTemperature.check(temperature)){
            Element newElement = Element.spawn(typeLowTemperature);
            newElement.setTemperature(temperature);
            cell.setElement(newElement);
        }
    }

    public void applyCellForce(double dt){
        applyForce(cell.getForce(), dt);
    }

    public void applyForce(V2D force, double dt){
        velocity = velocity.add(force.multiply(dt));
        double sqVelMag = velocity.magnitudeSquared();
        double sqSpeedMax = ElementData.SPEED_MAX * ElementData.SPEED_MAX;
        if(sqVelMag > sqSpeedMax){
            velocity = V2D.ZERO;
        }
    }

    public boolean movePhysics(double dt){
        final boolean inFSS = false;
        V2D newLocation = cell.LOCATION.add(velocity.multiply(dt / Element.METRIC_WIDTH));
        return steppingCheckAndSwap(newLocation, inFSS);
    }

    public boolean steppingCheckAndSwap(V2D to, boolean inFSS){
        boolean hasMoved = false;
        for(V2D step:XMath.bresenham(cell.LOCATION, to)){
            if(step.equal(cell.LOCATION)) continue;
            if(checkAndSwap(step, inFSS)){
                hasMoved = true;
            }else{
                break;
            }
        }
        return hasMoved;
    }

    public boolean checkAndSwap(V2D to, boolean inFSS){
        if(cell.canSwap(to)){
            cell.swap(to);
            updatedInFSS = inFSS;
            return true;
        }
        return false;
    }

    protected void setMassData(double density){
        this.density = density;
        mass = METRIC_VOLUME * density;
        iMass = (mass == 0 ? 0:1/mass);
    }

    public void updateMass(double newMass){
        mass = newMass;
        iMass = (mass == 0 ? 0:1/mass);
    }

    public static Element spawn(String type){
        switch (type){
            case ElementData.SAND: return new Sand();
            case ElementData.AIR: return new Air();
            case ElementData.SALT: return new Salt();
            case ElementData.COAL: return new Coal();
            case ElementData.WATER: return new Water();
            case ElementData.WOOD: return new Wood();
            case ElementData.ACID: return new Acid();
            case ElementData.STEAM: return new Steam();
            case ElementData.ICE: return new Ice();
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

    public void setVelocity(V2D velocity) {
        this.velocity = velocity;
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

    public void setTemperature(double temperature) {
        this.temperature = temperature;
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

    public String getTypeLowTemperature() {
        return typeLowTemperature;
    }

    public void setTypeLowTemperature(String typeLowTemperature) {
        this.typeLowTemperature = typeLowTemperature;
    }

    public ChanceThreshold<Double> getChanceLowTemperature() {
        return chanceLowTemperature;
    }

    public void setChanceLowTemperature(ChanceThreshold<Double> chanceLowTemperature) {
        this.chanceLowTemperature = chanceLowTemperature;
    }

    public String getTypeHighTemperature() {
        return typeHighTemperature;
    }

    public void setTypeHighTemperature(String typeHighTemperature) {
        this.typeHighTemperature = typeHighTemperature;
    }

    public ChanceThreshold<Double> getChanceHighTemperature() {
        return chanceHighTemperature;
    }

    public void setChanceHighTemperature(ChanceThreshold<Double> chanceHighTemperature) {
        this.chanceHighTemperature = chanceHighTemperature;
    }

    public String getTypeBurned() {
        return typeBurned;
    }

    public void setTypeBurned(String typeBurned) {
        this.typeBurned = typeBurned;
    }

    public ChanceThreshold<Double> getChanceBurn() {
        return chanceBurn;
    }

    public void setChanceBurn(ChanceThreshold<Double> chanceBurn) {
        this.chanceBurn = chanceBurn;
    }

    public String getTypeDissolved() {
        return typeDissolved;
    }

    public void setTypeDissolved(String typeDissolved) {
        this.typeDissolved = typeDissolved;
    }

    public ChanceThreshold<Double> getChanceDissolve() {
        return chanceDissolve;
    }

    public void setChanceDissolve(ChanceThreshold<Double> chanceDissolve) {
        this.chanceDissolve = chanceDissolve;
    }

    public double getPressureFlow(Cell relativeTo) {
        return 0;
    }
}
