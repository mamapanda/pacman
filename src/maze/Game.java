package maze;

import entities.Direction;
import entities.Enemy;
import entities.EnemyFactory;
import entities.Pacman;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Game {
    public Game(MazeGenerator generator,
                Pacman player,
                EnemyFactory enemyFactory) {
        currentLevel_ = 1;
        isEnemyMove_ = true;
        player_ = player;
        generator_ = generator;
        enemies_ = new ArrayList<>();
        enemyFactory_ = enemyFactory;

        generator().generate();
        enemies().addAll(enemyFactory_.initialBatch());

        goalTiles_ = new LinkedList<>();
        for (Quadrant q : Quadrant.values()) {
            goalTiles().addAll(generator().generatePoints(1, q));
        }
    }

    /**
     * Goes through one step of the game.
     */
    public void step(Direction playerMove) {
        try {
            if (playerMove != null) {
                player().move(playerMove);
            }
        } catch (IllegalArgumentException ignored) {
            //No error action.
        }

        enemies().forEach(e -> {
            if (e.collidesWith(player())) {
                player().die();
            }
        });

        if (isEnemyMove_) {
            enemies().forEach(Enemy::move);
            enemies().forEach(e -> {
                if (e.collidesWith(player())) {
                    player().die();
                }
            });
        }

        for (int i = 0; i < goalTiles().size(); i++) {
            if (goalTiles().get(i).equals(player().location())) {
                goalTiles().remove(i);
                break;
            }
        }

        isEnemyMove_ = !isEnemyMove_;
    }

    /**
     * Clears the maze, generates new goal points,
     * and adds another enemy.
     */
    public void prepNext() {
        generator().reset();
        generator().generate();

        goalTiles().clear();
        for (Quadrant q : Quadrant.values()) {
            goalTiles().addAll(generator().generatePoints(1, q));
        }

        player().moveToInitialLocation();
        for (int i = 0; i < enemies().size(); i++) {
            enemies().get(i).moveToInitialLocation();
        }
        enemies().add(enemyFactory_.make());

        currentLevel_++;
    }

    public int currentLevel() {
        return currentLevel_;
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

    private int currentLevel_;
    private boolean isEnemyMove_;
    private Pacman player_;
    private MazeGenerator generator_;
    private List<Enemy> enemies_;
    private List<Point> goalTiles_;
    private EnemyFactory enemyFactory_;
}
