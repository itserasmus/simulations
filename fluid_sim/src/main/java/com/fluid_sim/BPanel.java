package com.fluid_sim;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JPanel;

import com.fluid_sim.core.Sim;

public final class BPanel extends JPanel implements Runnable {
    public BFrame frame;
    public Sim sim;

    protected Thread frameThread;
    protected long lastFrame;
    public int fps = 10;

    protected boolean paintLock = true;

    public BFrame getBFrame() {
        return frame;
    }
    public void initialize(BFrame frame, Sim sim) {
        this.frame = frame;
        this.sim = sim;
        
        addComponentListener(new ComponentAdapter() {  
            public void componentResized(ComponentEvent e) {
            }
        });

        frameThread = new Thread(this);
        frameThread.start();
        paintLock = false;
    }
    @Override
    public void run() {
        while(frameThread != null) {
            if(System.nanoTime() < lastFrame + 1_000_000_000/fps) {
                try {
                    Thread.sleep(Math.max(0, lastFrame/1_000_000 + 1_000/fps - System.nanoTime()/1_000_000));
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }
            
            
            repaint();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        lastFrame = System.nanoTime();
        if(paintLock) {return;}
        long time = System.nanoTime();
        
        paintLock = true;
        
        sim.update(1.0f/fps, (Graphics2D)g, this);
        
        paintLock = false;
    }
}
