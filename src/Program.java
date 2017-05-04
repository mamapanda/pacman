import entities.EnemyFactory;
import entities.GhostFactory;
import entities.Pacman;
import graphics.GameVisual;
import graphics.MazeGenVisual;
import maze.Game;
import maze.MazeGenerator;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.Dimension;

public class Program {
    public static void main(String[] args) {
        int rows = 31;
        int columns = 51;

        MazeGenerator generator = new MazeGenerator(rows, columns);
        Pacman player = new Pacman(generator, 0, 0);
        EnemyFactory factory = new GhostFactory(generator, player);

        GameVisual game = new GameVisual(new Game(generator, player, factory));

        JFrame frame = new JFrame("Umi.jpg");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().setPreferredSize(
            new Dimension(
                generator.maze()[0].length * MazeGenVisual.CELL_LENGTH,
                generator.maze().length * MazeGenVisual.CELL_LENGTH));
        frame.pack();

        frame.add(game);
        frame.addKeyListener(game);

        frame.setVisible(true);
        game.run();
    }
}
