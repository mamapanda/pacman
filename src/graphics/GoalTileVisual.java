package graphics;

import constants.Constants;

import javax.swing.JComponent;
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
        g2.setColor(Constants.TileColors.SPECIAL);
        int cellSize = Constants.Graphics.CELL_SIZE;
        int offset = cellSize / 8;
        for (Point location : goalTiles_) {
            g2.fill(
                new Rectangle(
                    (location.x * cellSize) + offset,
                    (location.y * cellSize) + offset,
                    cellSize * 3 / 4,
                    cellSize * 3 / 4));
        }
    }

    @Override
    public String toString() {
        return goalTiles_.toString();
    }

    private List<Point> goalTiles_;
}
