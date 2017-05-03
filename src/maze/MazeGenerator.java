package maze;

import java.awt.Point;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

/**
 * The class that generates mazes.
 */
public class MazeGenerator {
    /**
     * Constructs a new MazeGenerator that generates a maze
     * with the given number of rows and columns.
     *
     * @param rows    the number of rows in the maze (must be odd)
     * @param columns the number of columns in the maze (must be odd)
     */
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
        while (step() != null) ;
        while (patchDeadEnd() != null) ;
    }

    /**
     * Executes the next step of the maze generation.
     *
     * @return the current visited point
     */
    public Point step() {
        if (stack_.isEmpty()) return null;

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

    /**
     * Patches the next dead end in the maze.
     *
     * @return the dead end that was fixed
     */
    public Point patchDeadEnd() {
        for (int y = 0; y < maze().length; y += 2) {
            for (int x = 0; x < maze()[0].length; x += 2) {
                Point current = new Point(x, y);
                Point[] adjacents = adjacentPoints(current);
                Point[] paths = Arrays.stream(adjacents)
                    .filter(p -> maze()[p.y][p.x])
                    .toArray(Point[]::new);
                Point[] walls = Arrays.stream(adjacents)
                    .filter(p -> !maze()[p.y][p.x])
                    .toArray(Point[]::new);
                if (paths.length == 1) {
                    Point newPath = walls[rand_.nextInt(walls.length)];
                    maze_[newPath.y][newPath.x] = true;
                    return current;
                }
            }
        }
        return null;
    }
    
    /**
     * Makes the special tiles.
     * 
     * @return the special tiles.
     */
    public Point[] specialTile()
    {
        Point[] pts = new Point[4];
        for (int i = 0; i < 4; i++)
        {
            int y = (int) (Math.random() * maze().length);
            int x = (int) (Math.random() * maze()[0].length);
            Point p = new Point (x,y);
            if (contains(p) && hasPathAt(p))
            {
                pts[i] = p;
            }
            else
            {
                i--;
            }
        }
        return pts;
    }

    /**
     * Clears the maze generated.
     */
    public void reset() {
        maze_ = new boolean[maze_.length][maze_[0].length];
    }

    /**
     * Checks if a point lies within the maze generated.
     * @param p the point
     * @return whether or not a point lies in this maze
     */
    public boolean contains(Point p) {
        return (p.x | p.y) >= 0 && p.x < maze()[0].length && p.y < maze().length;
    }

    /**
     * Checks if a certain point is an open pathway in the maze.
     * @param p the point
     * @return idk fam what do you think
     */
    public boolean hasPathAt(Point p) {
        return contains(p) && maze()[p.y][p.x];
    }

    /**
     * @return the maze handled by this generator
     */
    public boolean[][] maze() {
        return maze_;
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

    private Point[] adjacentPoints(Point point) {
        Point[] neighbors = new Point[]{
            new Point(point.x - 1, point.y),
            new Point(point.x + 1, point.y),
            new Point(point.x, point.y - 1),
            new Point(point.x, point.y + 1)
        };
        return Arrays.stream(neighbors)
            .filter(this::contains)
            .toArray(Point[]::new);
    }   
}
