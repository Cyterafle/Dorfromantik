package fr.iutfbleau.dick.siuda.paysages.controllers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import fr.iutfbleau.dick.siuda.paysages.models.PlateauModel;

/**
 * La classe <code>KeyHandler</code> gère les entrées clavier pour manipuler la tuile active
 * dans le jeu.
 * <p>
 * Cette classe implémente l'interface <code>KeyListener</code> pour capturer les événements
 * clavier et effectuer des actions spécifiques, comme la rotation de la tuile courante.
 * </p>
 *
 * @version 1.0
 * @author Siuda Matéo
 * @author Dick Adrien
 */
public class KeyHandler implements KeyListener {

    /**
     * Le modèle du plateau utilisé pour gérer les données et l'état du jeu.
     */
    private PlateauModel model;

    /**
     * Le contrôleur associé qui met à jour l'affichage en fonction des actions clavier.
     */
    private PlateauController controller;

    /**
     * Constructeur de la classe <code>KeyHandler</code>.
     * <p>
     * Initialise le gestionnaire clavier avec le modèle du plateau et le contrôleur associé.
     * </p>
     *
     * @param model Le modèle du plateau, utilisé pour accéder à la tuile active.
     * @param controller Le contrôleur du plateau, utilisé pour mettre à jour l'affichage après une action.
     */
    public KeyHandler(PlateauModel model, PlateauController controller) {
        this.model = model;
        this.controller = controller;
    }

    /**
     * Méthode appelée lorsqu'une touche est enfoncée.
     * <p>
     * Gère les actions suivantes :
     * <ul>
     *   <li>Flèche gauche ou pavé numérique gauche (<code>KeyEvent.VK_LEFT</code>, <code>KeyEvent.VK_KP_LEFT</code>) :
     *       Tourne la tuile active dans le sens inverse des aiguilles d'une montre.</li>
     *   <li>Flèche droite ou pavé numérique droite (<code>KeyEvent.VK_RIGHT</code>, <code>KeyEvent.VK_KP_RIGHT</code>) :
     *       Tourne la tuile active dans le sens des aiguilles d'une montre.</li>
     * </ul>
     * </p>
     *
     * @param e L'événement clavier capturé.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_KP_LEFT:
            case KeyEvent.VK_LEFT:
                model.getNexTuile().setOrientation('-');
                break;

            case KeyEvent.VK_KP_RIGHT:
            case KeyEvent.VK_RIGHT:
                model.getNexTuile().setOrientation('+');
                break;
        }
    }

    /**
     * Méthode appelée lorsqu'une touche est relâchée.
     * <p>
     * Met à jour l'affichage de la tuile active via le contrôleur.
     * </p>
     *
     * @param e L'événement clavier capturé.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        controller.updateTuile();
    }

    /**
     * Méthode appelée lorsqu'une touche est tapée (enfoncée puis relâchée).
     * <p>
     * Non utilisée dans cette implémentation.
     * </p>
     *
     * @param e L'événement clavier capturé.
     */
    @Override
    public void keyTyped(KeyEvent e) {
        // Rien à mettre ici
    }
}
