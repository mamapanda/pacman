package entities.enemies;

import entities.Direction;
import entities.Pacman;

import java.awt.Point;
import java.util.function.Function;

/**
 * @author Daniel Phan
 */
public class StupidEnemy extends Enemy {
    public StupidEnemy(int x, int y) {
        super(x, y);
    }

    @Override
    public void move(Function<Point, Boolean> isPath, Pacman target) {
        Direction[] moves = possibleMoves(isPath, location());
        Direction d = moves[(int) (Math.random() * moves.length)];
        location().translate(d.dx(), d.dy());
    }
}
