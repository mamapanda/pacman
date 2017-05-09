package game;

import misc.Constants;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Daniel Phan
 */
public class PointGenerator {
    public PointGenerator(Maze maze) {
        availablePaths_ =
            IntStream.range(0, Constants.Game.ROWS)
                .mapToObj(y ->
                    IntStream.range(0, Constants.Game.COLUMNS)
                        .mapToObj(x -> new Point(x, y))
                        .toArray(Point[]::new))
                .flatMap(Arrays::stream)
                .filter(maze::hasPathAt)
                .collect(Collectors.toCollection(ArrayList::new));
        Collections.shuffle(availablePaths_);
    }

    public Point next(Quadrant quadrant) {
        int xBoundL, xBoundR, yBoundU, yBoundD;

        switch (quadrant) {
            case I:
                xBoundL = 0;
                xBoundR = Constants.Game.COLUMNS / 2;
                yBoundU = 0;
                yBoundD = Constants.Game.ROWS / 2;
                break;
            case II:
                xBoundL = Constants.Game.COLUMNS / 2;
                xBoundR = Constants.Game.COLUMNS;
                yBoundU = 0;
                yBoundD = Constants.Game.ROWS / 2;
                break;
            case III:
                xBoundL = 0;
                xBoundR = Constants.Game.COLUMNS / 2;
                yBoundU = Constants.Game.ROWS / 2;
                yBoundD = Constants.Game.ROWS;
                break;
            case IV:
                xBoundL = Constants.Game.COLUMNS / 2;
                xBoundR = Constants.Game.COLUMNS;
                yBoundU = Constants.Game.ROWS / 2;
                yBoundD = Constants.Game.ROWS;
                break;
            default:
                throw new IllegalArgumentException("Fuck you @generatePoints");
        }

        Point tmp = availablePaths_.stream()
            .filter(location ->
                location.x >= xBoundL && location.x < xBoundR
                && location.y >= yBoundU && location.y < yBoundD)
            .findFirst().orElse(null);
        availablePaths_.remove(tmp);
        return tmp;
    }

    public static Point[] adjacents(Point point) {
        Point[] neighbors = new Point[]{
            new Point(point.x - 1, point.y),
            new Point(point.x + 1, point.y),
            new Point(point.x, point.y - 1),
            new Point(point.x, point.y + 1)
        };
        return Arrays.stream(neighbors)
            .filter(Maze::contains)
            .toArray(Point[]::new);
    }

    private List<Point> availablePaths_;
}
