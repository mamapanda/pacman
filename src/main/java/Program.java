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

public class Program {
    public static void main(String[] args) {
        new Program().run();
    }

    public Program() {
        initFrame();
    }

    public void run() {
        while (true) {
            Point playerLocation = Constants.Game.PLAYER_START_LOCATION;
            Pacman player = new Pacman(playerLocation.x, playerLocation.y);
            GameVisual game = new GameVisual(new Game(player, new GhostFactory()));

            frame_.add(game);
            frame_.addKeyListener(game);

            frame_.setVisible(true);
            game.run();

            if (!promptContinue()) {
                break;
            }
            frame_.remove(game);
            frame_.removeKeyListener(game);
        }
    }

    private JFrame frame_;

    private void initFrame() {
        frame_ = new JFrame("pecman");
        frame_.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame_.getContentPane().setPreferredSize(
            new Dimension(
                Constants.Graphics.CONTENT_WIDTH,
                Constants.Graphics.CONTENT_HEIGHT));
        frame_.pack();
    }

    private boolean promptContinue() {
        return JOptionPane.showConfirmDialog(
            frame_,
            "Do you wish to continue?",
            "Continue?",
            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }
}
