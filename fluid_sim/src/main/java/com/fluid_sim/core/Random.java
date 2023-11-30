package com.fluid_sim.core;

public final class Random {
    public static long longSeed = System.nanoTime();
    public static int intSeed = (int)System.nanoTime();

    public static long randLong() {
        longSeed = (1664525L * longSeed + 1013904223L);
        return longSeed;
    }

    public static long randInt() {
        intSeed = (1664525 * intSeed + 1013904223);
        return intSeed;
    }

    private Random() {}
}
