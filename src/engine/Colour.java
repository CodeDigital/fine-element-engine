package engine;

public class Colour {
    public final double R, G, B, A;

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

    public int get(){
        return Graphics.G().color((float) R, (float) G, (float) B, (float) A);
    }
}
