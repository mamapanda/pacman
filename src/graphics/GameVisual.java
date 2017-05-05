package graphics;

import entities.Direction;
import entities.Enemy;
import maze.Game;
import misc.Constants;

import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.OverlayLayout;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
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
            transitionLevel();
            while (!core_.levelFinished()) {
                try {
                    Thread.sleep(Constants.Graphics.UPDATE_DELAY);
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

    private void initComponents() {
        MazeGenVisual vGenerator = new MazeGenVisual(core_.generator());
        core_.generator().generate();
        add(vGenerator, Integer.valueOf(0));
        add(new EntityVisual(core_.player(), Constants.Images.PLAYER),
            Integer.valueOf(10));

        String[] enemyIMGs = Constants.Images.ENEMIES;
        for (Enemy e : core_.enemies()) {
            String img = enemyIMGs[(int) (Math.random() * enemyIMGs.length)];
            add(new EntityVisual(e, img), Integer.valueOf(15));
        }

        add(new GoalTileVisual(core_.goalTiles()), Integer.valueOf(5));
    }

    private void nextLevel() {
        if (core().goalTiles().size() != 0) {
            throw new IllegalStateException("There are still goal tiles.");
        }

        core().prepNext();

        String[] enemyIMGs = Constants.Images.ENEMIES;
        String newEnemyIMG = enemyIMGs[(int) (Math.random() * enemyIMGs.length)];
        add(
            new EntityVisual(
                core().enemies().get(core().enemies().size() - 1),
                newEnemyIMG),
            Integer.valueOf(15));
        for (int i = 0; i < getComponentCount(); i++) {
            if (getComponent(i) instanceof GoalTileVisual) {
                remove(i);
            }
        }
        add(new GoalTileVisual(core_.goalTiles()), Integer.valueOf(5));
        revalidate();
    }

    private void transitionLevel() {
        JComponent transition = new Transition();

        add(transition, Integer.valueOf(100));
        revalidate();
        try {
            Thread.sleep(Constants.Transition.DELAY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        remove(transition);
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

    private class Transition extends JComponent {
        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(Constants.Transition.BG_COLOR);
            g2.fill(new Rectangle(
                0,
                0,
                Constants.Graphics.CONTENT_WIDTH,
                Constants.Graphics.CONTENT_HEIGHT));

            g2.setFont(Constants.Transition.TEXT_FONT);
            g2.setColor(Constants.Transition.TEXT_COLOR);
            String s = String.format("LEVEL %d", core().currentLevel());

            FontMetrics fontMetrics = g2.getFontMetrics();
            int contentWidth = Constants.Graphics.CONTENT_WIDTH;
            int stringWidth = fontMetrics.stringWidth(s);
            int contentHeight = Constants.Graphics.CONTENT_HEIGHT;
            int stringHeight = fontMetrics.getHeight() - fontMetrics.getAscent();
            int textX = (contentWidth - stringWidth) / 2;
            int textY = (contentHeight - stringHeight) / 2;
            g2.drawString(s, textX, textY);
        }
    }
}
