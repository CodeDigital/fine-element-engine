package engine.math;

// TODO: Commenting
public class Chance {

    public static final Chance ALWAYS_FALSE = new Chance(0);
    public static final Chance ALWAYS_TRUE = new Chance(1);

    public final double CHANCE;

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
