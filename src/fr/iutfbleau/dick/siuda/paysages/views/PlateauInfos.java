package fr.iutfbleau.dick.siuda.paysages.views;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import fr.iutfbleau.dick.siuda.paysages.models.PlateauModel;

/**
 * La classe <code>PlateauInfos</code> représente un panneau affichant des informations 
 * supplémentaires sur le plateau de jeu, telles que la prochaine tuile à placer.
 * <p>
 * Ce panneau est utilisé pour fournir des détails contextuels au joueur, et il est
 * mis à jour en fonction de l'état du plateau.
 * </p>
 *
 * @version 1.0
 * @author Siuda Matéo
 * @author Dick Adrien
 */
public class PlateauInfos extends JPanel {

    /**
     * Le panneau principal du plateau, utilisé pour accéder à la prochaine tuile.
     */
    private PlateauPanel panel;

    /**
     * Position X pour dessiner les informations sur le panneau.
     */
    private int x;

    /**
     * Position Y pour dessiner les informations sur le panneau.
     */
    private int y;

    private PlateauModel model;

    /**
     * Constructeur de la classe <code>PlateauInfos</code>.
     * <p>
     * Initialise le panneau des informations avec le panneau principal du plateau 
     * pour accéder à la prochaine tuile et configure les positions pour le dessin.
     * </p>
     *
     * @param panel Le panneau principal du plateau, utilisé pour afficher la prochaine tuile.
     */
    public PlateauInfos(PlateauModel model, PlateauPanel panel) {
        this.model = model;
        this.panel = panel;
        x = 40;
        y = 400;
    }

    /**
     * Redessine les composants graphiques du panneau.
     * <p>
     * Cette méthode affiche le texte "Prochaine tuile :" et dessine graphiquement
     * la prochaine tuile sur le panneau des informations.
     * </p>
     *
     * @param g L'objet <code>Graphics</code> utilisé pour dessiner sur le panneau.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Affiche le texte "Prochaine tuile :"
        g.drawString("Prochaine tuile :", x + 40, y - 50);

        // Dessine la prochaine tuile à partir du panneau principal
        panel.getNextTuile(g2d, x, y);
    }
}