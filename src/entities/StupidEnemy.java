package entities;

import maze.MazeGenerator;

import java.util.Arrays;
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
    public Direction getMove() {
        Direction[] moves = possibleMoves(location());
        return moves[rand_.nextInt(moves.length)];
    }

    private Random rand_;
}
