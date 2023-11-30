package com.mystical.core;

import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Random;

import com.mystical.BFrame;
import com.mystical.core.vector.Vector3;

public final class MysticData implements WindowListener {
    public BFrame frame;
    public int numPoints;

    public double rad;

    public double distThreshold = 200;

    public double ry, rz;
    public double vry, vrz;

    public double[][] projectedPoints;

    public double[][] points;
    public double[][] velocities;

    public double[][] relAnchors;
    public double[][] absAnchors;


    public Color color;

    public void projectAll() {
        for(int i = 0; i < numPoints; i++) {
            Vector3.project2d(points[i], projectedPoints[i]);
        }
    }

    public void update(BFrame frame, double deltaTime) {
        double cenX = frame.getX() + frame.getWidth()/2 - 10;
        double cenY = frame.getY() + frame.getHeight()/2;
        rad = Math.min(frame.getWidth(), frame.getHeight()) * 0.4;

        ry = Math.min(0.005, Math.max(vry + ry, -0.005));
        rz = Math.min(0.005, Math.max(vrz + rz, -0.005));

        vry += Math.random() * 0.00004 - 0000002;
        vrz += Math.random() * 0.00004 - 0.00002;

        updatePoints(cenX, cenY, deltaTime);



        projectAll();
    }


    public void updatePoints(double cenX, double cenY, double deltaTime) {
        System.out.println(deltaTime);
        double sinY = Math.sin(ry * deltaTime), cosY = Math.cos(ry * deltaTime);
        double sinZ = Math.sin(rz * deltaTime), cosZ = Math.cos(rz * deltaTime);
        
        for(int i = 0; i < numPoints; i++) {

            Vector3.rotateY(relAnchors[i], sinY, cosY);
            Vector3.rotateZ(relAnchors[i], sinZ, cosZ);
            Vector3.setLength(relAnchors[i], rad);

            absAnchors[i][0] = relAnchors[i][0] + cenX;
            absAnchors[i][1] = relAnchors[i][1] + cenY;
            absAnchors[i][2] = relAnchors[i][2];


            Vector3.pushTo(points[i], velocities[i], absAnchors[i], (0.0004 * deltaTime));
            Vector3.add(points[i], velocities[i], deltaTime);
            Vector3.multiply(velocities[i], Math.pow(0.95, deltaTime));
        }
    }


    public MysticData(int numPoints, double rad, double cenX, double cenY, Color color, BFrame frame) {
        this.frame = frame;
        this.numPoints = numPoints;
        this.rad = rad;

        projectedPoints = new double[numPoints][2];

        points = new double[numPoints][];
        
        relAnchors = new double[numPoints][];
        absAnchors = new double[numPoints][];

        velocities = new double[numPoints][3];


        this.color = color;
        
        Random rand = new Random();
        double theta, phi;
        for (int i = 0; i < numPoints; i++) {
            theta = rand.nextDouble() * 2 * Math.PI;
            phi = Math.acos(2 * rand.nextDouble() - 1);

            points[i] = new double[]{
                rad * Math.sin(phi) * Math.cos(theta) + cenX,
                rad * Math.sin(phi) * Math.sin(theta) + cenY,
                rad * Math.cos(phi)
            };
            relAnchors[i] = new double[]{
                points[i][0] - cenX,
                points[i][1] - cenY,
                points[i][2]
            };
            absAnchors[i] = new double[]{
                points[i][0],
                points[i][1],
                points[i][2]
            };
        }

        ry = Math.random() * 0.005 - 0.0025;
        rz = Math.random() * 0.005 - 0.0025;

        projectAll();
    }

    @Override
    public void windowClosing(WindowEvent e) {
        for(int i = frame.id + 1; i < frame.master.numFrames; i++) {
            frame.master.frames.get(i).id--;
        }
        frame.master.numFrames--;
        frame.master.frames.remove(frame.id);
        frame.master.datas.remove(frame.id);

        if(frame.master.numFrames == 0) {
            System.exit(0);
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {}
    @Override
    public void windowActivated(WindowEvent e) {}
    @Override
    public void windowDeactivated(WindowEvent e) {}
    @Override
    public void windowDeiconified(WindowEvent e) {}
    @Override
    public void windowIconified(WindowEvent e) {}
    @Override
    public void windowOpened(WindowEvent e) {}
}
