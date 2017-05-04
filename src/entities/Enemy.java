package entities;

import maze.MazeGenerator;

import java.awt.Point;
import java.util.Arrays;

public abstract class Enemy extends MazeEntity {
    public Enemy(MazeGenerator generator, Pacman target, int x, int y) {
        super(generator, x, y);
        target_ = target;
    }

    public abstract void move();

    public Pacman target() {
        return target_;
    }

    protected int manhattanDistance(Point p) {
        return Math.abs(p.x - target().x()) + Math.abs(p.y - target().y());
    }

    protected Direction[] possibleMoves(Point p) {
        return Arrays.stream(Direction.values())
            .filter(d ->
                generator().hasPathAt(new Point(p.x + d.dx(), p.y + d.dy())))
            .toArray(Direction[]::new);
    }

    private Pacman target_;
}
