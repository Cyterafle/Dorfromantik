package fr.iutfbleau.dick.siuda.paysages.controllers;

import fr.iutfbleau.dick.siuda.paysages.views.PlateauPanel;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;

public class MouseMotionHandler extends MouseMotionAdapter {
    private final PlateauPanel plateau;

    public MouseMotionHandler(PlateauPanel plateau) {
        this.plateau = plateau;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (plateau.getDragStartPoint() != null) {
            Point dragEndPoint = e.getPoint();
            JViewport viewport = (JViewport) SwingUtilities.getAncestorOfClass(JViewport.class, plateau);

            if (viewport != null) {
                int deltaX = (int) plateau.getDragStartPoint().getX() - dragEndPoint.x;
                int deltaY = (int) plateau.getDragStartPoint().getY() - dragEndPoint.y;
                Point viewPosition = viewport.getViewPosition();
                viewPosition.translate(deltaX, deltaY);
                plateau.scrollRectToVisible(new Rectangle(viewPosition, viewport.getSize()));
            }
        }
    }
}