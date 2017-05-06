package entities;

import maze.MazeGenerator;
import maze.Quadrant;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AmbushFactory implements EnemyFactory{
    public AmbushFactory(MazeGenerator gen, Pacman target) {
        maze = gen;
        p = target;
    }

    @Override
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
        SmartEnemy leader =
            new SmartEnemy(
                maze, p, initialPoints.get(2).x, initialPoints.get(2).y);
        initialBatch.add(
            new AmbushEnemy(
                maze, p, initialPoints.get(0).x, initialPoints.get(0).y) {{
                setLeader(leader);
            }});
        initialBatch.add(
            new AmbushEnemy(
                maze, p, initialPoints.get(1).x, initialPoints.get(1).y) {{
                setLeader(leader);
            }});
        initialBatch.add(leader);
        return initialBatch;
    }

    @Override
    public Enemy make() {
        Quadrant[] quad = {Quadrant.II, Quadrant.III, Quadrant.IV};
        Quadrant here = quad[(int) (Math.random() * 3)]; //randomly select quadrant
        int whichEnemy = (int) (Math.random() * 4);

        List<Point> pointArray = maze.generatePoints(1, here);
        Point points = pointArray.get(0);

        int pointX = (int) points.getX();
        int pointY = (int) points.getY();

        if (whichEnemy == 0) {
            return new StupidEnemy(maze, pointX, pointY);
        } else if (whichEnemy == 1) {
            return new RushEnemy(maze, p, pointX, pointY);
        } else if (whichEnemy == 2) {
            return new SmartEnemy(maze, p, pointX, pointY);
        } else {
            return new AmbushEnemy(maze, p, pointX, pointY);
        }
    }

    private Pacman p;
    private MazeGenerator maze;
}


