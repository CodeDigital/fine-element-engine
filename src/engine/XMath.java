package engine;

public class XMath {

    public static double clamp(double val, double lo, double hi){
        return Math.max(lo, Math.min(val, hi));
    }

}
