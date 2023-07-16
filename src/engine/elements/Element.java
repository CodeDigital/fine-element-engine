package engine.elements;

import engine.Colour;
import engine.Steppable;
import engine.containers.Cell;
import engine.math.Chance;
import engine.math.V2D;
import engine.math.XMath;

/**
 * The element class.
 * Contains all types of scientific information.
 */
public abstract class Element implements Steppable {

    public final String MATTER, TYPE;

    // Cell and showing info
    private Cell cell;
    private Colour colour;

    // Translational Info
    private V2D acceleration = V2D.ZERO;
    private V2D velocity = V2D.ZERO;

    // Scientific Info
    public static final double METRIC_WIDTH = 0.0625; // metres
    public static final double METRIC_VOLUME = METRIC_WIDTH * METRIC_WIDTH * 0.25; // metres^3

    // Mass Info
    private double density;
    private double mass;
    private double iMass;
    private double restitution = 0.5;

    // Other Science Info
    private boolean isStatic = false;
    private boolean isCharged = false;
    private double temperature = XMath.toKelvin(24); // in Celsius
    private double newTemperature = temperature;
    private double gravityEffect = 1;
    private double conductivityHeat = ElementData.DEFAULT_CONDUCTIVITY_HEAT;
    private double conductivityElectrical = 0;
    private double electricalCharge = 0;
    private double frictionDynamic = 0;
    private double frictionStatic = 0;

    // low temperature (freezing, condensation).
    private String lowTemperatureType = ElementData.AIR;
    private Chance lowTemperatureChance = Chance.ALWAYS_FALSE;
    private double lowTemperature = Double.MIN_VALUE;

    // high temperature (melting, evaporation).
    private String highTemperatureType = ElementData.AIR;
    private Chance highTemperatureChance = Chance.ALWAYS_FALSE;
    private double highTemperature = Double.MAX_VALUE;

    // burning (by fire or spark or other super hot element).
    private String burnType = ElementData.AIR;
    private Chance burnChance = Chance.ALWAYS_FALSE;
    private double burnTemperature = 0;

    // dissolving (by acid).
    private String dissolveType = ElementData.AIR;
    private Chance dissolveChance = Chance.ALWAYS_FALSE;
    private double dissolveTemperature = Double.MAX_VALUE;

    // track if the update occurred in FSS or not (this may be used later to lock out physics features)
    private boolean updatedInFSS = false;

    /**
     * Instantiates a new Element.
     *
     * @param MATTER the matter
     * @param TYPE   the type
     */
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

