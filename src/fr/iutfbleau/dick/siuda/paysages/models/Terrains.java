package fr.iutfbleau.dick.siuda.paysages.models;

import java.awt.Color;

/**
 * L'énumération <code>Terrains</code> représente les différents types de terrains disponibles dans le jeu.
 * <p>
 * Chaque type de terrain est associé à une couleur spécifique qui peut être utilisée pour l'affichage graphique.
 * </p>
 *
 * @version 1.0
 * @author Siuda Matéo
 * @author Dick Adrien
 */
public enum Terrains {

    /**
     * Terrain de type "MER", représenté par la couleur bleue.
     */
    MER(Color.BLUE),

    /**
     * Terrain de type "PRÉ", représenté par une couleur verte claire.
     */
    PRE(new Color(58 , 157 , 35)),

    /**
     * Terrain de type "CHAMPS", représenté par la couleur jaune.
     */
    CHAMPS(new Color(232 , 214 , 48)),

    /**
     * Terrain de type "FORÊT", représenté par une couleur verte foncée.
     */
    FORET(new Color(0, 51, 0)),

    /**
     * Terrain de type "MONTAGNE", représenté par la couleur grise.
     */
    MONTAGNE(Color.GRAY);

    /**
     * La couleur associée à ce type de terrain.
     */
    private Color color;

    /**
     * Constructeur privé de l'énumération <code>Terrains</code>.
     * <p>
     * Initialise le terrain avec la couleur spécifiée.
     * </p>
     *
     * @param color La couleur associée au terrain.
     */
    private Terrains(Color color) {
        this.color = color;
    }

    /**
     * Retourne la couleur associée au terrain.
     *
     * @return La couleur du terrain.
     */
    public Color getColor() {
        return color;
    }
}
