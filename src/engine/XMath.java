package engine;

public class XMath {

    public static final double EPSILON = 0.001;
    public static final double BIAS_REL = 0.95;
    public static final double BIAS_ABS = 0.01;

    public static final double G = sciNum(6.67408, -11);

    public static double clamp(double val, double lo, double hi){
        return Math.max(lo, Math.min(val, hi));
    }

    public static double sciNum(double val, double pow){
        return (val * Math.pow(10, pow));
    }

}
