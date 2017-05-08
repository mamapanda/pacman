package entities.enemies;

import entities.Pacman;

import java.awt.Point;

public class RushEnemy extends AdvancedEnemy {
    public RushEnemy(int x, int y) {
        super(x, y);
    }

    @Override
    protected FScore heuristic(PointNode parent, Point p, Pacman target) {
        return new FScore(0, manhattanDistance(p, target.location()));
    }
}
