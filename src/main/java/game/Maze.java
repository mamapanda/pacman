package game;

import misc.Constants;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The class that generates mazes.
 */
public class Maze {
    /**
     * Constructs a new Maze that generates a game
     * with the given number of rows and columns.
     */
    public Maze() {
        tiles_ = new boolean[Constants.Game.ROWS][Constants.Game.COLUMNS];
        init();
    }

    /**
     * Checks if a certain point is an open pathway in the maze.
     *
     * @param p the point
     * @return idk fam what do you think
     */
    public boolean hasPathAt(Point p) {
        return contains(p) && tiles()[p.y][p.x];
    }

    /**
     * Checks if a point lies within the generated mazes.
     *
     * @param p the point
     * @return whether or not a point lies in this maze
     */
    public static boolean contains(Point p) {
        return (p.x | p.y) >= 0
            && p.x < Constants.Game.COLUMNS
            && p.y < Constants.Game.ROWS;
    }

    public boolean[][] tiles() {
        return tiles_;
    }

    private boolean[][] tiles_;

    private void init() {
        generatePaths();
        patchDeadEnds();
    }

    private void generatePaths() {
        List<Point> stack = new ArrayList<>();
        Point first = new Point(0, 0);
        stack.add(first);
        tiles_[first.y][first.x] = true;

        while (!stack.isEmpty()) {
            Point current = stack.get(stack.size() - 1);
            Point[] nexts = nextPaths(current);
            if (nexts.length > 0) {
                Point next = nexts[(int) (Math.random() * nexts.length)];
                makePath(current, next);
                stack.add(next);
            } else {
                stack.remove(stack.size() - 1);
            }
        }
    }

    private void patchDeadEnds() {
        for (int y = 0; y < tiles().length; y += 2) {
            for (int x = 0; x < tiles()[0].length; x += 2) {
                Point current = new Point(x, y);
                Point[] adjacents = PointGenerator.adjacents(current);
                Point[] paths = Arrays.stream(adjacents)
                    .filter(p -> tiles()[p.y][p.x])
                    .toArray(Point[]::new);
                Point[] walls = Arrays.stream(adjacents)
                    .filter(p -> !tiles()[p.y][p.x])
                    .toArray(Point[]::new);
                if (paths.length == 1) {
                    Point newPath = walls[(int) (Math.random() * walls.length)];
                    tiles_[newPath.y][newPath.x] = true;
                }
            }
        }
    }

    private void makePath(Point p1, Point p2) {
        Point wall = new Point((p1.x + p2.x) / 2, (p1.y + p2.y) / 2);
        tiles_[wall.y][wall.x] = true;
        tiles_[p2.y][p2.x] = true;
    }

    private Point[] nextPaths(Point point) {
        Point[] neighbors = new Point[]{
            new Point(point.x - 2, point.y),
            new Point(point.x + 2, point.y),
            new Point(point.x, point.y - 2),
            new Point(point.x, point.y + 2)
        };
        return Arrays.stream(neighbors)
            .filter(p -> contains(p) && !tiles_[p.y][p.x])
            .toArray(Point[]::new);
    }
}
