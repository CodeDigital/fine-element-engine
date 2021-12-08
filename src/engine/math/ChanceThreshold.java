package engine.math;

import java.util.function.Predicate;

public class ChanceThreshold<T>{

    public static final ChanceThreshold<Double> ALWAYS_FALSE = new ChanceThreshold<Double>(0, 0, i->false);
    public static final ChanceThreshold<Double> ALWAYS_TRUE = new ChanceThreshold<>(1, 1, i->true);
    private final Chance CHANCE_YES, CHANCE_NO;
    private final Predicate<T> threshold;

    public ChanceThreshold(double PROB_YES, double PROB_NO, Predicate<T> threshold) {
        this.CHANCE_YES = new Chance(PROB_YES);
        this.CHANCE_NO = new Chance(PROB_NO);
        this.threshold = threshold;
    }

    public boolean check(T value){
        if(threshold.test(value)){
            return CHANCE_YES.check();
        }else{
            return CHANCE_NO.check();
        }
    }
}
