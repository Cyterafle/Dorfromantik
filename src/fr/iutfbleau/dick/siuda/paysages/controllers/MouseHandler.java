package fr.iutfbleau.dick.siuda.paysages.controllers;


import fr.iutfbleau.dick.siuda.paysages.views.PlateauPanel;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;

public class MouseHandler extends MouseAdapter {
    private final PlateauPanel plateau;
    private final PlateauController controller;

    public MouseHandler(PlateauPanel plateau, PlateauController controller) {
        this.plateau = plateau;
        this.controller = controller;
    }

    @Override
    public void mousePressed(MouseEvent e) { 

        if (SwingUtilities.isRightMouseButton(e)) {
            plateau.setDragStartPoint(e.getPoint());
            plateau.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
        }

        if (SwingUtilities.isLeftMouseButton(e)) {
            if (plateau.selectHexagon(e.getPoint())){
                controller.updateTuile();
            } // Sélectionne l'hexagone cliqué
            if (plateau.getTuilesListSize() == 49){
                controller.endGame();
            }
            
        }
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
            plateau.setDragStartPoint(null);
            plateau.setCursor(Cursor.getDefaultCursor());
        }
    }
}