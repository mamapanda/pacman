import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Program {
    public static void main(String[] args) {
        int rows = 51;
        int columns = 51;

        JFrame frame = new JFrame("Keni Leni is a god.");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Dimension contentDimensions = new Dimension(
                rows * MazeGenVisual.CELL_LENGTH,
                columns * MazeGenVisual.CELL_LENGTH);
        frame.getContentPane().setPreferredSize(contentDimensions);
        frame.pack();
        frame.setVisible(true);

        MazeGenVisual visual = new MazeGenVisual(rows, columns);
        frame.add(visual);
        visual.run();
    }
}
