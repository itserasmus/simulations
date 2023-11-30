package com.fluid_sim.core;

import java.awt.Color;
import java.awt.Graphics2D;

import com.fluid_sim.BPanel;

public final class Sim {
    public final float[] gravity = Vector.create(0, 0.1f);

    public Color background = new Color(0xFF000000);
    public Color pointColor = new Color(0xFFFFFFFF);

    public int diam = 10;
    public int numPoints;
    public float damping = .96f;
    public float radOfInfluence = 100;
    public float[][] points;
    public float[][] velocities;

    public final void update(float deltaTime, Graphics2D g, BPanel panel) {
        g.setColor(background);
        g.fillRect(0, 0, panel.getWidth(), panel.getHeight());

        g.setColor(pointColor);
        for(float[] p : points) {
            g.fillOval((int)p[0]-diam/2, (int)p[1]-diam/2, diam, diam);
        }

        applyForces(panel.getWidth(), panel.getHeight());
    }

    public final void applyForces(int width, int height) {
        float[] p, v;
        for(int i = 0; i < numPoints; i++) {
            p = points[i];
            v = velocities[i];
            Vector.add(v, gravity);
            Vector.add(p, v);

            if(p[0] < diam/2) {
                p[0] = diam/2;
                v[0] *= -damping;
            } else if(p[0] > width - diam/2) {
                p[0] = width - diam/2;
                v[0] *= -damping;
            }
            if(p[1] < diam/2) {
                p[1] = diam/2;
                v[1] *= -damping;
            } else if(p[1] > height - diam/2) {
                p[1] = height - diam/2;
                v[1] *= -damping;
            }
        }

        applyPushing();
    }

    public final void applyPushing() {
        for(int i = 0; i < numPoints; i++) {
            for(int j = 0; j < numPoints; j++) {

            }
        }
    }

    public final float caclDensity(int i, float[] p) {
        float density = 0;

        for(int j = 0; j < numPoints; j++) {
            if(j == i) {continue;}

            density += smoothingKernel(radOfInfluence, Vector.distance(p, points[j]));
        }

        return 4 * (float)(density / (Math.PI * Math.pow(radOfInfluence, 8)));
    }

    public final float smoothingKernel(float radius, float dist) {
        return (float)Math.pow(Math.max(0, radius * radius - dist * dist), 3);
    }










    public final void init() {
        int numRows = 26, numCols = 26;
        numPoints = numRows * numCols;
        points = new float[numPoints][2];
        velocities = new float[numPoints][1];
        for(int i = 0; i < numPoints; i++) {
            points[i] = new float[]{(i%numCols)*diam*2, (i/numCols)*diam*2};
            velocities[i] = new float[]{
                (Random.randInt()*0.000000001f)%2,
                (Random.randInt()*0.000000001f)%2
            };
        }
    }

    public Sim() {
        init();
    }
}
