package fr.iutfbleau.dick.siuda.paysages.controllers;

import javax.swing.JOptionPane;

import fr.iutfbleau.dick.siuda.paysages.models.PlateauModel;
import fr.iutfbleau.dick.siuda.paysages.views.*;


public class PlateauController {
    private PlateauView frame;
    private PlateauModel model;
    private int idSerie;
    public PlateauController(int idSerie){
        model = new PlateauModel();
        this.idSerie = idSerie;
        frame = new PlateauView(model.recupererTuilesPourSerie(idSerie));
        PanelListener();
    }

    private void PanelListener(){
        frame.getPanel().addMouseListener(new MouseHandler(frame.getPanel(), this));
        frame.getPanel().addMouseMotionListener(new MouseMotionHandler(frame.getPanel()));
    }

    public void endGame(){
        JOptionPane.showMessageDialog(frame, "Vous avez posé toutes les tuiles ! Le jeu est terminé !");
        frame.dispose();
        new ScoreController(idSerie);
    }
}
