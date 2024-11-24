package fr.iutfbleau.dick.siuda.paysages.views;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;


public class PlateauInfos extends JPanel {
    private PlateauPanel panel;
    private int x;
    private int y;
    public PlateauInfos(PlateauPanel panel){
        this.panel = panel;
        x = 40;
        y = 400;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g.drawString("Prochaine tuile :", x + 40, y - 50);
        panel.getNextTuile(g2d, x, y);
    }
}
