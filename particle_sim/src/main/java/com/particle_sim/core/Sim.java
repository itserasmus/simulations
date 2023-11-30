package com.particle_sim.core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import com.particle_sim.BPanel;

public final class Sim {
    public final Random rand = new Random();

    public final Color background = new Color(0xFF000000);

    public final int diam = 10;
    public final int numParticles = 64;
    public final int numTypes = 3;


    public final float closeRepulsion = -3;
    public final float attractionStart = 40;
    public final float[][] forces = new float[numTypes][numTypes];
    public final float peak = 70;
    public final float isolationStart = 1000;

    public final Color[] colors = new Color[numTypes];

    public final float damping = .96f;
    public final float radOfInfluence = 100;
    
    public final int[] particleColors = new int[numParticles];
    public final float[][] particles = new float[numParticles][2];
    public final float[][] velocities = new float[numParticles][2];

    public final void update(float deltaTime, Graphics2D g, BPanel panel) {
        g.setColor(background);
        g.fillRect(0, 0, panel.getWidth(), panel.getHeight());

        for(int i = 0; i < numParticles; i++) {
            g.setColor(colors[particleColors[i]]);
            g.fillOval((int)particles[i][0]-diam/2, (int)particles[i][1]-diam/2, diam, diam);
        }

        applyForces(panel.getWidth(), panel.getHeight());
    }

    public final void applyForces(int width, int height) {
        float attraction = 0;
        float distance = 0;
        float[] attractionVec = new float[2];
        float[] p, v;
        for(int i = 0; i < numParticles; i++) {
            attractionVec[0] = 0;
            attractionVec[1] = 0;
            p = particles[i];
            v = velocities[i];
            for(int j = 0; j < numParticles; j++) {
                if(i == j) {continue;}
                
                distance = Vector.todoidalDistance(particles[i], particles[j], width, height);
                if(distance > isolationStart || distance == 0) {continue;}
                
                attraction = getAttraction(
                    distance,
                    forces[particleColors[i]][particleColors[j]]
                );
                attractionVec[0] += attraction * (particles[j][0]-particles[i][0]) / distance * 0.001f;
                attractionVec[1] += attraction * (particles[j][1]-particles[i][1]) / distance * 0.001f;
            }
            Vector.add(v, attractionVec);
            Vector.add(p, v);
            Vector.multiply(v, damping);

            if(p[0] < diam/2) {
                p[0] = width - diam/2;
            } else if(p[0] > width - diam/2) {
                p[0] = diam/2;
            }
            if(p[1] < diam/2) {
                p[1] = height - diam/2;
            } else if(p[1] > height - diam/2) {
                p[1] = diam/2;
            }
        }
    }



    public final float getAttraction(float dist, float peakForce) {
        if(dist < attractionStart) {
            return (attractionStart - dist) * closeRepulsion;
        }
        if(dist < peak) {
            return (peak - dist + attractionStart) * peakForce;
        }
        return (isolationStart - dist) * peakForce;
    }
    


















    public final void init(int width, int height) {
        for(int i = 0; i < numParticles; i++) {
            particles[i] = new float[]{
                (float)(rand.nextDouble()*width),
                (float)(rand.nextDouble()*height),
            };
            particleColors[i] = rand.nextInt(numTypes);
        }

        for(int i = 0; i < numTypes; i++) {
            colors[i] = new Color(
                rand.nextInt(0xFF/2) + 0xFF/4,
                rand.nextInt(0xFF/2) + 0xFF/4,
                rand.nextInt(0xFF/2) + 0xFF/4
            );

            for(int j = 0; j < numTypes; j++) {
                forces[i][j] = (float)rand.nextDouble()*2 - 1;
            }
        }
    }

    public Sim() {}
}
