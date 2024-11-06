import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;

public class MouseMotionHandler extends MouseMotionAdapter {
    private final Plateau plateau;

    public MouseMotionHandler(Plateau plateau) {
        this.plateau = plateau;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (plateau.dragStartPoint != null) {
            Point dragEndPoint = e.getPoint();
            JViewport viewport = (JViewport) SwingUtilities.getAncestorOfClass(JViewport.class, plateau);

            if (viewport != null) {
                int deltaX = plateau.dragStartPoint.x - dragEndPoint.x;
                int deltaY = plateau.dragStartPoint.y - dragEndPoint.y;
                Point viewPosition = viewport.getViewPosition();
                viewPosition.translate(deltaX, deltaY);
                plateau.scrollRectToVisible(new Rectangle(viewPosition, viewport.getSize()));
            }
        }
    }
}