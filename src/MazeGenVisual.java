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
        current_ = new Point(-1, -1);
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        for (int i = 0; i < generator_.maze().length; i++) {
            for (int j = 0; j < generator_.maze()[0].length; j++) {
                Rectangle cell = new Rectangle(
                        i * CELL_LENGTH, j * CELL_LENGTH,
                        CELL_LENGTH, CELL_LENGTH);
                g2.setColor(current_.x == i && current_.y == j ? CURRENT_CELL_COLOR
                        : generator_.maze()[i][j] ? PATH_COLOR
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
    private static final int DRAW_DELAY = 25;
}
