import graphics.MazeGenVisual;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Program {
    public static void main(String[] args) {
        int rows = 31;
        int columns = 51;

        JFrame frame = new JFrame("Umi.jpg");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Dimension contentDimensions = new Dimension(
            columns * MazeGenVisual.CELL_LENGTH,
            rows * MazeGenVisual.CELL_LENGTH);
        frame.getContentPane().setPreferredSize(contentDimensions);
        frame.pack();

        MazeGenVisual visual = new MazeGenVisual(rows, columns);
        frame.add(visual);
        frame.setVisible(true);

        visual.run();
    }
}
