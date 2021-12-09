package engine.math;

// TODO: Commenting
public class Chance {

    private final double CHANCE;

    public Chance(double CHANCE) {
        this.CHANCE = CHANCE;
    }

    public Chance(int numerator, int denominator){
        this.CHANCE = (double) numerator / (double)  denominator;
    }

    public boolean check(){
        return Math.random() < CHANCE;
    }

}
