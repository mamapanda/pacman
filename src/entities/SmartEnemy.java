package entities;


import maze.MazeGenerator;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.awt.Point;
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * Write a description of class SmartEnemy here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class SmartEnemy extends AdvancedEnemy {
    public SmartEnemy(MazeGenerator generator, Pacman target, int x, int y) {
        super(generator, target, x, y);
    }

    @Override
    protected FScore heuristic(PointNode parent, Point p) {
        return new FScore(parent.gScore() + 1, manhattanDistance(p));
    }
}
