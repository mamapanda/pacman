package entities;


import maze.MazeGenerator;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.awt.Point;

/**
 * Write a description of class RushEnemy here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class RushEnemy extends AdvancedEnemy {
    public RushEnemy(MazeGenerator generator, Pacman target, int x, int y) {
        super(generator, target, x, y);
    }

    @Override
    protected FScore heuristic(PointNode parent, Point p) {
        return new FScore(0, manhattanDistance(p));
    }
}
