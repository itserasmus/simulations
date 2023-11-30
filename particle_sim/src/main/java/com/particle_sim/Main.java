package com.particle_sim;

import com.particle_sim.core.Sim;

public class Main {
    public static void main(String[] args) {
        BFrame frame = new BFrame();
        BPanel panel = new BPanel();
        frame.initialize(panel);
        frame.maximize();
        panel.initialize(frame, new Sim());
    }
}