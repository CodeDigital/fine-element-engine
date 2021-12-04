package engine.elements;

import engine.containers.Cell;

public class FluidBody {

    private Fluid lowestFluid;
    private final double MAX_DP = Double.MAX_VALUE;
    private double lowestDotProduct = MAX_DP;
    private boolean wasReset = false;
    private int size = 0;

    public void add(Fluid f){
        size ++;
        double dp = dotProduct(f);
        if(dp < lowestDotProduct){
            lowestFluid = f;
            lowestDotProduct = dp;
        }
    }

    public void remove(Fluid f){
        size --;
        if(f == lowestFluid) {
            lowestFluid = null;
            lowestDotProduct = MAX_DP;
        }
    }

    public void reset(){
        if(!wasReset){
            lowestFluid = null;
            lowestDotProduct = MAX_DP;
            wasReset = true;
            size = 0;
        }
    }

    public Fluid getLowestFluid() {
        return lowestFluid;
    }

    public double getLowestDotProduct() {
        return lowestDotProduct;
    }

    public static double dotProduct(Fluid f){
        return f.getCell().LOCATION.dot(f.getCell().getTotalForce());
    }

    public static double dotProduct(Cell c){
        return c.LOCATION.dot(c.getTotalForce());
    }

    public boolean isWasReset() {
        return wasReset;
    }

    public void setWasReset(boolean wasReset) {
        this.wasReset = wasReset;
    }

    public int getSize() {
        return size;
    }
}
