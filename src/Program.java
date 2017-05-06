import entities.EnemyFactory;
import entities.GhostFactory;
import entities.Pacman;
import graphics.GameVisual;
import maze.Game;
import maze.MazeGenerator;
import misc.Constants;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import java.awt.Dimension;
import java.awt.event.WindowEvent;

public class Program {
    public static void main(String[] args) {
        while (true) {
            MazeGenerator generator =
                new MazeGenerator(
                    Constants.Maze.ROWS,
                    Constants.Maze.COLUMNS);
            Pacman player = new Pacman(generator, 0, 0);
            EnemyFactory factory = new GhostFactory(generator, player);

            GameVisual game = new GameVisual(new Game(generator, player, factory));

            JFrame frame = new JFrame("Umi and Daniel.jpg");
            frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            frame.getContentPane().setPreferredSize(
                new Dimension(
                    Constants.Graphics.CONTENT_WIDTH,
                    Constants.Graphics.CONTENT_HEIGHT));
            frame.pack();

            frame.add(game);
            frame.addKeyListener(game);

            frame.setVisible(true);
            game.run();

            if (!promptContinue()) {
                break;
            }
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        }
    }

    private static boolean promptContinue() {
        return JOptionPane.showConfirmDialog(
            null,
            "Do you wish to continue?",
            "Continue?",
            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }
}

