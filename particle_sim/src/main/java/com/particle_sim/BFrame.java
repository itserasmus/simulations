package com.particle_sim;

import javax.swing.JFrame;

public final class BFrame extends JFrame {
    public BPanel panel;

    public void maximize() {
        setExtendedState(MAXIMIZED_BOTH);
    }
    public void minimize() {
        setExtendedState(ICONIFIED);
    }
    public void restore() {
        setExtendedState(NORMAL);
    }
    public void initialize(BPanel panel) {
        this.panel = panel;
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
