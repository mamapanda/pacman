package maze;

import java.awt.Point;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

public class MazeGenerator {
    public MazeGenerator(int rows, int columns) {
        if ((rows & columns & 1) == 0) {
            throw new IllegalArgumentException(
                "Both rows and columns must be odd");
        } else if ((rows | columns) < 0) {
            throw new IllegalArgumentException(
                "Both rows and columns must be positive.");
        }
        maze_ = new boolean[rows][columns];
        stack_ = new LinkedList<>();
        rand_ = new Random();

        Point first = new Point(0, 0);
        stack_.addFirst(first);
        maze_[first.y][first.x] = true;
    }

    /**
     * Fully generates the maze.
     */
    public void generate() {
        while (!finished()) {
            step();
        }
    }

    /**
     * Executes the next step of the maze generation.
     *
     * @return the current visited point
     */
    public Point step() {
        if (finished()) return null;

        Point start = stack_.peekFirst();
        Point[] nexts = nextPoints(start);

        if (nexts.length > 0) {
            Point next = nexts[rand_.nextInt(nexts.length)];
            makePath(start, next);
            stack_.addFirst(next);
            return next;
        } else {
            stack_.pollFirst();
            return stack_.peekFirst();
        }
    }

    public boolean contains(Point p) {
        return (p.x | p.y) >= 0 && p.x < maze()[0].length && p.y < maze().length;
    }

    public boolean[][] maze() {
        return maze_;
    }

    public boolean finished() {
        return stack_.isEmpty();
    }

    private boolean[][] maze_;
    private LinkedList<Point> stack_;
    private Random rand_;

    private void makePath(Point p1, Point p2) {
        Point wall = new Point((p1.x + p2.x) / 2, (p1.y + p2.y) / 2);
        maze_[wall.y][wall.x] = true;
        maze_[p2.y][p2.x] = true;
    }

    private Point[] nextPoints(Point point) {
        Point[] neighbors = new Point[]{
            new Point(point.x - 2, point.y),
            new Point(point.x + 2, point.y),
            new Point(point.x, point.y - 2),
            new Point(point.x, point.y + 2)
        };
        return Arrays.stream(neighbors)
            .filter(p -> contains(p) && !maze_[p.y][p.x])
            .toArray(Point[]::new);
    }
}
