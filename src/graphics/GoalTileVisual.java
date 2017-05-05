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
        int add = MazeGenVisual.CELL_LENGTH / 8;
        for (Point location : goalTiles_) {
            g2.fill(
                new Rectangle(
                    (location.x * MazeGenVisual.CELL_LENGTH) + add,
                    (location.y * MazeGenVisual.CELL_LENGTH) + add,
                    MazeGenVisual.CELL_LENGTH * 3 / 4,
                    MazeGenVisual.CELL_LENGTH * 3 / 4));
        }
    }

    @Override
    public String toString() {
        return goalTiles_.toString();
    }

    private List<Point> goalTiles_;
    private final Color SPECIAL_COLOR = Color.RED;
}
