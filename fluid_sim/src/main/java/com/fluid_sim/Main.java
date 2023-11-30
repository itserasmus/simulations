package com.fluid_sim;

import com.fluid_sim.core.Sim;

public class Main {
    public static void main(String[] args) {
        BFrame frame = new BFrame();
        frame.initialize(new BPanel());
        frame.maximize();
        frame.panel.initialize(frame, new Sim());
    }
}