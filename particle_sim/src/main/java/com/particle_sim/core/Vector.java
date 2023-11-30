package com.particle_sim.core;

public final class Vector {
    private static float store1, store2;
    public static final float[] create(float x, float y) {
        return new float[]{x, y};
    }

    public static final float[] add(float[] v1, float[] v2) {
        v1[0] += v2[0];
        v1[1] += v2[1];
        return v1;
    }

    public static final float[] multiply(float[] v1, float scalar) {
        v1[0] *= scalar;
        v1[1] *= scalar;
        return v1;
    }

    public static final float distance(float[] v1, float[] v2) {
        return (float)Math.sqrt((v1[0] - v2[0])*(v1[0] - v2[0]) + (v1[1] - v2[1])*(v1[1] - v2[1]));
    }

    public static final float todoidalDistance(float[] v1, float[] v2, float width, float height) {
        store1 = Math.abs(v1[0] - v2[0]);
        store2 = Math.abs(v1[1] - v2[1]);

        if (store1 > width/2)
            store1 = width - store1;

        if (store2 > height/2)
            store2 = height - store2;

        return (float)Math.sqrt(store1 * store1 + store2 * store2);
    }

    private Vector() {}
}
