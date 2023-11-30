package com.fluid_sim.core;

public final class Vector {
    public static final float[] create(float x, float y) {
        return new float[]{x, y};
    }

    public static final float[] add(float[] v1, float[] v2) {
        v1[0] += v2[0];
        v1[1] += v2[1];
        return v1;
    }

    public static final float distance(float[] v1, float[] v2) {
        return (float)Math.sqrt((v1[0] - v2[0])*(v1[0] - v2[0]) + (v1[1] - v2[1])*v1[1] - v2[1]);
    }

    private Vector() {}
}
