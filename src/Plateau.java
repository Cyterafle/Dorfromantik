import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;

public class Plateau extends JPanel {
    private static final int HEX_SIZE = 40; // Taille du côté de l'hexagone
    private static final int BORDER_HEXAGONS = 50; // Nombre d'hexagones entre le centre et le bord
    private static final Color BORDER_COLOR = Color.BLACK; // Couleur des bordures
    private static final Color CENTER_COLOR = Color.RED; // Couleur de l'hexagone central

    // Méthode pour dessiner un hexagone à une position donnée
    private Path2D.Double createHexagon(int x, int y) {
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(2));

        // Taille d'un hexagone en hauteur
        int hexHeight = (int) (Math.sqrt(3) * HEX_SIZE);
        Dimension size = getSize();
        
        // Nombre d'hexagones nécessaires pour couvrir la largeur et la hauteur de l'écran
        int columns = size.width / (HEX_SIZE * 3 / 2) + 2;
        int rows = size.height / hexHeight + 2;

        // Calculer le centre de la zone de dessin
        int centerX = size.width / 2;
        int centerY = size.height / 2;

        // Dessiner les hexagones dans une zone défilante
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                int x = col * (HEX_SIZE * 3 / 2);
                int y = row * hexHeight + (col % 2) * (hexHeight / 2);

                Path2D.Double hex = createHexagon(x, y);

                // Vérifier si cet hexagone est au centre
                if (Math.abs(x - centerX) < HEX_SIZE && Math.abs(y - centerY) < hexHeight / 2) {
                    g2d.setColor(CENTER_COLOR); // Couleur rouge pour l'hexagone central
                    g2d.fill(hex); // Remplir l'hexagone central en couleur
                }

                g2d.setColor(BORDER_COLOR);
                g2d.draw(hex);
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Infinite Hexagonal Grid with Center Hexagon");
        Plateau hexGrid = new Plateau();

        // Calcul de la taille du plateau pour que l'hexagone central soit à 50 hexagones d'un bord
        int preferredWidth = (2 * BORDER_HEXAGONS + 1) * (int) (HEX_SIZE * 3 / 2);
        int preferredHeight = (2 * BORDER_HEXAGONS + 1) * (int) (Math.sqrt(3) * HEX_SIZE);
        hexGrid.setPreferredSize(new Dimension(preferredWidth, preferredHeight));

        // Utilisation d'un JScrollPane pour permettre le défilement
        JScrollPane scrollPane = new JScrollPane(hexGrid);

        frame.add(scrollPane);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
