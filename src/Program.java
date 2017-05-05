import entities.EnemyFactory;
import entities.GhostFactory;
import entities.Pacman;
import graphics.GameVisual;
import graphics.MazeGenVisual;
import maze.Game;
import maze.MazeGenerator;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.event.WindowEvent;

public class Program {

    public static void main(String[] args) {
        while (true) {
            int rows = 31;
            int columns = 51;

            MazeGenerator generator = new MazeGenerator(rows, columns);
            Pacman player = new Pacman(generator, 0, 0);
            EnemyFactory factory = new GhostFactory(generator, player);

            GameVisual game = new GameVisual(new Game(generator, player, factory));

            JFrame frame = new JFrame("Umi.jpg");
            frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            frame.getContentPane().setPreferredSize(
                new Dimension(
                    generator.maze()[0].length * MazeGenVisual.CELL_LENGTH,
                    generator.maze().length * MazeGenVisual.CELL_LENGTH));
            frame.pack();

            frame.add(game);
            frame.addKeyListener(game);

            frame.setVisible(true);
            game.run();

            if (promptContinue()) {
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            } else {
                break;
            }
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

