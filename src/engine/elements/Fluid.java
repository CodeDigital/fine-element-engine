package engine.elements;

import engine.Colour;
import engine.containers.Cell;
import engine.math.Chance;
import engine.math.V2D;
import engine.math.XMath;

import java.util.ArrayList;
import java.util.Collections;

public abstract class Fluid extends Element{

    private Chance fluidFSSSpread = new Chance(1);
    private double fluidFSSRange = 1;
    private Chance fluidEqualise = new Chance(0);
    private FluidBody fluidParent = new FluidBody();

    public Fluid(String MATTER, String TYPE) {
        super(MATTER, TYPE);
    }

    public Fluid(String MATTER, String TYPE, double fluidFSSSpreadProbability) {
        super(MATTER, TYPE);
        fluidFSSSpread = new Chance(fluidFSSSpreadProbability);
    }

    public Fluid(String MATTER, String TYPE, Chance fluidFSSSpread) {
        super(MATTER, TYPE);
        this.fluidFSSSpread = fluidFSSSpread;
    }

    @Override
    protected void setMassData(double density) {
        super.setMassData(density);
    }

    @Override
    public void stepPre(double dt) {
        applyCellForce(dt);
        fluidParent.reset();
        checkFluidParents();
    }

    @Override
    public void stepPhysics(double dt) {
        movePhysics(dt);
        fluidEqualisation();
    }

    public void fluidEqualisation(){
        checkFluidParents();

        // chance at swapping with the lowest dot product fluid of same type
        if(fluidEqualise.check()){

            Fluid lowestFluid = fluidParent.getLowestFluid();
            if(lowestFluid == null) return;

            ArrayList<Cell> borders = cell.CELL_BORDERS;
            Collections.shuffle(borders);
            for(Cell c:borders){
                if(cell.canSwap(c)){
                    if(c.getElement().TYPE == TYPE) return;
                    if(fluidParent.getLowestDotProduct() < FluidBody.dotProduct(c)){
                        fluidParent.remove(lowestFluid);
                        lowestFluid.getCell().swap(c);
                        return;
                    }
                }
            }
        }
    }


    @Override
    public void stepFSS(double dt) {

        assert cell != null;

        double range = fluidFSSRange * Math.random();

        V2D fssDown = V2D.CARDINALS[2];
        V2D down = cell.applyDirection(fssDown).add(cell.LOCATION);
        V2D fssRight = cell.applyDirection(V2D.CARDINALS[1]);
        V2D right = fssRight.multiply(range).add(cell.LOCATION);
        V2D downRight = fssRight.add(down);
        V2D fssLeft = cell.applyDirection(V2D.CARDINALS[3]);
        V2D left = fssLeft.multiply(range).add(cell.LOCATION);
        V2D downLeft = fssLeft.add(down);

        ArrayList<V2D> order = new ArrayList<>();
        order.add(downRight);
        order.add(downLeft);
        if(fluidFSSSpread.check()){
            order.add(right);
            order.add(left);
        }

        Collections.shuffle(order);

        if(XMath.randomBoolean()){
            order.add(down);
        }else{
            order.add(0, down);
        }

        for(V2D to:order){
            if(steppingCheckAndSwap(to)) return;
        }
    }

    @Override
    public void stepPost(double dt) {
        super.stepPost(dt);
        fluidParent.setWasReset(false);
    }

    public void checkFluidParents(){
        fluidParent.remove(this);
        FluidBody newParent = new FluidBody();
        double lowestDP = FluidBody.dotProduct(this);

        ArrayList<Cell> borders = cell.CELL_BORDERS;
        Collections.shuffle(borders);
        for(Cell c:borders){
            if(c == null) continue;
            Element e = c.getElement();
            if(e == null) continue;
            if(e.TYPE != TYPE) continue;
            if(!(e instanceof Fluid)) continue;
            double dp = ((Fluid) e).getFluidParent().getLowestDotProduct();
            if(dp <= lowestDP){
                if(((Fluid) e).getFluidParent().getSize() >= newParent.getSize()){
                    lowestDP = dp;
                    newParent = ((Fluid) e).getFluidParent();
                }
            }
        }

        fluidParent = newParent;
        fluidParent.add(this);
    }

