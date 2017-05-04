package graphics;

import entities.Direction;
import entities.EnemyFactory;
import entities.Pacman;
import maze.Game;
import maze.MazeGenerator;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author Daniel Phan
 */
public class GameVisual extends Game {
    public GameVisual(MazeGenerator generator,
                      Pacman player,
                      EnemyFactory enemyFactory,
                      int goalCount,
                      String title) {
        super(generator, player, enemyFactory, goalCount);
        frame_ = new JFrame(title);
        currentArrowEvent_ = null;

        frame_.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                //Nothing
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (isArrow(e)) {
                    currentArrowEvent_ = e;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (isArrow(e)) {
                    currentArrowEvent_ = null;
                }
            }

            private boolean isArrow(KeyEvent e) {
                int keycode = e.getKeyCode();
                return keycode == KeyEvent.VK_UP
                    || keycode == KeyEvent.VK_DOWN
                    || keycode == KeyEvent.VK_LEFT
                    || keycode == KeyEvent.VK_RIGHT;
            }
        });
    }

    public void run() {
        throw new NotImplementedException();
    }

    @Override
    protected Direction getPlayerMove() {
        Direction d = null;
        if (currentArrowEvent_ != null) {
            switch (currentArrowEvent_.getKeyCode()) {
                case KeyEvent.VK_UP:
                    d = Direction.UP;
                    break;
                case KeyEvent.VK_LEFT:
                    d = Direction.LEFT;
                    break;
                case KeyEvent.VK_RIGHT:
                    d = Direction.RIGHT;
                    break;
                case KeyEvent.VK_DOWN:
                    d = Direction.DOWN;
                    break;
                default:
                    throw new IllegalStateException("@GameVisual::getPlayerMove");
            }
        }
        return d;
    }

    private JFrame frame_;
    private KeyEvent currentArrowEvent_;
    private static final int UPDATE_DELAY = 100;
}
