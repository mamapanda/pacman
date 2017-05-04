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
public class SmartEnemy extends RushEnemy {
    public SmartEnemy(MazeGenerator generator, Pacman target, int x, int y) {
        super(generator, target, x, y);
    }

    @Override
    public void move() {
        PriorityQueue<PointNode> queue = new PriorityQueue<>();
        HashSet<Point> explored = new HashSet<>();

        queue.add(new PointNode(null, location(), 0));

        PointNode goalNode = queue.peek();

        while(!queue.isEmpty()) {
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
                queue.add(new PointNode(node, tmp, node.gScore() + 1));
            }
            explored.add(node.value());
        }

        while (goalNode.parent().parent() != null) {
            goalNode = goalNode.parent();
        }

        setLocation(goalNode.value());
    }

    private class PointNode implements Comparable<PointNode> {
        public PointNode(PointNode parent, Point value, int gScore) {
            parent_ = parent;
            value_ = value;
            gScore_ = gScore;
            fScore_ = gScore() + manhattanDistance(value);
        }

        public PointNode parent() {
            return parent_;
        }

        public Point value() {
            return value_;
        }

        public int gScore() {
            return gScore_;
        }

        @Override
        public int compareTo(PointNode other) {
            return fScore_ - other.fScore_;
        }

        private PointNode parent_;
        private Point value_;
        private int fScore_;
        private int gScore_;
    }
}
