package entities.enemies;

import entities.Direction;
import entities.Pacman;
import misc.PointNode;

import java.awt.Point;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.function.Function;

public abstract class AdvancedEnemy extends Enemy {
    public AdvancedEnemy(int x, int y) {
        super(x, y);
    }

    public void move(Function<Point, Boolean> isPath, Pacman target) {
        PointNode goalNode = searchMove(location(), target.location(), isPath, null);

        while (goalNode.parent() != null && goalNode.parent().parent() != null) {
            goalNode = goalNode.parent();
        }

        setLocation(goalNode.value());
    }

    protected abstract PointNode.FScore heuristic(PointNode parent, Point p, Point end);

    protected PointNode searchMove(Point start,
                                   Point end,
                                   Function<Point, Boolean> isPath,
                                   Pacman target) {
        PriorityQueue<PointNode> queue = new PriorityQueue<>();
        HashSet<Point> explored = new HashSet<>();

        PointNode.FScore initialScore = new PointNode.FScore(0, manhattanDistance(start, end));
        queue.add(new PointNode(null, start, initialScore));

        PointNode goalNode = queue.peek();
        while (!queue.isEmpty()) {
            PointNode node = queue.poll();

            if (explored.contains(node.value())
                || (target != null && node.value().equals(target.location()))) {
                continue;
            }
            if (node.value().equals(end)) {
                goalNode = node;
                break;
            }

            for (Direction d : possibleMoves(isPath, node.value())) {
                Point tmp = new Point(node.value());
                tmp.translate(d.dx(), d.dy());
                queue.add(new PointNode(node, tmp, heuristic(node, tmp, end)));
            }
            explored.add(node.value());
        }

        return goalNode;
    }

    protected int manhattanDistance(Point p, Point other) {
        return Math.abs(p.x - other.x) + Math.abs(p.y - other.y);
    }
}
