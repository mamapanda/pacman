import entities.Pacman;
import entities.enemies.factories.GhostFactory;
import game.Game;
import graphics.GameVisual;
import misc.Constants;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.WindowEvent;

public class Program {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Heart to Heart sucks.");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().setPreferredSize(
            new Dimension(
                Constants.Graphics.CONTENT_WIDTH,
                Constants.Graphics.CONTENT_HEIGHT));
        frame.pack();

        while (true) {
            Point playerLocation = Constants.Game.PLAYER_START_LOCATION;
            Pacman player = new Pacman(playerLocation.x, playerLocation.y);
            GameVisual game = new GameVisual(new Game(player, new GhostFactory()));

            frame.add(game);
            frame.addKeyListener(game);

            frame.setVisible(true);
            game.run();

            if (!promptContinue()) {
                break;
            }
            frame.remove(game);
            frame.removeKeyListener(game);
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

