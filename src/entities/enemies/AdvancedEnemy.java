package entities.enemies;

import entities.Direction;
import entities.Pacman;

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

    protected abstract FScore heuristic(PointNode parent, Point p, Point end);

    protected PointNode searchMove(Point start,
                                   Point end,
                                   Function<Point, Boolean> isPath,
                                   Pacman target) {
        PriorityQueue<PointNode> queue = new PriorityQueue<>();
        HashSet<Point> explored = new HashSet<>();

        FScore initialScore = new FScore(0, manhattanDistance(start, end));
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

    protected static class FScore {
        public FScore(int gScore, int hScore) {
            gScore_ = gScore;
            hScore_ = hScore;
        }

        public int value() {
            return gScore() + hScore();
        }

        public int gScore() {
            return gScore_;
        }

        public int hScore() {
            return hScore_;
        }

        private int gScore_;
        private int hScore_;
    }

    protected static class PointNode implements Comparable<PointNode> {
        public PointNode(PointNode parent, Point value, FScore score) {
            parent_ = parent;
            value_ = value;
            score_ = score;
        }

        public PointNode parent() {
            return parent_;
        }

        public Point value() {
            return value_;
        }

        public int gScore() {
            return score_.gScore();
        }

        @Override
        public int compareTo(PointNode other) {
            return score_.value() - other.score_.value();
        }

        private PointNode parent_;
        private Point value_;
        private FScore score_;
    }
}
