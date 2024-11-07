import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;

public class Plateau extends JPanel {
    public static final int HEX_SIZE = 40; // Taille du côté de l'hexagone
    public static final int BORDER_HEXAGONS = 50; // Nombre d'hexagones entre le centre et le bord
    private static final Color BORDER_COLOR = Color.BLACK; // Couleur des bordures
    private static final Color CENTER_COLOR = Color.BLUE; // Couleur de l'hexagone central

    Point dragStartPoint = null; // Point de départ pour le déplacement

    public Plateau() {
        // Ajoute les écouteurs pour gérer le clic et le déplacement
        addMouseListener(new MouseHandler(this));
        addMouseMotionListener(new MouseMotionHandler(this));
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

    @Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.setStroke(new BasicStroke(2));

    int hexHeight = (int) (Math.sqrt(3) * HEX_SIZE);
    Dimension size = getSize();

    int centerX = size.width / 2;
    int centerY = size.height / 2;

    // Dessiner et remplir l'hexagone central en rouge
    g2d.setColor(CENTER_COLOR);
    Path2D.Double centerHex = createHexagon(centerX, centerY);
    g2d.fill(centerHex);
    g2d.setColor(BORDER_COLOR);
    g2d.draw(centerHex);

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
            if (col == 0 && row == 0) continue;

            Path2D.Double hex = createHexagon(x, y);
            g2d.setColor(BORDER_COLOR);
            g2d.draw(hex);
        }
    }
}



    public static void main(String[] args) {
        JFrame frame = new JFrame("Dorfromantik");
        Plateau hexGrid = new Plateau();

        int preferredWidth = (2 * BORDER_HEXAGONS + 1) * (int) (HEX_SIZE * 3 / 2);
        int preferredHeight = (2 * BORDER_HEXAGONS + 1) * (int) (Math.sqrt(3) * HEX_SIZE);
        hexGrid.setPreferredSize(new Dimension(preferredWidth, preferredHeight));

        JScrollPane scrollPane = new JScrollPane(hexGrid);

        int centerX = (preferredWidth / 2) - (800 / 2);
        int centerY = (preferredHeight / 2) - (600 / 2);
        scrollPane.getViewport().setViewPosition(new Point(centerX, centerY));

        frame.add(scrollPane);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
