package fr.iutfbleau.dick.siuda.paysages.views;

import javax.swing.*;

import fr.iutfbleau.dick.siuda.paysages.models.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * La classe <code>PlateauPanel</code> gère l'affichage graphique du plateau de jeu.
 * <p>
 * Elle est responsable du dessin des hexagones représentant les tuiles, 
 * de leur sélection, et de leur mise en surbrillance en fonction des interactions utilisateur.
 * </p>
 *
 * @version 1.0
 * @author Siuda Matéo
 * @author Dick Adrien
 */
public class PlateauPanel extends JPanel {

    /**
     * Taille d'un côté d'un hexagone.
     */
    private static final int HEX_SIZE = 40;

    /**
     * Nombre d'hexagones entre le centre et le bord du plateau.
     */
    private static final int BORDER_HEXAGONS = 50;

    /**
     * Couleur des bordures des hexagones.
     */
    private static final Color BORDER_COLOR = Color.BLACK;

    /**
     * Point de départ pour le déplacement du plateau.
     */
    private Point dragStartPoint = null;

    /**
     * Liste des tuiles du plateau.
     */
    private List<Tuile> tuiles;

    /**
     * Indice
     */
    private int index;

    /**
     * Le modèle du plateau pour interagir avec les données
     */
    private PlateauModel model;

    /**
     * Constructeur de la classe <code>PlateauPanel</code>.
     * <p>
     * Initialise le plateau avec une liste de tuiles.
     * </p>
     *
     * @param tuiles La liste des tuiles à afficher sur le plateau.
     */
    public PlateauPanel(PlateauModel model) {
        this.model = model;
        this.tuiles = model.getTuiles();
    }


    
    /**
     * Crée un hexagone à une position donnée.
     *
     * @param x La coordonnée X du centre de l'hexagone.
     * @param y La coordonnée Y du centre de l'hexagone.
     * @return Un objet <code>Path2D.Double</code> représentant l'hexagone.
     */
    public Path2D.Double createHexagon(int x, int y) {
        Path2D.Double hex = new Path2D.Double();
        for (int i = 0; i < 6; i++) {
            double angle = Math.toRadians(60 * i);
            double dx = x + HEX_SIZE * Math.cos(angle);
            double dy = y + HEX_SIZE * Math.sin(angle);
            if (i == 0) {
                hex.moveTo(dx, dy);
            } else {
                hex.lineTo(dx, dy);
            }
        }
        hex.closePath();
        return hex;
    }

    /**
     * Vérifie si un point est à l'intérieur d'un hexagone donné.
     *
     * @param point Le point à vérifier.
     * @param x La coordonnée X du centre de l'hexagone.
     * @param y La coordonnée Y du centre de l'hexagone.
     * @return <code>true</code> si le point est à l'intérieur de l'hexagone, <code>false</code> sinon.
     */
    public boolean isPointInHexagon(Point point, int x, int y) {
        Path2D.Double hex = createHexagon(x, y);
        return hex.contains(point);
    }

