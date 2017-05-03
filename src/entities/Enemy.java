package entities;

import maze.MazeGenerator;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.awt.Point;
import java.util.Arrays;

public abstract class Enemy extends MazeEntity {
    public Enemy(MazeGenerator generator, Pacman target, int x, int y) {
        super(generator, x, y);
        target_ = target;
    }

    public void move() {
        super.move(getMove());
    }

    public Pacman target() {
        return target_;
    }

    protected abstract Direction getMove();

    protected int manhattanDistance(Point p) {
        throw new NotImplementedException();
    }

    protected Direction[] possibleMoves(Point p) {
        return Arrays.stream(Direction.values())
            .filter(d ->
                generator().hasPathAt(new Point(p.x + d.dx(), p.y + d.dy())))
            .toArray(Direction[]::new);
    }

    private Pacman target_;
}
