package game;

import misc.Constants;
import misc.PointNode;

import java.awt.Point;
import java.util.ArrayDeque;
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
                if (paths.length == 1) {
                    Point[] candidates = Arrays.stream(adjacents)
                        .filter(p -> !tiles()[p.y][p.x])
                        .map(p -> new Point(2 * p.x - current.x, 2 * p.y - current.y))
                        .toArray(Point[]::new);
                    Point p = farthestPath(current, candidates);
                    System.out.println(current);
                    tiles_[(p.y + current.y) / 2][(p.x + current.x) / 2] = true;
                }
            }
        }
    }

    private Point farthestPath(Point start, Point[] paths) {
        ArrayDeque<PointNode> queue = new ArrayDeque<>();
        int[][] pathLengths = new int[Constants.Game.ROWS][Constants.Game.COLUMNS];
        queue.add(new PointNode(null, start, new PointNode.FScore(1, 0)));

        while (!queue.isEmpty()) {
            PointNode currentNode = queue.pollFirst();
            Point current = currentNode.value();
            if (pathLengths[current.y][current.x] != 0) {
                continue;
            }
            pathLengths[current.y][current.x] = currentNode.score().value();
            Arrays.stream(PointGenerator.adjacents(current))
                .filter(this::hasPathAt)
                .map(p ->
                    new PointNode(
                        null,
                        p,
                        new PointNode.FScore(
                            currentNode.score().gScore() + 1,
                            0)))
                .forEach(queue::add);
        }
        return Arrays.stream(paths)
            .sorted((p1, p2) -> pathLengths[p2.y][p2.x] - pathLengths[p1.y][p1.x])
            .findFirst().orElse(null);
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
