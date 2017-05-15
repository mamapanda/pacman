package entities.enemies;

import misc.PointNode;

import java.awt.Point;

public class RushEnemy extends AdvancedEnemy {
    public RushEnemy(int x, int y) {
        super(x, y);
    }

    @Override
    protected PointNode.FScore heuristic(PointNode parent, Point p, Point end) {
        return new PointNode.FScore(0, manhattanDistance(p, end));
    }
}
