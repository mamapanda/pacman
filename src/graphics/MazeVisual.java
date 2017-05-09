package graphics;

import game.Maze;
import misc.Constants;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.JComponent;

public class MazeVisual extends JComponent {
    public MazeVisual(Maze maze) {
        maze_ = maze;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        int cellSize = Constants.Graphics.CELL_SIZE;

        for (int y = 0; y < Constants.Game.ROWS; y++) {
            for (int x = 0; x < Constants.Game.COLUMNS; x++) {
                Rectangle cell = new Rectangle(
                    x * cellSize, y * cellSize,
                    cellSize, cellSize);
                g2.setColor(
                    maze_.tiles()[y][x]
                        ? Constants.TileColors.PATH
                        : Constants.TileColors.WALL);
                g2.fill(cell);
            }
        }
    }

    private Maze maze_;
}
