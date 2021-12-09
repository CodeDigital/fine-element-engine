package engine;

import engine.math.XMath;

// TODO: Commenting
public class Colour {
    public static final Colour BLACK = new Colour(0);
    public static final Colour WHITE = new Colour(255);
    public final double R, G, B, A;

    public Colour(double grey) {
        R = XMath.clamp(grey, 0, 255);
        G = XMath.clamp(grey, 0, 255);
        B = XMath.clamp(grey, 0, 255);
        A = 255;
    }

    public Colour(double r, double g, double b, double a) {
        R = XMath.clamp(r, 0, 255);
        G = XMath.clamp(g, 0, 255);
        B = XMath.clamp(b, 0, 255);
        A = XMath.clamp(a, 0, 255);
    }

    public Colour(double r, double g, double b) {
        R = XMath.clamp(r, 0, 255);
        G = XMath.clamp(g, 0, 255);
        B = XMath.clamp(b, 0, 255);
        A = 255;
    }

    public int asInt(){
        return Graphics.G().color((float) R, (float) G, (float) B, (float) A);
    }

    public double getDarkness() {
        return (R + G + B + A) / 4;
    }

    public Colour darken(double by) {
        return new Colour(R - by, G - by, B - by);
    }

    public Colour setR(double r){
        return new Colour(r, G, B, A);
    }

    public Colour setG(double g){
        return new Colour(R, g, B, A);
    }

    public Colour setB(double b){
        return new Colour(R, G, b, A);
    }

    public Colour setA(double a){
        return new Colour(R, G, B, a);
    }
}
