package graphics;

import maze.MazeGenerator;
import misc.Constants;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.JComponent;

/**
 * Graphical maze generation.
 */
public class MazeGenVisual extends JComponent {
    /**
     * Constructs a new MazeGenVisual with the given number
     * of rows and columns for the maze
     *
     * @param rows    the number of rows in the maze (must be odd)
     * @param columns the number of columns in the maze (must be odd)
     */
    public MazeGenVisual(int rows, int columns) {
        this(new MazeGenerator(rows, columns));
    }

    public MazeGenVisual(MazeGenerator generator) {
        generator_ = generator;
    }

    public MazeGenerator core() {
        return generator_;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        int cellSize = Constants.Graphics.CELL_SIZE;
        for (int y = 0; y < generator_.maze().length; y++) {
            for (int x = 0; x < generator_.maze()[0].length; x++) {
                Rectangle cell = new Rectangle(
                    x * cellSize, y * cellSize,
                    cellSize, cellSize);
                g2.setColor(
                    generator_.maze()[y][x]
                        ? Constants.TileColors.PATH
                        : Constants.TileColors.WALL);
                g2.fill(cell);
            }
        }
    }

    private MazeGenerator generator_;
}
