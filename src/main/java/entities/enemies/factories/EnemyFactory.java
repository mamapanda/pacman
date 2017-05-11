package entities.enemies.factories;

import entities.enemies.Enemy;

import java.awt.Point;
import java.util.List;

/**
 * @author Daniel Phan
 */
public interface EnemyFactory {
    List<Enemy> make(List<Point> locations);
}
