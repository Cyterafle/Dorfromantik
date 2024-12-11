package fr.iutfbleau.dick.siuda.paysages.controllers;
import java.awt.event.*;

import fr.iutfbleau.dick.siuda.paysages.models.Connexion;
import fr.iutfbleau.dick.siuda.paysages.views.Scoreboard;

/**
 * Classe pour gérer le comportement du bouton "Retour" affiché sur la vue Scoreboard
 */
public class ScoreActionListener implements ActionListener {
    /**
     * La vue Scoreboard associée au listener
     */
    private Scoreboard view;

    /**
     * Simple constructeur d'une instance de listener
     * @param view correspond au scoreboard sur lequel agir
     */
    public ScoreActionListener(Scoreboard view){
        this.view = view;
    }


    /**
     * Méthode agissant lorsqu'un clic est effectué sur le bouton évoqué plus haut
     * Il s'agit de fermer la connexion à la base de données (Le fameux singleton utilisé tout le long) ainsi que la vue Scoreboard
     * @param e l'instance qui a capturé l'évènement
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Connexion.getInstance().fermeture();
        view.dispose(); // Ferme la fenêtre des scores
    }
}