        // propagate the temperature
        propagateTemperature(dt);
    }

    @Override
    public void stepPost(double dt) {
        if(!cell.isUpdated()) velocity = velocity.multiply(ElementData.STATIC_FRICTION);

        temperature = newTemperature;
        checkConversions();
    }

    /**
     * Propagate temperature through the cell's bordering this one.
     *
     * @param dt time delta
     */
    public void propagateTemperature(double dt){
        for(Cell c:cell.CELL_BORDERS){

            // ensure the cell and element exists.
            if(c == null) continue;
            Element e = c.getElement();
            if(e == null) continue;

            // get the temperature change.
            double dTemp = e.getTemperature() - temperature;
            dTemp *= dt;
            dTemp /= 8;
            double totalConductivity = conductivityHeat + e.getConductivityHeat();
            newTemperature += dTemp * (conductivityHeat / totalConductivity);
            e.setNewTemperature(e.getNewTemperature() - dTemp * (e.getConductivityHeat() / totalConductivity));
        }
    }

    /**
     * Convert elements depending on new temperatures.
     */
    public void checkConversions(){
        Element element = null;

        if(temperature <= lowTemperature){ // freezing or condensation
            if(lowTemperatureChance.check()){
                element = Element.spawn(lowTemperatureType);
            }
        }else if(temperature >= highTemperature){ // melting or evaporating
            if(highTemperatureChance.check()){
                element = Element.spawn(highTemperatureType);
            }
        }

        // convert if necessary
        if(element != null){
            element.setTemperature(temperature);
            cell.setElement(element);
        }
    }

    /**
     * Apply forces acting on the container cell.
     *
     * @param dt the time delta
     */
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

    /**
     * Move based on where the velocity sends you.
     *
     * @param dt the time delta
     * @return whether it was successful
     */
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
        if(canSwap(to, inFSS)){
            cell.swap(to);
            updatedInFSS = inFSS;
            return true;
        }
        return false;
    }

    public boolean canSwap(V2D to, boolean inFSS){
        return cell.canSwap(to);
    }

    protected void setMassData(double density){
        this.density = density;
        mass = METRIC_VOLUME * density;
        iMass = (mass == 0 ? 0:1/mass);
    }

    public static Element spawn(String type){
        return ElementData.spawn(type);
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

    public void setColour(Colour colour) {
        this.colour = colour;
    }

    public V2D getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(V2D acceleration) {
        this.acceleration = acceleration;
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

    public void setDensity(double density) {
        this.density = density;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
        iMass = (mass == 0 ? 0:1/mass);
    }

    public double getiMass() {
        return iMass;
    }

    public void setiMass(double iMass) {
        this.iMass = iMass;
    }

    public double getRestitution() {
        return restitution;
    }

    public void setRestitution(double restitution) {
        this.restitution = restitution;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public void setStatic(boolean aStatic) {
        isStatic = aStatic;
    }

    public boolean isCharged() {
        return isCharged;
    }

    public void setCharged(boolean charged) {
        isCharged = charged;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        double newTemp = XMath.clamp(temperature, XMath.TEMPERATURE_MIN, XMath.TEMPERATURE_MAX);
        this.temperature = temperature;
        this.newTemperature = temperature;
    }

    public double getNewTemperature() {
        return newTemperature;
    }

    public void setNewTemperature(double newTemperature) {
        this.newTemperature = newTemperature;
    }

    public double getGravityEffect() {
        return gravityEffect;
    }

    public void setGravityEffect(double gravityEffect) {
        this.gravityEffect = gravityEffect;
    }

    public double getConductivityHeat() {
        return conductivityHeat;
    }

    public void setConductivityHeat(double conductivityHeat) {
        this.conductivityHeat = conductivityHeat;
    }

    public double getConductivityElectrical() {
        return conductivityElectrical;
    }

    public void setConductivityElectrical(double conductivityElectrical) {
        this.conductivityElectrical = conductivityElectrical;
    }

    public double getElectricalCharge() {
        return electricalCharge;
    }

    public void setElectricalCharge(double electricalCharge) {
        this.electricalCharge = electricalCharge;
    }

    public double getFrictionDynamic() {
        return frictionDynamic;
    }

    public void setFrictionDynamic(double frictionDynamic) {
        this.frictionDynamic = frictionDynamic;
    }

    public double getFrictionStatic() {
        return frictionStatic;
    }

    public void setFrictionStatic(double frictionStatic) {
        this.frictionStatic = frictionStatic;
    }

    public String getLowTemperatureType() {
        return lowTemperatureType;
    }

    public void setLowTemperatureType(String lowTemperatureType) {
        this.lowTemperatureType = lowTemperatureType;
    }

    public Chance getLowTemperatureChance() {
        return lowTemperatureChance;
    }

    public void setLowTemperatureChance(Chance lowTemperatureChance) {
        this.lowTemperatureChance = lowTemperatureChance;
    }

    public double getLowTemperature() {
        return lowTemperature;
    }

    public void setLowTemperature(double lowTemperature) {
        this.lowTemperature = lowTemperature;
    }

    public String getHighTemperatureType() {
        return highTemperatureType;
    }

    public void setHighTemperatureType(String highTemperatureType) {
        this.highTemperatureType = highTemperatureType;
    }

    public Chance getHighTemperatureChance() {
        return highTemperatureChance;
    }

    public void setHighTemperatureChance(Chance highTemperatureChance) {
        this.highTemperatureChance = highTemperatureChance;
    }

    public double getHighTemperature() {
        return highTemperature;
    }

    public void setHighTemperature(double highTemperature) {
        this.highTemperature = highTemperature;
    }

    public String getBurnType() {
        return burnType;
    }

    public void setBurnType(String burnType) {
        this.burnType = burnType;
    }

    public Chance getBurnChance() {
        return burnChance;
    }

    public void setBurnChance(Chance burnChance) {
        this.burnChance = burnChance;
    }

    public double getBurnTemperature() {
        return burnTemperature;
    }

    public void setBurnTemperature(double burnTemperature) {
        this.burnTemperature = burnTemperature;
    }

    public String getDissolveType() {
        return dissolveType;
    }

    public void setDissolveType(String dissolveType) {
        this.dissolveType = dissolveType;
    }

    public Chance getDissolveChance() {
        return dissolveChance;
    }

    public void setDissolveChance(Chance dissolveChance) {
        this.dissolveChance = dissolveChance;
    }

    public double getDissolveTemperature() {
        return dissolveTemperature;
    }

    public void setDissolveTemperature(double dissolveTemperature) {
        this.dissolveTemperature = dissolveTemperature;
    }

    public boolean isUpdatedInFSS() {
        return updatedInFSS;
    }

    public void setUpdatedInFSS(boolean updatedInFSS) {
        this.updatedInFSS = updatedInFSS;
    }
}
