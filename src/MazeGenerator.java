import java.awt.Point;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

public class MazeGenerator {
    public MazeGenerator(int rows, int columns) {
        if ((rows & 1) == 0 || (columns & 1) == 0) {
            throw new IllegalArgumentException(
                    "Both rows and columns must be odd");
        } else if ((rows | columns) < 0) {
            throw new IllegalArgumentException(
                    "Both rows and columns must be positive.");
        }
        maze_ = new boolean[rows][columns];
        stack_ = new LinkedList<>();
        explored_ = new HashSet<>();
        rand_ = new Random();

        prepare();
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
     * @return the current visited point
     */
    public Point step() {
        if (finished()) return null;

        Point start = stack_.peekFirst();
        Point[] nexts = nextPoints(start);

        if (nexts.length > 0) {
            Point next = nexts[rand_.nextInt(nexts.length)];
            removeWall(start, next);
            stack_.addFirst(next);
            explored_.add(next);
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
    private HashSet<Point> explored_;
    private Random rand_;

    /**
     * Sets the core cells of the maze to "true"
     * and adds (0, 0) to stack_ & explored_.
     */
    private void prepare() {
        for (int i = 0; i < maze_.length; i += 2) {
            for (int j = 0; j < maze_[0].length; j += 2) {
                maze_[i][j] = true;
            }
        }

        Point first = new Point(0, 0);
        stack_.addFirst(first);
        explored_.add(first);
    }

    private void removeWall(Point p1, Point p2) {
        Point wall = new Point((p1.x + p2.x) / 2, (p1.y + p2.y) / 2);
        maze_[wall.x][wall.y] = true;
    }

    private Point[] nextPoints(Point point) {
        Point[] neighbors = new Point[]{
                new Point(point.x - 2, point.y),
                new Point(point.x + 2, point.y),
                new Point(point.x, point.y - 2),
                new Point(point.x, point.y + 2)
        };
        return Arrays.stream(neighbors)
                .filter(p -> contains(p) && !explored_.contains(p))
                .toArray(Point[]::new);
    }
}
