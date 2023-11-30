package com.mystical.core.vector;

public final class Vector3 {
    private Vector3() {}

    
    public static double[] project2d(double[] v3) {
        return new double[]{v3[0], v3[1]};
    }

    public static void project2d(double[] v3, double[] op) {
        op[0] = v3[0];
        op[1] = v3[1];
    }

    public static void rotateX(double[] v3, double rad) {
        double sin = Math.sin(rad),
            cos = Math.cos(rad),
            ny = v3[2]*cos - v3[1]*sin;
        v3[1] = v3[2]*sin + v3[1]*cos;
        v3[2] = ny;
    }
    public static void rotateX(double[] v3, double sin, double cos) {
        double ny = v3[2]*cos - v3[1]*sin;
        v3[1] = v3[2]*sin + v3[1]*cos;
        v3[2] = ny;
    }

    public static void rotateY(double[] v3, double rad) {
        double sin = Math.sin(rad),
            cos = Math.cos(rad),
            nx = v3[0]*cos - v3[2]*sin;
        v3[2] = v3[0]*sin + v3[2]*cos;
        v3[0] = nx;
    }
    public static void rotateY(double[] v3, double sin, double cos) {
        double nx = v3[0]*cos - v3[2]*sin;
        v3[2] = v3[0]*sin + v3[2]*cos;
        v3[0] = nx;
    }

    public static void rotateZ(double[] v3, double rad) {
        double sin = Math.sin(rad),
            cos = Math.cos(rad),
            nx = v3[0]*cos - v3[1]*sin;
        v3[1] = v3[0]*sin + v3[1]*cos;
        v3[0] = nx;
    }
    public static void rotateZ(double[] v3, double sin, double cos) {
        double nx = v3[0]*cos - v3[1]*sin;
        v3[1] = v3[0]*sin + v3[1]*cos;
        v3[0] = nx;
    }



    public static void pushTo(double[] p3, double[] v3, double[] a3, double k) {
        double fMul = Math.sqrt(
            (a3[0] - p3[0]) * (a3[0] - p3[0]) +
            (a3[1] - p3[1]) * (a3[1] - p3[1]) +
            (a3[2] - p3[2]) * (a3[2] - p3[2])
        ) * k;
        v3[0] += (a3[0] - p3[0]) * fMul;
        v3[1] += (a3[1] - p3[1]) * fMul;
        v3[2] += (a3[2] - p3[2]) * fMul;

    }

    public static void add(double[] p3, double[] v3) {
        p3[0] += v3[0];
        p3[1] += v3[1];
        p3[2] += v3[2];

    }

    public static void add(double[] p3, double[] v3, double k) {
        p3[0] += v3[0] * k;
        p3[1] += v3[1] * k;
        p3[2] += v3[2] * k;

    }


    public static void multiply(double[] v3, double k) {
        v3[0] *= k;
        v3[1] *= k;
        v3[2] *= k;
    }

    public static void subtract(double[] v3, double[] s3) {
        v3[0] -= s3[0];
        v3[1] -= s3[1];
        v3[2] -= s3[2];
    }

    public static void setLength(double[] v3, double k) {
        double fMul = k / Math.sqrt(v3[0]*v3[0] + v3[1]*v3[1] + v3[2]*v3[2]);
        v3[0] *= fMul;
        v3[1] *= fMul;
        v3[2] *= fMul;
    }

    public static void setLength(double[] v3, double k, double[] op) {
        double fMul = k / Math.sqrt(v3[0]*v3[0] + v3[1]*v3[1] + v3[2]*v3[2]);
        op[0] = v3[0]*fMul;
        op[1] = v3[1]*fMul;
        op[2] = v3[2]*fMul;
    }

    public static void projectOntoPlane(double[] v3, double[] n3, double[] result) {
        double magSqr = n3[0]*n3[0] + n3[1]*n3[1] + n3[2]*n3[2];
        if (magSqr == 0) {
            return;
        }
    
        double projectionScalar = Vector3.dot(v3, n3) / magSqr;
    
        for (int i = 0; i < 3; i++) {
            result[i] = v3[i] - projectionScalar * n3[i];
        }
    }

    public static void projectOntoPlane(double[] v3, double[] n3) {
        double magSqr = n3[0]*n3[0] + n3[1]*n3[1] + n3[2]*n3[2];
        if (magSqr == 0) {
            return;
        }
    
        double projectionScalar = Vector3.dot(v3, n3) / magSqr;
    
        for (int i = 0; i < 3; i++) {
            v3[i] = v3[i] - projectionScalar * n3[i];
        }
    }

    public static double dot(double[] v3, double[] p3) {
        return v3[0] * p3[0] + v3[1] * p3[1] + v3[2] * p3[2];
    }

    public static double distSqr(double[] v3, double[] p3) {
        return (v3[0] - p3[0])*((v3[0] - p3[0])) + (v3[1] - p3[1])*(v3[1] - p3[1]) + (v3[2] - p3[2])*(v3[2] - p3[2]);
    }

    public static double length(double[] v3) {
        return Math.sqrt(v3[0]*(v3[0]) + (v3[1])*v3[1] + (v3[2])*v3[2]);
    }
}
