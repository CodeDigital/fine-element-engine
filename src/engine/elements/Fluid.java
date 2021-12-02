package engine.elements;

import engine.containers.Cell;
import engine.math.V2D;
import engine.math.XMath;

public abstract class Fluid extends Element{

    private double fluidRestDensity = 0;
    public static final double FLUID_GAS_CONSTANT = 8.314;
    private double fluidH = XMath.SQRT_2;
    private double fluidHSQ = fluidH * fluidH;
    private double fluidViscosity = 0;
    private double fluidPoly6 = 4 / (Math.PI * Math.pow(fluidH, 8));
    private double fluidSpikyGrad = -10 / (Math.PI * Math.pow(fluidH, 5));
    private double fluidViscosityLap = 40 / (Math.PI * Math.pow(fluidH, 5));
    private double fluidDensity = 0;
    private double fluidPressure = 0;

    public Fluid(String MATTER, String TYPE) {
        super(MATTER, TYPE);
    }

    @Override
    protected void setMassData(double density) {
        fluidDensity = density;
        super.setMassData(density);
    }

    public void setFluidData(double restDensity, double h, double viscosity){
        fluidRestDensity = restDensity;
        fluidH = h;
        fluidHSQ = h * h;
        fluidViscosity = viscosity;
        fluidPoly6 = 4 / (Math.PI * Math.pow(fluidH, 8));
        fluidSpikyGrad = -10 / (Math.PI * Math.pow(fluidH, 5));
        fluidViscosityLap = 40 / (Math.PI * Math.pow(fluidH, 5));
    }

    @Override
    public void stepPre(double dt) {
        computeDensityPressure();
//        applyCellForce(dt);
    }

    @Override
    public void stepPhysics(double dt) {
        computeForces(dt);
        movePhysics(dt);
    }

    public void computeDensityPressure(){
        fluidDensity = mass * fluidPoly6 * Math.pow(fluidHSQ, 3);

        for(int i = 0; i < 8; i++){
            Cell c = cell.CELL_BORDERS.get(i);
            if(c == null) continue;
            Element e = c.getElement();
            if(e == null) continue;
            if(!(e instanceof Fluid)) continue;

            V2D rij = V2D.OCTALS[i];
            double r2 = rij.magnitudeSquared();

            fluidDensity += e.getMass() * fluidPoly6 * Math.pow(fluidHSQ - r2, 3);

        }

        fluidPressure = FLUID_GAS_CONSTANT * (fluidDensity - fluidRestDensity);
//        System.out.println("fp: " + fluidPressure);
//        System.out.println("fd: " + fluidDensity);
    }

    public void computeForces(double dt){

        V2D forcePressure = V2D.ZERO;
        V2D forceViscosity = V2D.ZERO;

        for(int i = 0; i < 8; i++){
            Cell c = cell.CELL_BORDERS.get(i);
            if(c == null) continue;
            Element e = c.getElement();
            if(e == null) continue;
            if(!(e instanceof Fluid)) continue;

            V2D rij = V2D.OCTALS[i];
            double r = rij.magnitude();
//            System.out.println("rij: " + rij);
//            System.out.println("old fp: " + forcePressure);

            forcePressure = forcePressure.subtract(
                    rij.normal()
                            .multiply(e.getMass())
                            .multiply(fluidPressure + ((Fluid) e).getFluidPressure())
                            .multiply(0.5 / ((Fluid) e).getFluidDensity())
                            .multiply(fluidSpikyGrad)
                            .multiply(Math.pow(fluidH - r, 3))
            );
//            System.out.println("new fp: " + forcePressure);

            forceViscosity = forceViscosity.add(
                    e.getVelocity()
                            .subtract(velocity)
                            .multiply(fluidViscosity)
                            .multiply(e.getMass())
                            .multiply(1 / ((Fluid) e).getFluidDensity())
                            .multiply(fluidViscosityLap)
                            .multiply(fluidH - r)
            );

        }
        velocity = velocity.add(forcePressure.multiply(dt / fluidDensity));
        velocity = velocity.add(forceViscosity.multiply(dt / fluidDensity));
        applyCellForce(dt);
    }

    public double getFluidRestDensity() {
        return fluidRestDensity;
    }

    public double getFluidH() {
        return fluidH;
    }

    public double getFluidHSQ() {
        return fluidHSQ;
    }

    public double getFluidViscosity() {
        return fluidViscosity;
    }

    public double getFluidPoly6() {
        return fluidPoly6;
    }

    public double getFluidSpikyGrad() {
        return fluidSpikyGrad;
    }

    public double getFluidViscosityLap() {
        return fluidViscosityLap;
    }

    public double getFluidDensity() {
        return fluidDensity;
    }

    public double getFluidPressure() {
        return fluidPressure;
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
