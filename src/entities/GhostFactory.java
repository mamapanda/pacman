package entities;

import maze.MazeGenerator;
import maze.Quadrant;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class GhostFactory implements EnemyFactory {
    public GhostFactory(MazeGenerator m, Pacman man) {
        p = man;
        maze = m;
    }

    public List<Enemy> initialBatch() {
        List<Point> initialPoints =
            Arrays.stream(Quadrant.values())
                .skip(1)
                .map(q -> maze.generatePoints(1, q).get(0))
                .collect(Collectors.toCollection(ArrayList::new));
        Collections.shuffle(initialPoints);

        if (initialPoints.size() != 3) {
            throw new IllegalStateException("There are not 3 quadrant values supplied.");
        }

        List<Enemy> initialBatch = new ArrayList<>();
        initialBatch.add(
            new StupidEnemy(
                maze, initialPoints.get(0).x, initialPoints.get(0).y));
        initialBatch.add(
            new RushEnemy(
                maze, p, initialPoints.get(1).x, initialPoints.get(1).y));
        initialBatch.add(
            new SmartEnemy(
                maze, p, initialPoints.get(2).x, initialPoints.get(2).y));
        return initialBatch;
    }

    public Enemy make() {
        Quadrant[] quad = {Quadrant.II,Quadrant.III,Quadrant.IV};
        Random r = new Random();
        Quadrant here = quad[r.nextInt(2)];
        int whichEnemy = (int) (Math.random() * 3);

        if (whichEnemy == 0) {
            return new StupidEnemy();
        } else if (whichEnemy == 1) {
            return new RushEnemy();
        } else {
           return new SmartEnemy();
        }
    }

    private Pacman p;
    private MazeGenerator maze;
}
