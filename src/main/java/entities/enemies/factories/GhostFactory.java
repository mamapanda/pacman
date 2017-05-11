package entities.enemies.factories;

import entities.enemies.AmbushEnemy;
import entities.enemies.Enemy;
import entities.enemies.RushEnemy;
import entities.enemies.SmartEnemy;
import entities.enemies.StupidEnemy;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GhostFactory implements EnemyFactory {
    @Override
    public List<Enemy> make(List<Point> locations) {
        lastSmart_ = null;
        return IntStream.range(0, locations.size())
            .mapToObj(n -> makeEnemy(locations.get(n), n))
            .collect(Collectors.toCollection(ArrayList::new));
    }

    private Enemy makeEnemy(Point location, int n) {
        int x = location.x;
        int y = location.y;
        n %= 4;

        if (n == 0) {
            return new StupidEnemy(x, y);
        } else if (n == 1) {
            return new RushEnemy(x, y);
        } else if (n == 2) {
            lastSmart_ = new SmartEnemy(x, y);
            return lastSmart_;
        } else {
            return new AmbushEnemy(x, y, lastSmart_);
        }
    }

    private SmartEnemy lastSmart_;
}
