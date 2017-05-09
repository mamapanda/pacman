package entities.enemies;

import entities.Pacman;

import java.awt.Point;

public class RushEnemy extends AdvancedEnemy {
    public RushEnemy(int x, int y) {
        super(x, y);
    }

    @Override
    protected FScore heuristic(PointNode parent, Point p, Point end) {
        return new FScore(0, manhattanDistance(p, end));
    }
}
