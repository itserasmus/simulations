package com.mystical;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Random;

import javax.swing.JFrame;

import com.mystical.core.MysticData;
import com.mystical.core.MysticMaster;


public class BFrame extends JFrame {
    public BPanel panel;
    public MysticMaster master;
    public int id;

    public void maximize() {
        setExtendedState(MAXIMIZED_BOTH);
    }
    public void minimize() {
        setExtendedState(ICONIFIED);
    }
    public void restore() {
        setExtendedState(NORMAL);
    }

    public void init() {
        int x = (int)(new Random().nextInt(Toolkit.getDefaultToolkit().getScreenSize().width - 800)),
            y = (int)(new Random().nextInt(Toolkit.getDefaultToolkit().getScreenSize().height - 600));
        master.datas.add(new MysticData(
            1000,
            250,
            x + 800/2,
            y + 600/2,
            new Color((int)(Math.random()*0x2F2F2F) + 0x707070),
            this
        ));
        panel = new BPanel(this);
        panel.init();

        setResizable(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        add(panel);
        setPreferredSize(new Dimension(800, 600));
        pack();
        setLocation(x, y);
        setVisible(true);

        addMouseListener(master);
        addWindowListener(master.datas.get(master.datas.size() - 1));
    }

    public BFrame(MysticMaster master) {
        this.master = master;
    }
}
