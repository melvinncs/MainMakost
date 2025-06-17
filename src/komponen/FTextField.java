/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package komponen;

import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 *
 * @author DELL
 */
public class FTextField extends JTextField{
    private Color fillColor;
    private Color lineColor;
    private int strokeWIdth;

    public FTextField() {
        fillColor = new Color(236, 240, 241);
        lineColor = new Color(174, 226, 219);
        strokeWIdth = 2;
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
    }
    
    protected void paintComponent(Graphics g){
        if (!isOpaque()) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int  s = strokeWIdth;
            int w = getWidth() - (2 * s);
            int h = getHeight() - (2 * s);
            g2d.setColor(fillColor);
            g2d.fillRoundRect(s, s, w, h, h, h);
            g2d.setStroke(new BasicStroke(s));
            g2d.setColor(lineColor);
            g2d.drawRoundRect(s, s, w, h, h, h);
        }
    super.paintComponent(g);
    }
}
class RoundedBorder implements Border {
    private int radius;

    RoundedBorder(int radius) {
        this.radius = radius;
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
    }

    @Override
    public boolean isBorderOpaque() {
        return true;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.drawRoundRect(x, y, width-1, height-1, radius, radius);
    }
}

