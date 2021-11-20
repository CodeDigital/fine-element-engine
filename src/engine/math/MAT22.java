package engine.math;

public class MAT22 implements Mathable<MAT22>{

    public static final MAT22 ZERO = new MAT22(0, 0, 0, 0);
    public final double M00, M01, M10, M11;

    public MAT22(double m00, double m01, double m10, double m11) {
        M00 = m00;
        M01 = m01;
        M10 = m10;
        M11 = m11;
    }

    public MAT22(double angle){
        double c = Math.cos(angle);
        double s = Math.sin(angle);
        M00 = c;
        M01 = -s;
        M10 = s;
        M11 = c;
    }

    @Override
    public MAT22 add(MAT22 rhs) {
        return new MAT22(
            M00 + rhs.M00,
            M01 + rhs.M01,
            M10 + rhs.M10,
            M11 + rhs.M11
        );
    }

    @Override
    public MAT22 subtract(MAT22 rhs) {
        return add(rhs.negative());
    }

    @Override
    public MAT22 multiply(double scalar) {
        return new MAT22(
            M00 * scalar,
            M01 * scalar,
            M10 * scalar,
            M11 * scalar
        );
    }

    @Override
    public MAT22 round() {
        return new MAT22(
            Math.round(M00),
            Math.round(M01),
            Math.round(M10),
            Math.round(M11)
        );
    }

    @Override
    public MAT22 negative() {
        return multiply(-1);
    }

    @Override
    public boolean equal(MAT22 rhs) {
        return (XMath.equal(M00, rhs.M00) &&
                XMath.equal(M01, rhs.M01) &&
                XMath.equal(M10, rhs.M10) &&
                XMath.equal(M11, rhs.M11));
    }

    public double getAngle(){
        double angle = Math.atan(M10 / M00);
        if(M00 < 0) {
            angle += Math.PI / 2;
        }
        if(M10 < 0){
            angle *= -1;
        }
        return angle;
    }
}
