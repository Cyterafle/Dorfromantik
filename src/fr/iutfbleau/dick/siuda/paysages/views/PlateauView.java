package fr.iutfbleau.dick.siuda.paysages.views;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import java.util.List;
import fr.iutfbleau.dick.siuda.paysages.models.*;

public class PlateauView extends JFrame {
    private PlateauPanel panel;
    private static final int HEX_SIZE = 40; // Taille du côté de l'hexagone
    private static final int BORDER_HEXAGONS = 50;
    public PlateauView(List<Tuile> tuiles){
        panel = new PlateauPanel(tuiles);
        this.setTitle("Dorfromantik - Plateau");
        //this.add(panel);
        int preferredWidth = (2 * BORDER_HEXAGONS + 1) * (int) (HEX_SIZE * 3 / 2);
        int preferredHeight = (2 * BORDER_HEXAGONS + 1) * (int) (Math.sqrt(3) * HEX_SIZE);
        panel.setPreferredSize(new Dimension(preferredWidth, preferredHeight));
        JScrollPane scrollPane = new JScrollPane(panel);
        int centerX = (preferredWidth / 2) - (800 / 2);
        int centerY = (preferredHeight / 2) - (600 / 2);
        scrollPane.getViewport().setViewPosition(new Point(centerX, centerY));
        JSplitPane jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane, new TextArea());
        jsp.setDividerLocation(0.33);
        add(jsp);
        setVisible(true);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setSize(800, 800);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public PlateauPanel getPanel(){
        return panel;
    }
}
