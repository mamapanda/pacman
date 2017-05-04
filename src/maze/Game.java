package maze;

import entities.Direction;
import entities.Enemy;
import entities.EnemyFactory;
import entities.Pacman;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

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

        goalTiles_ = generator().generatePoints(goalCount);
    }

    /**
     * Goes through one step of the game.
     */
    public void step() {
        try {
            Direction playerMove = getPlayerMove();
            if (playerMove != null) {
                player().move(playerMove);
            }
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
}
