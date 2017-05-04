package entities;

import maze.MazeGenerator;

import java.util.Random;

/**
 * @author Daniel Phan
 */
public class StupidEnemy extends Enemy {
    public StupidEnemy(MazeGenerator generator, Pacman target, int x, int y) {
        super(generator, target, x, y);
        rand_ = new Random();
    }

    @Override
    public void move() {
        Direction[] moves = possibleMoves(location());
        Direction d = moves[rand_.nextInt(moves.length)];
        location().translate(d.dx(), d.dy());
    }

    private Random rand_;
}