    @Override
    public Colour getColour() {
        if(fluidParent.getLowestFluid() == this){
            return new Colour(255, 0, 0);
        }
        return super.getColour();
    }

    @Override
    public void setCell(Cell cell) {
        super.setCell(cell);
        checkFluidParents();
    }

    public Chance getFluidFSSSpread() {
        return fluidFSSSpread;
    }

    public void setFluidFSSSpread(Chance fluidFSSSpread) {
        this.fluidFSSSpread = fluidFSSSpread;
    }

    public double getFluidFSSRange() {
        return fluidFSSRange;
    }

    public void setFluidFSSRange(double fluidFSSRange) {
        this.fluidFSSRange = fluidFSSRange;
    }

    public Chance getFluidEqualise() {
        return fluidEqualise;
    }

    public void setFluidEqualise(Chance fluidEqualise) {
        this.fluidEqualise = fluidEqualise;
    }

    public FluidBody getFluidParent() {
        return fluidParent;
    }

    public void setFluidParent(FluidBody fluidParent) {
        this.fluidParent = fluidParent;
    }


}



// Implementation 1.0;

//package engine.elements;
//
//import engine.containers.Cell;
//import engine.containers.Chunk;
//import engine.math.V2D;
//import engine.math.XMath;
//
//// FLUID SIM FROM:
//// Particle-based Viscoelastic Fluid Simulation
//// By: Simon Clavet, Philippe Beaudoin, and Pierre Poulin
//
//public abstract class Fluid extends Element{
//
//    private Cell cellPrevious;
//    private double fluidViscosityLinear = 0;
//    private double fluidViscosityQuadratic = 0;
//    private double fluidStiffness = 0;
//    private double fluidRestDensity = 0;
//    private boolean updatedPhysics = false;
//
//    public Fluid(String MATTER, String TYPE) {
//        super(MATTER, TYPE);
//    }
//
//    @Override
//    public void stepPre(double dt) {
//        applyCellForce(dt);
//        updatedPhysics = false;
//        cellPrevious = cell;
//        // TODO: apply viscosity
//        applyViscosity(dt);
//
//        // TODO: adjust springs
//    }
//
//    @Override
//    public void stepPhysics(double dt) {
//
//        // TODO: update springs
//
//        // TODO: perform density updating
//        if(movePhysics(dt)) updatedPhysics = true;
//
//    }
//
//    @Override
//    public void stepPost(double dt) {
////        super.stepPost(dt);
//        // use previous position to compute next velocity
//
//        doubleDensityRelaxation(dt);
//
//        if(updatedPhysics){
//            velocity = cell.LOCATION.subtract(cellPrevious.LOCATION).multiply(METRIC_WIDTH/dt);
//        }
//    }
//
//    public void applyViscosity(double dt) {
//
//        final double SQ_2 = XMath.SQRT_2;
//
//        for(int i = 3; i < 6; i++){
//            Cell c = cell.CELL_BORDERS.get(i);
//            if(c == null) continue;
//            Element e = c.getElement();
//            if(e == null) continue;
//            if(e.TYPE == ElementData.MATTER_SOLID
//            || e.TYPE == ElementData.MATTER_REACTION) continue;
//
//            V2D rij = V2D.OCTALS[i];
//            V2D rijNormal = rij.normal();
//            double rijMagnitude = (i % 2 == 0 ? XMath.SQRT_2:1);
//
//            double q = rijMagnitude / SQ_2;
//            double u = rijNormal.dot(velocity.subtract(e.velocity));
//            if(u > 0){
//                V2D impulse = rijNormal.multiply(0.5)
//                        .multiply(dt)
//                        .multiply(1 - q)
//                        .multiply(fluidViscosityLinear * u + fluidViscosityQuadratic * u * u);
//                velocity = velocity.subtract(impulse);
//                e.setVelocity(e.getVelocity().subtract(impulse));
//            }
//
//        }
//    }
//
//    public void doubleDensityRelaxation(double dt){
//
//        final double SQ_2 = Chunk.WIDTH / 2;
//        double rho = 0;
//        double rhoNear = 0;
//
//        for(int i = 0; i < 8; i++){
//            Cell c = cell.CELL_BORDERS.get(i);
//            if(c == null) continue;
//            Element e = c.getElement();
//            if(e == null) continue;
//            if(e.TYPE == ElementData.MATTER_SOLID
//            || e.TYPE == ElementData.MATTER_REACTION) continue;
//
//            V2D rij = V2D.OCTALS[i];
//            V2D rijNormal = rij.normal();
//            double rijMagnitude = (i % 2 == 0 ? XMath.SQRT_2:1);
//
//            double q = rijMagnitude / SQ_2;
//
//            rho += e.getDensity() * (1 - q) * (1 - q);
//            rhoNear += e.getDensity() * (1 - q) * (1 - q) * (1 - q);
//        }
//
//        double pressure = fluidStiffness * (rho - fluidRestDensity);
//        double pressureNear = fluidStiffness * rhoNear;
//        V2D dx = V2D.ZERO;
//
//        for(int i = 0; i < 8; i++){
//            Cell c = cell.CELL_BORDERS.get(i);
//            if(c == null) continue;
//            Element e = c.getElement();
//            if(e == null) continue;
//            if(e.TYPE == ElementData.MATTER_SOLID
//                    || e.TYPE == ElementData.MATTER_REACTION) continue;
//
//            V2D rij = V2D.OCTALS[i];
//            V2D rijNormal = rij.normal();
//            double rijMagnitude = (i % 2 == 0 ? XMath.SQRT_2:1);
//
//            double q = rijMagnitude / SQ_2;
//            V2D fluidDensity = rijNormal.multiply(0.5)
//                    .multiply(dt * dt)
//                    .multiply(pressure * (1 - q) + pressureNear * (1 - q) * (1 - q));
//            V2D eNewLocation = c.LOCATION.add(fluidDensity);
//            e.steppingCheckAndSwap(eNewLocation);
//            dx = dx.subtract(fluidDensity);
//        }
//
//        steppingCheckAndSwap(cell.LOCATION.add(dx));
//    }
//
//    public Cell getCellPrevious() {
//        return cellPrevious;
//    }
//
//    public void setCellPrevious(Cell cellPrevious) {
//        this.cellPrevious = cellPrevious;
//    }
//
//    public double getFluidViscosityLinear() {
//        return fluidViscosityLinear;
//    }
//
//    public void setFluidViscosityLinear(double fluidViscosityLinear) {
//        this.fluidViscosityLinear = fluidViscosityLinear;
//    }
//
//    public double getFluidViscosityQuadratic() {
//        return fluidViscosityQuadratic;
//    }
//
//    public void setFluidViscosityQuadratic(double fluidViscosityQuadratic) {
//        this.fluidViscosityQuadratic = fluidViscosityQuadratic;
//    }
//
//    public double getFluidStiffness() {
//        return fluidStiffness;
//    }
//
//    public void setFluidStiffness(double fluidStiffness) {
//        this.fluidStiffness = fluidStiffness;
//    }
//
//    public double getFluidRestDensity() {
//        return fluidRestDensity;
//    }
//
//    public void setFluidRestDensity(double fluidRestDensity) {
//        this.fluidRestDensity = fluidRestDensity;
//    }
//
//    public boolean isUpdatedPhysics() {
//        return updatedPhysics;
//    }
//
//    public void setUpdatedPhysics(boolean updatedPhysics) {
//        this.updatedPhysics = updatedPhysics;
//    }
//}
