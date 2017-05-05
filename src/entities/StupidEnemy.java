package entities;

import maze.MazeGenerator;

/**
 * @author Daniel Phan
 */
public class StupidEnemy extends Enemy {
    public StupidEnemy(MazeGenerator generator, int x, int y) {
        super(generator, x, y);
    }

    @Override
    public void move() {
        Direction[] moves = possibleMoves(location());
        Direction d = moves[(int) (Math.random() * moves.length)];
        location().translate(d.dx(), d.dy());
    }
}
