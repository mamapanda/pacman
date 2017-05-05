package graphics;

import entities.Direction;
import entities.Enemy;
import maze.Game;

import javax.swing.JLayeredPane;
import javax.swing.OverlayLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author Daniel Phan
 */
public class GameVisual extends JLayeredPane implements KeyListener {
    public GameVisual(Game core) {
        setLayout(new OverlayLayout(this));
        core_ = core;
        currentArrowEvent_ = null;
        initComponents();
    }

    public void run() {
        while (true) {
            while (!core_.levelFinished()) {
                try {
                    Thread.sleep(UPDATE_DELAY);
                } catch (InterruptedException ignored) {

                }
                core_.step(getPlayerMove());
                repaint();
            }
            if (core_.player().alive()) {
                nextLevel();
            } else {
                break;
            }
        }
    }


    public Game core() {
        return core_;
    }

    @Override
    public boolean isOptimizedDrawingEnabled() {
        return false;
    }

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
        if (currentArrowEvent_ != null
                && e.getKeyCode() == currentArrowEvent_.getKeyCode()) {
            currentArrowEvent_ = null;
        }
    }

    private Game core_;
    private KeyEvent currentArrowEvent_;
    private static final int UPDATE_DELAY = 100;
    private static final String PLAYER_IMG = "pacman.gif";
    private static final String[] ENEMY_IMGs = {
            "redghost.gif", "purpleghost.gif", "blueghost.gif"
    };

    private void initComponents() {
        MazeGenVisual vGenerator = new MazeGenVisual(core_.generator());
        core_.generator().generate();
        add(vGenerator, Integer.valueOf(0));
        add(new EntityVisual(core_.player(), PLAYER_IMG, MazeGenVisual.CELL_LENGTH),
                Integer.valueOf(10));

        for (Enemy e : core_.enemies()) {
            String img = ENEMY_IMGs[(int) (Math.random() * ENEMY_IMGs.length)];
            add(new EntityVisual(e, img, MazeGenVisual.CELL_LENGTH),
                    Integer.valueOf(15));
        }

        add(new GoalTileVisual(core_.goalTiles()), Integer.valueOf(5));
    }

    private void nextLevel() {
        if (core().goalTiles().size() != 0) {
            throw new IllegalStateException("There are still goal tiles.");
        }

        core().prepNext();

        String newEnemyIMG = ENEMY_IMGs[(int) (Math.random() * ENEMY_IMGs.length)];
        add(
                new EntityVisual(
                        core().enemies().get(core().enemies().size() - 1),
                        newEnemyIMG,
                        MazeGenVisual.CELL_LENGTH),
                Integer.valueOf(15));
        for (int i = 0; i < getComponentCount(); i++) {
            if (getComponent(i) instanceof GoalTileVisual) {
                remove(i);
            }
        }
        add(new GoalTileVisual(core_.goalTiles()), Integer.valueOf(5));
        revalidate();
    }

    private boolean isArrow(KeyEvent e) {
        int keycode = e.getKeyCode();
        return keycode == KeyEvent.VK_UP
                || keycode == KeyEvent.VK_DOWN
                || keycode == KeyEvent.VK_LEFT
                || keycode == KeyEvent.VK_RIGHT;
    }

    private Direction getPlayerMove() {
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
}
