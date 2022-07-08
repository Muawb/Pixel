package me.muawb.project.utils;

import javax.swing.*;
import java.awt.*;

public class BarPainter implements Painter<JProgressBar> {

    private final Color color;

    public BarPainter(Color c){
        this.color = c;
    }

    @Override
    public void paint(Graphics2D g, JProgressBar object, int width, int height) {
        g.setColor(color);
        g.fillRect(0,0, width, height);
    }
}
