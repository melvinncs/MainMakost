/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package komponen;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JButton;

/**
 *
 * @author DELL
 */
public class FButton extends JButton {
    private boolean  over;
    private Color fill;
    
    private Color fillOriginal;
    private Color fillclick;
    private Color fillOver;
    private int StrokeWIdth;

    public FButton() {
        fillOriginal = new Color(52, 152, 215);
        fillOver = new Color(41, 128, 165);
        fillclick = new Color(174, 226, 255);
        fill = fillOriginal;
        StrokeWIdth= 2 ;
        setOpaque(false);
        setBorder(null);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBackground(fillOriginal);
        setForeground(Color.WHITE);
    }
    
    protected void paintComponent(Graphics g){
        if (!isOpaque()) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int  s = StrokeWIdth;
            int w = getWidth() - (2 * s);
            int h = getHeight() - (2 * s);
            g2d.setColor(fill);
            g2d.fillRoundRect(s, s, w, h, h, h);
            g2d.setStroke(new BasicStroke(s));
            g2d.setColor(fill);
            g2d.drawRoundRect(s, s, w, h, h, h);
        }
    super.paintComponent(g);
    }
}


