package fr.iutfbleau.dick.siuda.paysages.controllers;

import javax.swing.JOptionPane;
import fr.iutfbleau.dick.siuda.paysages.models.PlateauModel;
import fr.iutfbleau.dick.siuda.paysages.views.*;

/**
 * La classe <code>PlateauController</code> gère les interactions entre la vue 
 * (<code>PlateauView</code>) et le modèle (<code>PlateauModel</code>) pour le plateau de jeu.
 * <p>
 * Ce contrôleur initialise les composants nécessaires au fonctionnement du jeu sur le plateau,
 * gère les événements utilisateur (clavier et souris), et coordonne la fin de la partie.
 * </p>
 *
 * @version 1.0
 * @author Siuda Matéo
 * @author Dick Adrien
 */
public class PlateauController {

    /**
     * La vue associée au plateau.
     */
    private PlateauView frame;

    /**
     * Le modèle associé au plateau.
     */
    private PlateauModel model;


    /**
     * Constructeur de la classe <code>PlateauController</code>.
     * <p>
     * Initialise la vue et le modèle pour le plateau de jeu. Configure les gestionnaires d'événements
     * pour la souris et le clavier, et relie le modèle à la vue.
     * </p>
     *
     * @param idSerie L'identifiant de la série à charger pour la partie.
     */
    public PlateauController(int idSerie) {
        model = new PlateauModel(idSerie);
        frame = new PlateauView(model);
        model.setView(frame);
        PanelListener();
        frame.addKeyListener(new KeyHandler(model, this));
    }

    /**
     * Ajoute les gestionnaires d'événements pour la souris sur le panneau du plateau.
     * <p>
     * Configure les gestionnaires pour détecter les clics de souris et les mouvements
     * de glisser-déplacer sur le plateau.
     * </p>
     */
    private void PanelListener() {
        frame.getPanel().addMouseListener(new MouseHandler(frame.getPanel(), this));
        frame.getPanel().addMouseMotionListener(new MouseMotionHandler(frame.getPanel()));
    }

    /**
     * Met à jour l'affichage des informations liées à la tuile active.
     * <p>
     * Cette méthode est appelée pour rafraîchir le panneau d'informations
     * après une interaction utilisateur.
     * </p>
     */
    public void updateTuile() {
        frame.getInfosPanel().repaint();
    }

    /**
     * Met à jour l'affichage des informations liées au plateau.
     * <p>
     * Cette méthode est appelée pour rafraîchir le plateau après une interaction utilisateur.
     * </p>
     */
    public void updatePlateau(){
        model.rechercheVoisins();    
        model.calculerScore();
    }

    /**
     * Renvoie au model le plateau. 
     */
    public PlateauModel getModel(){
        return model;
    }

    /**
     * Termine la partie lorsque toutes les tuiles ont été posées.
     * <p>
     * Affiche un message indiquant la fin de la partie, ferme la fenêtre du plateau,
     * et ouvre le tableau des scores pour la série actuelle.
     * </p>
     */
    public void endGame() {
        JOptionPane.showMessageDialog(frame, "Vous avez posé toutes les tuiles ! Le jeu est terminé !");
        frame.dispose();
        new ScoreController(model.getIdSerie(), model.getScore());
    }
}
