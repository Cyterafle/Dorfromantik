package fr.iutfbleau.dick.siuda.paysages.controllers;

import fr.iutfbleau.dick.siuda.paysages.models.PlateauModel;
import fr.iutfbleau.dick.siuda.paysages.views.*;


public class PlateauController {
    private PlateauView frame;
    private PlateauModel model;
    public PlateauController(){
        frame = new PlateauView();
        model = new PlateauModel(frame);
        PanelListener();
    }

    private void PanelListener(){
        frame.getPanel().addMouseListener(new MouseHandler(frame.getPanel()));
        frame.getPanel().addMouseMotionListener(new MouseMotionHandler(frame.getPanel()));
    }
}
