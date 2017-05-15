package entities.enemies;

import misc.PointNode;

import java.awt.Point;

public class SmartEnemy extends AdvancedEnemy {
    public SmartEnemy(int x, int y) {
        super(x, y);
    }

    @Override
    protected PointNode.FScore heuristic(PointNode parent, Point p, Point end) {
        return new PointNode.FScore(parent.score().gScore() + 1, manhattanDistance(p, end));
    }
}
