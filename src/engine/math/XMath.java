package engine.math;

import java.util.ArrayList;

// TODO: Commenting
public class XMath {

    public static final double EPSILON = 0.001;
    public static final double BIAS_REL = 0.95;
    public static final double BIAS_ABS = 0.01;

    public static final double G = sciNum(6.67408, -11);
    public static final double SQRT_2 = Math.sqrt(2);
    public static final double GRAVITY = 20;
    public static final double ZERO_CELSIUS = 273.15;
    public static final double TEMPERATURE_MIN = 0;
    public static final double TEMPERATURE_MAX = 12000;

    public static double toKelvin(double celsius){
        return ZERO_CELSIUS + celsius;
    }

    public static double clamp(double val, double lo, double hi){
        return Math.max(lo, Math.min(val, hi));
    }

    public static double sciNum(double val, double pow){
        return (val * Math.pow(10, pow));
    }

    public static boolean equal(double a, double b){
        return Math.abs(a - b) <= EPSILON;
    }

    public static double random(double lo, double hi){
        double r = Math.random();
        return lo + (hi - lo) * r;
    }

    public static double roundDP(double value, double dp){
        return (double) ((int) (value * Math.pow(10, dp))) / Math.pow(10, dp);
    }

    public static boolean randomBoolean(){
        return Math.random() >= 0.5;
    }


    /**
     * Sigmoid double.
     *
     * @param x the x
     * @param a the a (~ slope)
     * @param b the b (x offset, def = 0.5)
     * @return the double
     */
    public static double sigmoid(double x, double a, double b){
        return(1 / (1 + Math.exp(-a * (x - b))));
    }

    public static double biasFunction(double x, double bias){
        double k = Math.pow(1 - bias, 3);
        return (x * k) / (x * k - x + 1);
    }

    public static ArrayList<V2D> bresenham(V2D from, V2D to){
        int x0 = (int) from.X;
        int y0 = (int) from.Y;

        int x1 = (int) to.X;
        int y1 = (int) to.Y;

        if(Math.abs(y1 - y0) < Math.abs(x1 - x0)){
            return bresenhamOnX(from, to);
        }else{
            return bresenhamOnY(from, to);
        }
    }

    public static ArrayList<V2D> bresenhamOnX(V2D from, V2D to){
        int x0 = (int) from.X;
        int y0 = (int) from.Y;

        int x1 = (int) to.X;
        int y1 = (int) to.Y;

        ArrayList<V2D> line = new ArrayList<V2D>();

        int dx = x1 - x0;
        int dy = y1 - y0;
        int xi = 1;
        int yi = 1;

        if(dy < 0){
            yi = -1;
            dy = -dy;
        }

        if(dx < 0){
            xi = -1;
            dx = -dx;
        }

        int d = (2 * dy) - dx;
        int y = y0;

        for(int x = x0; x != x1; x+=xi){
            line.add(new V2D(x, y));
            if (d > 0){
                y = y + yi;
                d = d + (2 * (dy - dx));
            }else{
                d = d + 2*dy;
            }
        }

        line.add(to);
        return line;
    }

    public static ArrayList<V2D> bresenhamOnY(V2D from, V2D to) {
        int x0 = (int) from.X;
        int y0 = (int) from.Y;

        int x1 = (int) to.X;
        int y1 = (int) to.Y;

        ArrayList<V2D> line = new ArrayList<V2D>();

        int dx = x1 - x0;
        int dy = y1 - y0;
        int xi = 1;
        int yi = 1;

        if(dy < 0){
            yi = -1;
            dy = -dy;
        }

        if(dx < 0){
            xi = -1;
            dx = -dx;
        }


        int d = (2 * dx) - dy;
        int x = x0;

        for(int y = y0; y != y1; y+=yi){
            line.add(new V2D(x, y));
            if (d > 0){
                x = x + xi;
                d = d + (2 * (dx - dy));
            }else{
                d = d + 2*dx;
            }
        }

        line.add(to);
        return line;
    }

    public static double map(double val, double lo, double hi, double mapLo, double mapHi) {
        double frac = (val - lo) / (hi - lo);
        return frac * (mapHi - mapLo) + mapLo;
    }
}
















