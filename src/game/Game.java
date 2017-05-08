package game;

import entities.enemies.AdvancedEnemy;
import entities.enemies.AmbushEnemy;
import entities.Direction;
import entities.enemies.Enemy;
import entities.enemies.factories.EnemyFactory;
import entities.Pacman;
import misc.Constants;

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
        enemies().addAll(enemyFactory_.);

        goalTiles_ = new LinkedList<>();
        initGoalTiles();
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
     * Clears the game, generates new goal points,
     * and adds another enemy.
     */
    public void prepNext() {
        generator().reset();
        generator().generate();

        goalTiles().clear();
        initGoalTiles();

        player().moveToInitialLocation();
        for (int i = 0; i < enemies().size(); i++) {
            enemies().get(i).moveToInitialLocation();
        }
        Enemy newEnemy = enemyFactory_.make();
        if (newEnemy instanceof AmbushEnemy) {
            Enemy[] currentEnemies =
                enemies().stream()
                    .filter(e -> e instanceof AdvancedEnemy
                        && !(e instanceof AmbushEnemy))
                    .toArray(Enemy[]::new);
            int i = (int) (Math.random() * currentEnemies.length);
            ((AmbushEnemy) newEnemy).setLeader((AdvancedEnemy) currentEnemies[i]);
        }
        enemies().add(newEnemy);

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

    private void initGoalTiles() {
        for (Quadrant q : Quadrant.values()) {
            goalTiles().addAll(
                generator().generatePoints(
                    Constants.Game.TILES_PER_QUADRANT, q));
        }
    }
}