    /**
     * Gère la sélection d'un hexagone.
     * <p>
     * Si l'hexagone est adjacent à un autre déjà coloré et n'a pas été sélectionné auparavant,
     * il est ajouté à la liste des hexagones sélectionnés.
     * </p>
     *
     * @param point Le point où l'utilisateur a cliqué.
     * @return <code>true</code> si un hexagone a été sélectionné, <code>false</code> sinon.
     */
    public boolean selectHexagon(Point point) {
        int hexHeight = (int) (Math.sqrt(3) * HEX_SIZE);
        Dimension size = getSize();

        int centerX = size.width / 2;
        int centerY = size.height / 2;

        // Nombre d'hexagones à afficher horizontalement et verticalement
        int cols = BORDER_HEXAGONS; // Nombre d'hexagones à droite/gauche du centre
        int rows = BORDER_HEXAGONS; // Nombre d'hexagones au-dessus/en-dessous du centre

        // Vérifier si le point est dans un des hexagones
        for (int row = -rows; row <= rows; row++) {
            for (int col = -cols; col <= cols; col++) {
                // Calculer la position de chaque hexagone
                int x = centerX + col * HEX_SIZE * 3 / 2;
                int y = centerY + row * hexHeight + (col % 2) * (hexHeight / 2);

                // Ne pas vérifier les hexagones placés
                if (col == 0 && row == 0 || model.getSelectedHexagons().contains(new Point(x, y)))
                    continue;

                // Si le point est à l'intérieur de l'hexagone et cet hexagone est adjacent à un
                // autre posé, on le sélectionne
                if (isPointInHexagon(point, x, y) && isAdjacentToColoredHexagon(x, y, centerX, centerY)) {
                    model.getSelectedHexagons().add(new Point(x, y));
                    if (model.getCurrentTuile() <= 48){
                        model.incrementCurrentTuile();
                    }
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Redessine les composants graphiques du plateau.
     * <p>
     * Affiche les hexagones dans une grille et applique des couleurs en fonction de leur état
     * (sélectionné, adjacent, etc.).
     * </p>
     *
     * @param g L'objet <code>Graphics</code> utilisé pour dessiner.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Tuile tuileInit = tuiles.get(0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(2));

        int hexHeight = (int) (Math.sqrt(3) * HEX_SIZE);
        Dimension size = getSize();

        int centerX = size.width / 2;
        int centerY = size.height / 2;

        // Ajouter des triangles dans l'hexagone central
        drawHexTriangles(g2d, centerX, centerY, tuileInit);
        if (tuileInit.getCenterPoint() == null)
            tuileInit.setCenterPoint(new Point(centerX, centerY));

        // Nombre d'hexagones à afficher horizontalement et verticalement
        int cols = BORDER_HEXAGONS; // Nombre d'hexagones à droite/gauche du centre
        int rows = BORDER_HEXAGONS; // Nombre d'hexagones au-dessus/en-dessous du centre

        // Dessiner les hexagones dans une grille
        for (int row = -rows; row <= rows; row++) {
            for (int col = -cols; col <= cols; col++) {
                // Calculer la position de chaque hexagone
                int x = centerX + col * HEX_SIZE * 3 / 2;
                int y = centerY + row * hexHeight + (col % 2) * (hexHeight / 2);

                // Ne pas redessiner l'hexagone central
                if (col == 0 && row == 0)
                    continue;

                Path2D.Double hex = createHexagon(x, y);

                // Si cet hexagone est sélectionné, on le colorie en bleu
                if (model.getSelectedHexagons().contains(new Point(x, y))) {
                    g2d.fill(hex);
                    index = model.getSelectedHexagons().indexOf(new Point(x, y));
                    if (index < 49)
                        drawHexTriangles(g2d, x, y, tuiles.get(index+1));
                }

                // Si cet hexagone est adjacent à un hexagone coloré (central ou sélectionné),
                // afficher uniquement le contour
                if (isAdjacentToColoredHexagon(x, y, centerX, centerY)) {
                    g2d.setColor(BORDER_COLOR);
                    g2d.draw(hex);
                }
            }
        }
    }

    /**
     * Dessine les triangles à l'intérieur d'un hexagone en fonction des terrains d'une tuile.
     *
     * @param g2d L'objet <code>Graphics2D</code> utilisé pour dessiner.
     * @param x La coordonnée X du centre de l'hexagone.
     * @param y La coordonnée Y du centre de l'hexagone.
     * @param tuile La tuile dont les terrains sont utilisés pour colorer les triangles.
     */
    private void drawHexTriangles(Graphics2D g2d, int x, int y, Tuile tuile) {
        // Coordonnées des sommets de l'hexagone
        double[] xPoints = new double[6];
        double[] yPoints = new double[6];
        Terrains[] terrains = tuile.getRepartitionTerrains();
        for (int i = 0; i < 6; i++) {
            double angle = Math.toRadians(60 * i);
            xPoints[i] = x + HEX_SIZE * Math.cos(angle);
            yPoints[i] = y + HEX_SIZE * Math.sin(angle);
        }

        // Dessiner chaque triangle
        for (int i = 0; i < 6; i++) {
            int next = (i + 1)%6;
            Polygon triangle = new Polygon();
            triangle.addPoint(x, y); // Centre de l'hexagone
            triangle.addPoint((int) xPoints[i], (int) yPoints[i]); // Premier sommet
            triangle.addPoint((int) xPoints[next], (int) yPoints[next]); // Sommet suivant
            g2d.setColor(terrains[i].getColor());
            // Définir une couleur pour chaque triangle
            g2d.fill(triangle);
            g2d.setColor(BORDER_COLOR);
            g2d.draw(triangle);
        }
    }

    /**
     * Vérifie si un hexagone est adjacent à un autre hexagone coloré.
     *
     * @param x La coordonnée X de l'hexagone à vérifier.
     * @param y La coordonnée Y de l'hexagone à vérifier.
     * @param centerX La coordonnée X du centre du plateau.
     * @param centerY La coordonnée Y du centre du plateau.
     * @return <code>true</code> si l'hexagone est adjacent, <code>false</code> sinon.
     */
    private boolean isAdjacentToColoredHexagon(int x, int y, int centerX, int centerY) {
        // Vérifier si l'hexagone est adjacent au centre
        if (Math.abs(x - centerX) <= HEX_SIZE * 3 / 2 && Math.abs(y - centerY) <= (Math.sqrt(3) * HEX_SIZE)) {
            return true;
        }

        // Vérifier si l'hexagone est adjacent à un hexagone sélectionné
        for (Point selectedHex : model.getSelectedHexagons()) {
            if (Math.abs(x - selectedHex.x) <= HEX_SIZE * 3 / 2
                    && Math.abs(y - selectedHex.y) <= (Math.sqrt(3) * HEX_SIZE)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Retourne le point de départ du déplacement.
     *
     * @return Le point de départ pour le déplacement.
     */
    public Point getDragStartPoint() {
        return dragStartPoint;
    }

    /**
     * Définit le point de départ du déplacement.
     *
     * @param p Le nouveau point de départ.
     */
    public void setDragStartPoint(Point p) {
        dragStartPoint = p;
    }

    

    /**
     * Dessine la prochaine tuile sur un hexagone donné.
     *
     * @param g L'objet <code>Graphics2D</code> utilisé pour dessiner.
     * @param x La coordonnée X de l'hexagone.
     * @param y La coordonnée Y de l'hexagone.
     */
    public void getNextTuile(Graphics2D g, int x, int y){
        int currentTuile;
        createHexagon(x, y);
        if ((currentTuile = model.getCurrentTuile()) <= 48){
            drawHexTriangles(g, x, y, tuiles.get(currentTuile+1));
        }else{
            drawHexTriangles(g, x, y, tuiles.get(currentTuile));
        }
    }

    
}
