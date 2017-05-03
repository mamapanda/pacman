package maze;

import entities.Direction;
import entities.Enemy;
import entities.Pacman;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Game {
    public Game(MazeGenerator generator, Pacman player, int panelCount) {
        player_ = player;
        generator_ = generator;
        enemies_ = new ArrayList<>();
        goalPanels_ = new ArrayList<>();
        rand_ = new Random();

        for (int i = 0; i < panelCount; i++) {
            goalPanels_.add(
                new Point(
                    rand_.nextInt(generator_.maze()[0].length),
                    rand_.nextInt(generator_.maze().length)));
        }
    }

    /**
     * Goes through one step of the game.
     */
    public void step() {
        try {
            player().move(getPlayerMove());
        } catch (IllegalArgumentException ignored) {
            //No error action.
        }
        enemies().forEach(Enemy::move);
    }

    public void reset() {
        throw new NotImplementedException();
    }

    public Pacman player() {
        return player_;
    }

    public MazeGenerator generator() {
        return generator_;
    }

    public List<Enemy> enemies() {
        return enemies_;
    }

    public List<Point> goalPanels() {
        return goalPanels_;
    }

    protected abstract Direction getPlayerMove();

    private Pacman player_;
    private MazeGenerator generator_;
    private List<Enemy> enemies_;
    private List<Point> goalPanels_;
    private Random rand_;
}
