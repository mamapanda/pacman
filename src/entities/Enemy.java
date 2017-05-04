package entities;

import maze.MazeGenerator;

import java.awt.Point;
import java.util.Arrays;

public abstract class Enemy extends MazeEntity {
    public Enemy(MazeGenerator generator, int x, int y) {
        super(generator, x, y);
    }

    public abstract void move();

    protected Direction[] possibleMoves(Point p) {
        return Arrays.stream(Direction.values())
            .filter(d ->
                generator().hasPathAt(new Point(p.x + d.dx(), p.y + d.dy())))
            .toArray(Direction[]::new);
    }
}
