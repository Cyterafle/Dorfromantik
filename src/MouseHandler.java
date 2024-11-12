import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;

public class MouseHandler extends MouseAdapter {
    private final Plateau plateau;

    public MouseHandler(Plateau plateau) {
        this.plateau = plateau;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
            plateau.dragStartPoint = e.getPoint();
            plateau.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
        }

        if (SwingUtilities.isLeftMouseButton(e)) {
            plateau.selectHexagon(e.getPoint());  // Sélectionne l'hexagone cliqué
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
            plateau.dragStartPoint = null;
            plateau.setCursor(Cursor.getDefaultCursor());
        }
    }
}