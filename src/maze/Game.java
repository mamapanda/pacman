package maze;

import entities.Direction;
import entities.Enemy;
import entities.Pacman;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class Game {
    public Game(MazeGenerator generator, Pacman player, int goalCount) {
        player_ = player;
        generator_ = generator;
        enemies_ = new ArrayList<>();
        initGoalTiles(goalCount);
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
        //check for death
    }

    public void reset() {
        generator().reset();
        initGoalTiles(goalTiles().length);
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

    public Point[] goalTiles() {
        return goalTiles_;
    }

    protected abstract Direction getPlayerMove();

    private Pacman player_;
    private MazeGenerator generator_;
    private List<Enemy> enemies_;
    private Point[] goalTiles_;

    /**
     * Initializes the array of goal tiles.
     * @param goalCount the number of goal tiles.
     */
    private void initGoalTiles(int goalCount) {
        Function<Integer, Point[]> rowPoints = r ->
            IntStream.range(0, generator().maze().length)
                .mapToObj(c -> new Point(r, c))
                .toArray(Point[]::new);
        List<Point> availableTiles =
            IntStream.range(0, generator().maze()[0].length)
                .mapToObj(rowPoints::apply)
                .flatMap(Arrays::stream)
                .filter(generator()::contains)
                .collect(Collectors.toList());
        Collections.shuffle(availableTiles);
        goalTiles_ =
            availableTiles.stream()
                .limit(goalCount)
                .toArray(Point[]::new);
    }
}
