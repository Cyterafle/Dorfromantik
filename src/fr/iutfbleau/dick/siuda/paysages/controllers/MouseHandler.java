package fr.iutfbleau.dick.siuda.paysages.controllers;

import fr.iutfbleau.dick.siuda.paysages.views.PlateauPanel;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;

/**
 * La classe <code>MouseHandler</code> gère les interactions utilisateur avec la souris
 * sur le plateau de jeu.
 * <p>
 * Cette classe permet de détecter les clics gauche et droit, et de déclencher les actions
 * associées, comme la sélection d'un hexagone ou le déplacement du plateau.
 * </p>
 *
 * @version 1.0
 * @author Siuda Matéo
 * @author Dick Adrien
 */
public class MouseHandler extends MouseAdapter {

    /**
     * Le panneau du plateau sur lequel les actions de la souris sont détectées.
     */
    private final PlateauPanel plateau;

    /**
     * Le contrôleur du plateau utilisé pour effectuer des actions en fonction des interactions utilisateur.
     */
    private final PlateauController controller;

    /**
     * Constructeur de la classe <code>MouseHandler</code>.
     * <p>
     * Initialise le gestionnaire d'événements souris avec le panneau du plateau et le contrôleur associé.
     * </p>
     *
     * @param plateau Le panneau du plateau où les actions de la souris sont détectées.
     * @param controller Le contrôleur associé, utilisé pour déclencher les mises à jour et les actions du jeu.
     */
    public MouseHandler(PlateauPanel plateau, PlateauController controller) {
        this.plateau = plateau;
        this.controller = controller;
    }

    /**
     * Méthode appelée lorsqu'un bouton de la souris est pressé.
     * <p>
     * Les actions prises en charge incluent :
     * <ul>
     *   <li>Clic droit : Début du déplacement du plateau (drag) avec un changement de curseur.</li>
     *   <li>Clic gauche : Sélection d'un hexagone et mise à jour de la tuile active.</li>
     * </ul>
     * Si 49 tuiles sont placées, la partie est terminée via le contrôleur.
     * </p>
     *
     * @param e L'événement de la souris capturé.
     */
    @Override
    public void mousePressed(MouseEvent e) { 
        if (SwingUtilities.isRightMouseButton(e)) {
            plateau.setDragStartPoint(e.getPoint());
            plateau.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
        }

        if (SwingUtilities.isLeftMouseButton(e)) {
            if (plateau.selectHexagon(e.getPoint())) {
                plateau.repaint();
                if (controller.getModel().getSelectedHexagonsSize() == 49) {
                    controller.endGame(); // Termine la partie si 50 tuiles sont placées
                }
            }
        }
    }

    /**
     * Méthode appelée lorsqu'un bouton de la souris est relâché.
     * <p>
     * Lorsqu'un clic droit est relâché, le curseur revient à son état par défaut 
     * et le déplacement (drag) est terminé.
     * </p>
     *
     * @param e L'événement de la souris capturé.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
            plateau.setDragStartPoint(null);
            plateau.setCursor(Cursor.getDefaultCursor());
        }

        if (SwingUtilities.isLeftMouseButton(e)) {
            controller.updatePlateau();
        }
    }
}
