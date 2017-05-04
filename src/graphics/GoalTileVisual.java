package graphics;

import javax.swing.JComponent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;

public class GoalTileVisual extends JComponent {
    public GoalTileVisual(List<Point> goalTiles) {
        goalTiles_ = goalTiles;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(SPECIAL_COLOR);
        for (Point location : goalTiles_) {
            g2.fill(
                new Rectangle(
                    location.x,
                    location.y,
                    MazeGenVisual.CELL_LENGTH,
                    MazeGenVisual.CELL_LENGTH));
        }
    }

    private List<Point> goalTiles_;
    private final Color SPECIAL_COLOR = Color.RED;
}
