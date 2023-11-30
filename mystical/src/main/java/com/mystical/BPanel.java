package com.mystical;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import com.mystical.core.MysticData;

public class BPanel extends JPanel {
    public BFrame frame;
    public boolean paintLock = false;
    public long lastFrame;

    public void init() {}


    @Override
    public void paintComponent(Graphics g) {
        if(paintLock) {return;}
        paintLock = true;
        lastFrame = System.nanoTime();

        Graphics2D g2 = (Graphics2D)g;
        
        int offX = -frame.getX();
        int offY = -frame.getY();
        
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, getWidth(), getHeight());
        
        for(int i = 0; i < frame.master.numFrames; i++) {
            MysticData data = frame.master.datas.get(i);
            g2.setColor(
                i == frame.id?
                    data.color:
                    new Color(
                        ((int)(data.color.getRed() * 0.6)) << 16 |
                        ((int)(data.color.getGreen() * 0.6)) << 8 |
                        ((int)(data.color.getBlue() * 0.6))
                    )
                );
            for(int j = 0; j < data.numPoints; j++) {
                g2.drawRect(
                    (int)data.projectedPoints[j][0] + offX,
                    (int)data.projectedPoints[j][1] + offY,
                    1,
                    1
                );
            }
        }



        paintLock = false;
    }



    public BPanel(BFrame frame) {
        this.frame = frame;
    }
}
