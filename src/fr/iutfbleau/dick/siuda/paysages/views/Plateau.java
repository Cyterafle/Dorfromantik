package fr.iutfbleau.dick.siuda.paysages.views;

import java.awt.TextArea;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JSplitPane;
import fr.iutfbleau.dick.siuda.paysages.models.Tuile;

public class Plateau extends JFrame {
    private PlateauPanel panel;
    public Plateau(List<Tuile> tuiles){
        PlateauView view = new PlateauView(tuiles);
        panel = view.getPanel();
        JSplitPane jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, view, new TextArea());
        jsp.setDividerLocation(0.67);
        add(jsp);
        setVisible(true);
    }

    public PlateauPanel getPanel(){
        return panel;
    }
}
