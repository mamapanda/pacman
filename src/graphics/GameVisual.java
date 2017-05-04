package graphics;

import entities.Direction;
import entities.Enemy;
import entities.EnemyFactory;
import entities.Pacman;
import maze.Game;
import maze.MazeGenerator;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.List;

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
        initComponents();
        initFrame();
        initArrowListener();
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
    private MazeGenVisual vGenerator_;
    private EntityVisual vPlayer_;
    private List<EntityVisual> vEnemies_;
    private static final int UPDATE_DELAY = 100;
    private static final String PLAYER_IMG = "pacman.gif";
    private static final String[] ENEMY_IMGs = {
        "redghost.gif", "purpleghost.gif", "blueghost.gif"
    };

    private void initFrame() {
        frame_.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Dimension contentDimensions = new Dimension(
            generator().maze()[0].length * MazeGenVisual.CELL_LENGTH,
            generator().maze().length * MazeGenVisual.CELL_LENGTH);
        frame_.getContentPane().setPreferredSize(contentDimensions);
        frame_.pack();

        frame_.add(vGenerator_);
        frame_.add(vPlayer_);
        vEnemies_.forEach(frame_::add);

        frame_.setVisible(true);
    }

    private void initComponents() {
        vGenerator_ = new MazeGenVisual(generator());
        vPlayer_ = new EntityVisual(player(), PLAYER_IMG, MazeGenVisual.CELL_LENGTH);

        vEnemies_ = new LinkedList<>();
        for (Enemy e : enemies()) {
            String img = ENEMY_IMGs[(int)(Math.random() * ENEMY_IMGs.length)];
            vEnemies_.add(new EntityVisual(e, img, MazeGenVisual.CELL_LENGTH));
        }
    }

    private void initArrowListener() {
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
}
