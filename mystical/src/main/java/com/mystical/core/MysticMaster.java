package com.mystical.core;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import com.mystical.BFrame;

public class MysticMaster implements Runnable, MouseListener {
    public int numFrames;
    public ArrayList<BFrame> frames;

    public ArrayList<MysticData> datas;
    
    public long lastClick;
    public long lastFrame;
    public final int fps = 6000;

    public boolean paintLock = true;
    public Thread frameThread;

    @Override
    public void run() {
        lastFrame = System.nanoTime() - 1_000_000_000/fps;
        while(frameThread != null) {
            long nanoTime = System.nanoTime();
            if(nanoTime < lastFrame + 1_000_000_000/fps) {
                try {
                    Thread.sleep(Math.max(0, lastFrame/1_000_000 + 1_000/fps - nanoTime/1_000_000));
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }
            
            update((nanoTime - lastFrame) * 0.000_000_06);
            repaint();

            lastFrame = nanoTime;
        }
    }
    
    public void update(double deltaTime) {
        for(int i = 0; i < numFrames; i++) {
            datas.get(i).update(frames.get(i), deltaTime);
        }
    }

    public void repaint() {
        if(paintLock) {return;}
        paintLock = true;
        
        for(int i = 0; i < numFrames; i++) {
            if(System.nanoTime() - frames.get(i).panel.lastFrame > 900_000_000/fps) {
                frames.get(i).repaint();
            }
        }

        paintLock = false;
    }

    public void init() {
        paintLock = false;
        frameThread = new Thread(this);
        frames = new ArrayList<BFrame>(numFrames);
        datas = new ArrayList<MysticData>(numFrames);
        
        for(int i = 0; i < numFrames; i++) {
            frames.add(new BFrame(this));
        }
        for(int i = 0; i < numFrames; i++) {
            frames.get(i).id = i;
            frames.get(i).init();
        }
        
        run();
    }

    public MysticMaster(int numFrames) {
        this.numFrames = numFrames;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(System.nanoTime() - lastClick < 300_000_000) {
            frames.add(new BFrame(this));
            frames.get(numFrames).id = numFrames;

            frames.get(numFrames).init();
            numFrames++;
        }

        lastClick = System.nanoTime();
    }
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
}
