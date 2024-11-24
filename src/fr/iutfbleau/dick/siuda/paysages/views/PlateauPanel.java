package fr.iutfbleau.dick.siuda.paysages.views;

import javax.swing.*;

import fr.iutfbleau.dick.siuda.paysages.models.Tuile;

import fr.iutfbleau.dick.siuda.paysages.models.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

public class PlateauPanel extends JPanel {
    private static final int HEX_SIZE = 40; // Taille du côté de l'hexagone
    private static final int BORDER_HEXAGONS = 50; // Nombre d'hexagones entre le centre et le bord
    private static final Color BORDER_COLOR = Color.BLACK; // Couleur des borduresné
    private Point dragStartPoint = null; // Point de départ pour le déplacement
    private List<Point> selectedHexagons = new ArrayList<>(); // Liste pour suivre les hexagones sélectionnés
    private List<Tuile> tuiles;
    private int currentTuile;
    private int index;


    public PlateauPanel(List<Tuile> tuiles) {
        this.tuiles = tuiles;
        this.currentTuile = 0;
    }

    // Méthode pour dessiner un hexagone à une position donnée
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

    // Méthode pour vérifier si un point est à l'intérieur d'un hexagone
    public boolean isPointInHexagon(Point point, int x, int y) {
        Path2D.Double hex = createHexagon(x, y);
        return hex.contains(point);
    }

    // Méthode pour gérer la sélection d'un hexagone
    public void selectHexagon(Point point) {
        int hexHeight = (int) (Math.sqrt(3) * HEX_SIZE);
        Dimension size = getSize();
        if (currentTuile <= 48){
            ++currentTuile;
        }

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

                // Ne pas vérifier l'hexagone central
                if (col == 0 && row == 0)
                    continue;

                // Si le point est à l'intérieur de l'hexagone et cet hexagone est adjacent à un
                // autre posé, on le sélectionne
                if (isPointInHexagon(point, x, y) && isAdjacentToColoredHexagon(x, y, centerX, centerY)) {
                    selectedHexagons.add(new Point(x, y));
                    repaint();
                    return;
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(2));

        int hexHeight = (int) (Math.sqrt(3) * HEX_SIZE);
        Dimension size = getSize();

        int centerX = size.width / 2;
        int centerY = size.height / 2;

        // Ajouter des triangles dans l'hexagone central
        drawHexTriangles(g2d, centerX, centerY, tuiles.get(0));

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
                if (selectedHexagons.contains(new Point(x, y))) {
                    g2d.fill(hex);
                    index = selectedHexagons.indexOf(new Point(x, y));
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

    // Méthode pour dessiner les triangles dans un hexagone
    private void drawHexTriangles(Graphics2D g2d, int x, int y, Tuile tuile) {
        // Coordonnées des sommets de l'hexagone
        double[] xPoints = new double[6];
        double[] yPoints = new double[6];
        int j = 0, lastPoint = -1, slice = 0;
        Terrains terrain = Terrains.values()[0];
        int[] typeTerr = tuile.getRepartitionTerrains();
        for (int i = 0; i < 6; i++) {
            double angle = Math.toRadians(60 * i);
            xPoints[i] = x + HEX_SIZE * Math.cos(angle);
            yPoints[i] = y + HEX_SIZE * Math.sin(angle);
        }

        // Dessiner chaque triangle
        for (int i = 0; i < 6; i++) {
            int next = (i + 1) % 6;
            Polygon triangle = new Polygon();
            triangle.addPoint(x, y); // Centre de l'hexagone
            triangle.addPoint((int) xPoints[i], (int) yPoints[i]); // Premier sommet
            triangle.addPoint((int) xPoints[next], (int) yPoints[next]); // Sommet suivant
            if (slice == 0){
                while (typeTerr[j] == 0 | j == lastPoint)
                    ++j;
                slice = typeTerr[j];
                lastPoint = j;
                terrain = Terrains.values()[j];
            }
            try{
                g2d.setColor(terrain.getColor());
            } catch (NullPointerException ex) {
                //TODO
            }
            --slice;
            // Définir une couleur pour chaque triangle
            g2d.fill(triangle);
            g2d.setColor(BORDER_COLOR);
            g2d.draw(triangle);
        }
        
    }

    // Méthode pour vérifier si un hexagone est adjacent à un hexagone coloré
    private boolean isAdjacentToColoredHexagon(int x, int y, int centerX, int centerY) {
        // Vérifier si l'hexagone est adjacent au centre
        if (Math.abs(x - centerX) <= HEX_SIZE * 3 / 2 && Math.abs(y - centerY) <= (Math.sqrt(3) * HEX_SIZE)) {
            return true;
        }

        // Vérifier si l'hexagone est adjacent à un hexagone sélectionné
        for (Point selectedHex : selectedHexagons) {
            if (Math.abs(x - selectedHex.x) <= HEX_SIZE * 3 / 2
                    && Math.abs(y - selectedHex.y) <= (Math.sqrt(3) * HEX_SIZE)) {
                return true;
            }
        }

        return false;
    }

    public Point getDragStartPoint(){
        return dragStartPoint;
    }

    public void setDragStartPoint(Point p){
        dragStartPoint = p;
    }

    public int getTuilesListSize(){
        return selectedHexagons.size();
    }
}
