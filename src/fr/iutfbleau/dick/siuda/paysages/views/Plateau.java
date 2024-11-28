package fr.iutfbleau.dick.siuda.paysages.views;

import java.awt.TextArea;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JSplitPane;
import fr.iutfbleau.dick.siuda.paysages.models.Tuile;

/**
 * La classe <code>Plateau</code> représente la fenêtre principale du plateau de jeu.
 * <p>
 * Elle utilise un <code>JSplitPane</code> pour séparer la vue du plateau de jeu et une zone de texte
 * dédiée à l'affichage d'informations ou de journaux.
 * </p>
 *
 * @version 1.0
 * @author Siuda Matéo
 * @author Dick Adrien
 */
public class Plateau extends JFrame {

    /**
     * Le panneau principal du plateau, qui contient les tuiles et gère leur affichage.
     */
    private PlateauPanel panel;

    /**
     * Constructeur de la classe <code>Plateau</code>.
     * <p>
     * Initialise la fenêtre du plateau avec une vue contenant les tuiles fournies. 
     * Utilise un <code>JSplitPane</code> pour séparer la vue principale et une zone de texte.
     * </p>
     *
     * @param tuiles La liste des tuiles à afficher sur le plateau.
     */
    public Plateau(List<Tuile> tuiles) {
        PlateauView view = new PlateauView(tuiles);
        panel = view.getPanel();

        // Configuration du JSplitPane
        JSplitPane jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, view, new TextArea());
        jsp.setDividerLocation(0.67);
        add(jsp);

        // Affiche la fenêtre
        setVisible(true);
    }

    /**
     * Retourne le panneau principal du plateau.
     * <p>
     * Cette méthode permet d'accéder au panneau contenant les tuiles et gérant leur affichage.
     * </p>
     *
     * @return Le panneau principal du plateau.
     */
    public PlateauPanel getPanel() {
        return panel;
    }
}
