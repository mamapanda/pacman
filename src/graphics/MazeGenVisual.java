package graphics;

import maze.MazeGenerator;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.JComponent;

/**
 * Graphical maze generation.
 */
public class MazeGenVisual extends JComponent {
    /**
     * The width of a square.
     */
    public static final int CELL_LENGTH = 20;

    /**
     * Constructs a new MazeGenVisual with the given number
     * of rows and columns for the maze
     * @param rows the number of rows in the maze (must be odd)
     * @param columns the number of columns in the maze (must be odd)
     */
    public MazeGenVisual(int rows, int columns) {
        this(new MazeGenerator(rows, columns));
    }

    public MazeGenVisual(MazeGenerator generator) {
        generator_ = generator;
    }

    /**
     * Generates the maze and repaints it after each step.
     */
    public void run() {
        while (generator_.step()) {
            delayedRepaint();
        }
        while (generator_.patchDeadEnd()) {
            delayedRepaint();
        }
        delayedRepaint();
    }

    /**
     * Generates the maze and repaints it after completion.
     */
    public void finish() {
        generator_.generate();
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        for (int y = 0; y < generator_.maze().length; y++) {
            for (int x = 0; x < generator_.maze()[0].length; x++) {
                Rectangle cell = new Rectangle(
                    x * CELL_LENGTH, y * CELL_LENGTH,
                    CELL_LENGTH, CELL_LENGTH);
                g2.setColor(generator_.maze()[y][x] ? PATH_COLOR : WALL_COLOR);
                g2.fill(cell);
            }
        }
    }

    private MazeGenerator generator_;
    private static final Color PATH_COLOR = Color.WHITE;
    private static final Color WALL_COLOR = Color.BLACK;
    private static final int DRAW_DELAY = 5;

    private void delayedRepaint() {
        try {
            Thread.sleep(DRAW_DELAY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        repaint();
    }

}
