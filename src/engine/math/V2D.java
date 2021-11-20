package engine.math;

public class V2D implements Mathable<V2D>{
    public static final V2D ZERO = new V2D(0, 0);
    public final double X, Y;

    public V2D(double x, double y) {
        X = x;
        Y = y;
    }

    public V2D(double angle){
        X = Math.cos(angle);
        Y = Math.sin(angle);
    }

    public static V2D set(double x, double y){
        return new V2D(x, y);
    }

    public static V2D set(double angle){
        return new V2D(angle);
    }

    public V2D setX(double x){
        return new V2D(x, Y);
    }

    public V2D setY(double y){
        return new V2D(X, y);
    }

    @Override
    public V2D add(V2D rhs) {
        return new V2D(rhs.X + X, rhs.Y + Y);
    }

    public V2D addX(double dx){
        return new V2D(X + dx, Y);
    }

    public V2D addY(double dy){
        return new V2D(X, Y + dy);
    }

    @Override
    public V2D subtract(V2D rhs) {
        return add(rhs.negative());
    }

    public V2D to(V2D to){
        return to.subtract(this);
    }

    @Override
    public V2D multiply(double scalar) {
        return new V2D(X * scalar, Y * scalar);
    }

    @Override
    public V2D round() {
        return new V2D(Math.round(X), Math.round(Y));
    }

    @Override
    public V2D negative() {
        return multiply(-1);
    }

    public V2D cross(double scalar){
        return new V2D(Y * scalar, X * -scalar);
    }

    public V2D crossReversed(double scalar){
        return cross(-scalar);
    }

    public double cross(V2D rhs){
        return (X * rhs.Y) - (Y * rhs.X);
    }

    public double dot(V2D rhs){
        return (X * rhs.X) + (Y * rhs.Y);
    }

    public double magnitudeSquared(){
        return (X * X) + (Y * Y);
    }

    public double magnitude(){
        return Math.sqrt(magnitudeSquared());
    }

    @Override
    public boolean equal(V2D rhs) {
        return (XMath.equal(X, rhs.X) && XMath.equal(Y, rhs.Y));
    }

    public double getAngle(){
        double angle = Math.atan(Y/X);
        if(X < 0){
            angle += Math.PI / 2;
        }
        if(Y < 0){
            angle *= -1;
        }
        return angle;
    }

    public MAT22 getRotation(){
        return new MAT22(getAngle());
    }
}
