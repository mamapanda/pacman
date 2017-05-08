package entities.enemies;

import entities.Pacman;

import java.awt.Point;

public class SmartEnemy extends AdvancedEnemy {
    public SmartEnemy(int x, int y) {
        super(x, y);
    }

    @Override
    protected FScore heuristic(PointNode parent, Point p, Pacman target) {
        return new FScore(parent.gScore() + 1, manhattanDistance(p, target.location()));
    }
}
