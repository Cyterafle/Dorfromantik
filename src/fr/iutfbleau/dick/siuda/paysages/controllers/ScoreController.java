package fr.iutfbleau.dick.siuda.paysages.controllers;

import fr.iutfbleau.dick.siuda.paysages.models.ScoreModel;
import fr.iutfbleau.dick.siuda.paysages.views.Scoreboard;

/**
 * La classe <code>ScoreController</code> gère l'interaction entre la vue 
 * (<code>Scoreboard</code>) et le modèle (<code>ScoreModel</code>) pour afficher 
 * les scores d'une série donnée.
 * <p>
 * Cette classe initialise le tableau des scores pour une série spécifique et 
 * configure le bouton "Retour" pour fermer la fenêtre.
 * </p>
 *
 * @version 1.0
 * @author Siuda Matéo
 * @author Dick Adrien
 */
public class ScoreController {

    /**
     * La vue associée au tableau des scores.
     */
    private Scoreboard view;

    /**
     * Le modèle fournissant les données des scores.
     */
    private ScoreModel model;

    /**
     * Constructeur de la classe <code>ScoreController</code>.
     * <p>
     * Initialise la vue et le modèle pour afficher les scores d'une série donnée.
     * Ajoute un gestionnaire d'événements au bouton "Retour" pour fermer la fenêtre.
     * </p>
     *
     * @param idSerie L'identifiant de la série pour laquelle les scores doivent être affichés.
     */
    public ScoreController(int idSerie, int score) {
        model = new ScoreModel(score, idSerie);
        view = new Scoreboard(model);

        // Ajout d'un gestionnaire d'événements au bouton "Retour"
        view.getBackButton().addActionListener(new ScoreActionListener(view));
    }
}
