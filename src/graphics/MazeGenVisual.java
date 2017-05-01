package graphics;

import maze.MazeGenerator;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.JComponent;

public class MazeGenVisual extends JComponent {
    public static final int CELL_LENGTH = 10;

    public MazeGenVisual(int rows, int columns) {
        this(new MazeGenerator(rows, columns));
    }

    public MazeGenVisual(MazeGenerator generator) {
        generator_ = generator;
        current_ = new Point(0, 0);
    }

    public void run() {
        while (!generator_.finished()) {
            try {
                Thread.sleep(DRAW_DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            current_ = generator_.step();
            repaint();
        }
    }

    public void finish() {
        generator_.generate();
        current_ = null;
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
                g2.setColor(
                    current_ != null && current_.x == x && current_.y == y
                        ? CURRENT_CELL_COLOR
                        : generator_.maze()[y][x] ? PATH_COLOR
                        : WALL_COLOR);
                g2.fill(cell);
            }
        }
    }

    private MazeGenerator generator_;
    private Point current_;
    private static final Color PATH_COLOR = Color.WHITE;
    private static final Color WALL_COLOR = Color.BLACK;
    private static final Color CURRENT_CELL_COLOR = Color.RED;
    private static final int DRAW_DELAY = 1;
}
