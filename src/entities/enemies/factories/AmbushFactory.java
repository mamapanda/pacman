package entities.enemies.factories;

import entities.enemies.AdvancedEnemy;
import entities.enemies.AmbushEnemy;
import entities.enemies.Enemy;
import entities.enemies.SmartEnemy;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AmbushFactory implements EnemyFactory {
    @Override
    public List<Enemy> make(List<Point> locations) {
        AdvancedEnemy leader = new SmartEnemy(locations.get(0).x, locations.get(0).y);

        List<Enemy> ambushes = locations.stream().skip(1)
            .map(location -> new AmbushEnemy(location.x, location.y, leader))
            .collect(Collectors.toCollection(ArrayList::new));
        ambushes.add(leader);
        return ambushes;
    }
}


