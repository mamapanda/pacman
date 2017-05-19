package game;

import entities.Direction;
import entities.enemies.Enemy;
import entities.enemies.factories.EnemyFactory;
import entities.Pacman;
import misc.Constants;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Game {
    public Game(Pacman player, EnemyFactory enemyFactory) {
        currentLevel_ = 1;
        player_ = player;
        enemyFactory_ = enemyFactory;
        init();
    }

    /**
     * Goes through one step of the game.
     */
    public void step(Direction playerMove) {
        try {
            if (playerMove != null) {
                player().move(maze()::hasPathAt, playerMove);
            }
        } catch (IllegalArgumentException ignored) {
            //No error action.
        }

        enemies().forEach(e -> {
            if (e.collidesWith(player())) {
                player().die();
            }
        });

        if (ghostN_ % Constants.Game.GHOST_UPDATE_N == 0) {
            enemies().forEach(e -> {
                e.move(maze()::hasPathAt, player());
                if (e.collidesWith(player())) {
                    player().die();
                }
            });
            ghostN_ = 0;
        }

        for (int i = 0; i < goalTiles().size(); i++) {
            if (goalTiles().get(i).equals(player().location())) {
                goalTiles().remove(i);
                break;
            }
        }

        ghostN_++;
    }

    public void prepNext() {
        currentLevel_++;
        init();
    }

    public boolean levelFinished() {
        return !player().alive() || goalTiles().size() == 0;
    }

    public int currentLevel() {
        return currentLevel_;
    }

    public List<Enemy> enemies() {
        return enemies_;
    }

    public List<Point> goalTiles() {
        return goalTiles_;
    }

    public Maze maze() {
        return maze_;
    }

    public Pacman player() {
        return player_;
    }

    private int currentLevel_;
    private List<Enemy> enemies_;
    private EnemyFactory enemyFactory_;
    private List<Point> goalTiles_;
    private int ghostN_;
    private Maze maze_;
    private Pacman player_;

    private void init() {
        maze_ = new Maze();
        ghostN_ = 0;

        player().setLocation(Constants.Game.PLAYER_START_LOCATION);

        PointGenerator gen = new PointGenerator(maze_);
        initEnemies(gen);
        initGoalTiles(gen);
    }

    private void initEnemies(PointGenerator gen) {
        int count = Constants.Game.INITIAL_GHOST_COUNT + currentLevel() - 1;
        Quadrant[] quadrants = Quadrant.values();

        List<Point> locations =
            IntStream.range(0, count)
                .mapToObj(i -> quadrants[i % (quadrants.length - 1) + 1])
                .map(gen::next)
                .collect(Collectors.toCollection(ArrayList::new));

        Collections.shuffle(locations);
        enemies_ = enemyFactory_.make(locations);
    }

    private void initGoalTiles(PointGenerator gen) {
        goalTiles_ = Arrays.stream(Quadrant.values())
            .flatMap(q ->
                Collections
                    .nCopies(Constants.Game.TILES_PER_QUADRANT, q)
                    .stream())
            .map(gen::next)
            .collect(Collectors.toCollection(ArrayList::new));
    }
}
