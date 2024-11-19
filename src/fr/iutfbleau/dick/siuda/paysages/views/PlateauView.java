package fr.iutfbleau.dick.siuda.paysages.views;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class PlateauView extends JFrame {
    private PlateauPanel panel;
    private static final int HEX_SIZE = 40; // Taille du côté de l'hexagone
    private static final int BORDER_HEXAGONS = 50;
    public PlateauView(){
        panel = new PlateauPanel();
        this.setTitle("Dorfromantik - Plateau");
        this.add(panel);
        int preferredWidth = (2 * BORDER_HEXAGONS + 1) * (int) (HEX_SIZE * 3 / 2);
        int preferredHeight = (2 * BORDER_HEXAGONS + 1) * (int) (Math.sqrt(3) * HEX_SIZE);
        panel.setPreferredSize(new Dimension(preferredWidth, preferredHeight));
        JScrollPane scrollPane = new JScrollPane(panel);
        int centerX = (preferredWidth / 2) - (800 / 2);
        int centerY = (preferredHeight / 2) - (600 / 2);
        scrollPane.getViewport().setViewPosition(new Point(centerX, centerY));
        this.add(scrollPane);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public PlateauPanel getPanel(){
        return panel;
    }
}
