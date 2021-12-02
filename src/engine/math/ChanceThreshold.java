package engine.math;

import java.util.function.Predicate;

public class ChanceThreshold<T>{

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
