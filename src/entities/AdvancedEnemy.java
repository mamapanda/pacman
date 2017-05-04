package entities;

import maze.MazeGenerator;

import java.awt.Point;
import java.util.HashSet;
import java.util.PriorityQueue;

public abstract class AdvancedEnemy extends Enemy {
    public AdvancedEnemy(MazeGenerator generator, Pacman target, int x, int y) {
        super(generator, x, y);
        target_ = target;
    }

    public Pacman target() {
        return target_;
    }

    @Override
    public void move() {
        PriorityQueue<PointNode> queue = new PriorityQueue<>();
        HashSet<Point> explored = new HashSet<>();

        queue.add(
            new PointNode(
                null,
                location(),
                new FScore(0, manhattanDistance(location()))));

        PointNode goalNode = queue.peek();

        while (!queue.isEmpty()) {
            PointNode node = queue.poll();

            if (explored.contains(node.value())) {
                continue;
            }

            if (node.value().equals(target().location())) {
                goalNode = node;
                break;
            }

            for (Direction d : possibleMoves(node.value())) {
                Point tmp = new Point(node.value());
                tmp.translate(d.dx(), d.dy());
                queue.add(new PointNode(node, tmp, heuristic(node, tmp)));
            }
            explored.add(node.value());
        }

        while (goalNode.parent() != null && goalNode.parent().parent() != null) {
            goalNode = goalNode.parent();
        }

        setLocation(goalNode.value());
    }

    protected abstract FScore heuristic(PointNode parent, Point p);

    protected int manhattanDistance(Point p) {
        return Math.abs(p.x - target().x()) + Math.abs(p.y - target().y());
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

    private Pacman target_;
}
