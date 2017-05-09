package entities.enemies;

import entities.Pacman;
import game.PointGenerator;

import java.awt.Point;
import java.util.Arrays;
import java.util.function.Function;

/**
 * An enemy that targets a point twice the vector length of the blinky to pac man plus two vector.
 *
 * @author (Keeny Kwatch)
 * @version (05 05 17)
 */
public class AmbushEnemy extends SmartEnemy {
    public AmbushEnemy(int x, int y, AdvancedEnemy leader) {
        super(x, y);
        leader_ = leader;
    }

    @Override
    public void move(Function<Point, Boolean> isPath, Pacman target) {
        Point[] targetLocations = PointGenerator.adjacents(target.location());
        if (Arrays.stream(targetLocations).anyMatch(location()::equals)) {
            setLocation(target.location());
        }

        Point targetLocation =
            Arrays.stream(targetLocations)
                .filter(isPath::apply)
                .sorted((p1, p2) ->
                    (int) Math.signum(pythagoreanDistance(p1, leader_.location())
                        - pythagoreanDistance(p2, leader_.location())))
                .findFirst().orElse(null);

        PointNode goalNode = searchMove(location(), targetLocation, isPath, target);
        while (goalNode.parent() != null && goalNode.parent().parent() != null) {
            goalNode = goalNode.parent();
        }

        setLocation(goalNode.value());
    }

    protected static double pythagoreanDistance(Point p, Point other) {
        return Math.sqrt(Math.pow(p.x - other.x, 2) + Math.pow(p.y - other.y, 2));
    }

    private AdvancedEnemy leader_;
}
