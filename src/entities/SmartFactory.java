package entities;

import maze.MazeGenerator;
import maze.Quadrant;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SmartFactory implements EnemyFactory {
    public SmartFactory(MazeGenerator generator, Pacman target) {
        generator_ = generator;
        target_ = target;
    }

    @Override
    public List<Enemy> initialBatch() {
        return Arrays.stream(Quadrant.values())
            .skip(1)
            .map(q -> generator_.generatePoints(2, q))
            .flatMap(List::stream)
            .map(p -> new SmartEnemy(generator_, target_, p.x, p.y))
            .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public Enemy make() {
        Quadrant[] quadrants = {Quadrant.II, Quadrant.III, Quadrant.IV};
        Quadrant q = quadrants[(int) (Math.random() * quadrants.length)];
        Point location = generator_.generatePoints(1, q).get(0);
        return new SmartEnemy(generator_, target_, location.x, location.y);
    }

    private MazeGenerator generator_;
    private Pacman target_;
}
