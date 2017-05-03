package maze;

import entities.Direction;
import entities.Enemy;
import entities.EnemyFactory;
import entities.Pacman;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class Game {
    public Game(MazeGenerator generator,
                Pacman player,
                EnemyFactory enemyFactory,
                int goalCount) {
        player_ = player;
        generator_ = generator;
        enemies_ = new ArrayList<>();
        enemyFactory_ = enemyFactory;

        enemies().addAll(enemyFactory_.initialBatch());

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
        enemies().forEach(e -> {
            if (e.collidesWith(player())) {
                player().die();
            }
        });

        goalTiles().forEach(tile -> {
            if (tile.equals(player().location())) {
                goalTiles().remove(tile);
            }
        });
    }

    public void nextLevel() {
        generator().reset();
        initGoalTiles(goalTiles().size());
        enemies().add(enemyFactory_.make());
    }

    public boolean levelFinished() {
        return !player().alive() || goalTiles().size() == 0;
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

    public List<Point> goalTiles() {
        return goalTiles_;
    }

    protected abstract Direction getPlayerMove();

    private Pacman player_;
    private MazeGenerator generator_;
    private List<Enemy> enemies_;
    private List<Point> goalTiles_;
    private EnemyFactory enemyFactory_;

    /**
     * Initializes the array of goal tiles.
     *
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
                .collect(Collectors.toList());
    }
}
