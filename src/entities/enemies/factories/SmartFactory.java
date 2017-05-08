package entities.enemies.factories;

import entities.enemies.Enemy;
import entities.enemies.SmartEnemy;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SmartFactory implements EnemyFactory {
    @Override
    public List<Enemy> make(List<Point> locations) {
        return locations.stream()
            .map(location -> new SmartEnemy(location.x, location.y))
            .collect(Collectors.toCollection(ArrayList::new));
    }
}
