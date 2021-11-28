package engine;

public class Colour {
    public static final Colour BLACK = new Colour(0);
    public static final Colour WHITE = new Colour(255);
    public final double R, G, B, A;

    public Colour(double grey) {
        R = grey;
        G = grey;
        B = grey;
        A = 255;
    }

    public Colour(double r, double g, double b, double a) {
        R = r;
        G = g;
        B = b;
        A = a;
    }

    public Colour(double r, double g, double b) {
        R = r;
        G = g;
        B = b;
        A = 255;
    }

    public int asInt(){
        return Graphics.G().color((float) R, (float) G, (float) B, (float) A);
    }

    public double getDarkness() {
        return (R + G + B + A) / 4;
    }
}
