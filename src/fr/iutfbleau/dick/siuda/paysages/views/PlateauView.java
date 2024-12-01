package fr.iutfbleau.dick.siuda.paysages.views;

import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import java.util.List;

import fr.iutfbleau.dick.siuda.paysages.models.PlateauModel;
import fr.iutfbleau.dick.siuda.paysages.models.Tuile;

/**
 * La classe <code>PlateauView</code> représente la vue principale du plateau de jeu.
 * <p>
 * Cette classe configure l'interface utilisateur, divisant la fenêtre en deux parties :
 * <ul>
 *   <li>Un panneau principal (<code>PlateauPanel</code>) pour afficher les tuiles hexagonales.</li>
 *   <li>Un panneau d'informations (<code>PlateauInfos</code>) pour afficher la prochaine tuile.</li>
 * </ul>
 * </p>
 *
 * @version 1.0
 * @author Siuda Matéo
 * @author Dick Adrien
 */
public class PlateauView extends JFrame {

    /**
     * Le panneau principal affichant les hexagones du plateau.
     */
    private PlateauPanel panel;

    /**
     * Le panneau affichant les informations sur la prochaine tuile.
     */
    private PlateauInfos infos;

    /**
     * Taille d'un côté de l'hexagone, utilisé pour calculer les dimensions.
     */
    private static final int HEX_SIZE = 40;

    /**
     * Nombre d'hexagones entre le centre et le bord du plateau.
     */
    private static final int BORDER_HEXAGONS = 50;

    /**
     * Constructeur de la classe <code>PlateauView</code>.
     * <p>
     * Initialise la fenêtre principale du plateau en configurant :
     * <ul>
     *   <li>Un panneau principal contenant les hexagones.</li>
     *   <li>Un panneau d'informations affichant la prochaine tuile.</li>
     *   <li>Un <code>JSplitPane</code> pour séparer les deux panneaux.</li>
     * </ul>
     * </p>
     *
     * @param tuiles La liste des tuiles à afficher sur le plateau.
     */
    public PlateauView(PlateauModel model, List<Tuile> tuiles) {
        panel = new PlateauPanel(model, tuiles);
        infos = new PlateauInfos(model, panel);

        this.setTitle("Dorfromantik - Plateau");

        // Configuration des dimensions préférées du plateau
        int preferredWidth = (2 * BORDER_HEXAGONS + 1) * (int) (HEX_SIZE * 3 / 2);
        int preferredHeight = (2 * BORDER_HEXAGONS + 1) * (int) (Math.sqrt(3) * HEX_SIZE);
        panel.setPreferredSize(new Dimension(preferredWidth, preferredHeight));
        infos.setPreferredSize(new Dimension(200, MAXIMIZED_VERT));

        // Ajout d'un panneau déroulant pour le plateau
        JScrollPane scrollPane = new JScrollPane(panel);
        int centerX = (preferredWidth / 2) - (800 / 2);
        int centerY = (preferredHeight / 2) - (600 / 2);
        scrollPane.getViewport().setViewPosition(new Point(centerX, centerY));
        scrollPane.setPreferredSize(new Dimension(preferredWidth, preferredHeight));

        // Configuration du JSplitPane pour séparer le plateau et les infos
        JSplitPane jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane, infos);
        jsp.setResizeWeight(0.8);
        add(jsp);

        // Configuration de la fenêtre
        setVisible(true);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setSize(1200, 800);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    /**
     * Retourne le panneau principal affichant les hexagones.
     *
     * @return Le panneau principal du plateau.
     */
    public PlateauPanel getPanel() {
        return panel;
    }

    /**
     * Retourne le panneau d'informations affichant la prochaine tuile.
     *
     * @return Le panneau d'informations.
     */
    public PlateauInfos getInfosPanel() {
        return infos;
    }
}
