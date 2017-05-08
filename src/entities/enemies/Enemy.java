package entities.enemies;

import entities.Direction;
import entities.MazeEntity;
import entities.Pacman;

import java.awt.Point;
import java.util.Arrays;
import java.util.function.Function;

public abstract class Enemy extends MazeEntity {
    public Enemy(int x, int y) {
        super(x, y);
    }

    public abstract void move(Function<Point, Boolean> isPath, Pacman target);

    protected Direction[] possibleMoves(Function<Point, Boolean> isPath, Point p) {
        return Arrays.stream(Direction.values())
            .filter(d -> isPath.apply(new Point(p.x + d.dx(), p.y + d.dy())))
            .toArray(Direction[]::new);
    }
}
