package fr.iutfbleau.dick.siuda.paysages.controllers;

import fr.iutfbleau.dick.siuda.paysages.views.PlateauPanel;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;

/**
 * La classe <code>MouseMotionHandler</code> gère les interactions de glisser-déplacer
 * avec la souris sur le plateau de jeu.
 * <p>
 * Cette classe permet de faire défiler le contenu du plateau lorsque l'utilisateur
 * clique et maintient le bouton droit de la souris tout en déplaçant la souris.
 * </p>
 *
 * @version 1.0
 * @author Siuda Matéo
 * @author Dick Adrien
 */
public class MouseMotionHandler extends MouseMotionAdapter {

    /**
     * Le panneau du plateau sur lequel les actions de glisser-déplacer sont détectées.
     */
    private final PlateauPanel plateau;

    /**
     * Constructeur de la classe <code>MouseMotionHandler</code>.
     * <p>
     * Initialise le gestionnaire d'événements de mouvement de la souris pour le panneau du plateau.
     * </p>
     *
     * @param plateau Le panneau du plateau sur lequel les interactions sont détectées.
     */
    public MouseMotionHandler(PlateauPanel plateau) {
        this.plateau = plateau;
    }

    /**
     * Méthode appelée lorsque la souris est déplacée avec un bouton enfoncé (glisser-déplacer).
     * <p>
     * Cette méthode gère le défilement du plateau en calculant la distance entre le point 
     * de départ et le point actuel du déplacement, et en mettant à jour la position de la vue.
     * </p>
     *
     * @param e L'événement de mouvement de la souris capturé.
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if (plateau.getDragStartPoint() != null) {
            Point dragEndPoint = e.getPoint();
            JViewport viewport = (JViewport) SwingUtilities.getAncestorOfClass(JViewport.class, plateau);

            if (viewport != null) {
                // Calcul de la distance entre le point de départ et le point actuel
                int deltaX = (int) plateau.getDragStartPoint().getX() - dragEndPoint.x;
                int deltaY = (int) plateau.getDragStartPoint().getY() - dragEndPoint.y;

                // Mise à jour de la position de la vue
                Point viewPosition = viewport.getViewPosition();
                viewPosition.translate(deltaX, deltaY);

                // Scroll pour rendre la nouvelle position visible
                plateau.scrollRectToVisible(new Rectangle(viewPosition, viewport.getSize()));
            }
        }
    }
}
