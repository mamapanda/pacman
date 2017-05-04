package entities;


import maze.MazeGenerator;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Write a description of class RushEnemy here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class RushEnemy extends Enemy {
    public RushEnemy(MazeGenerator generator, Pacman target, int x, int y) {
        super(generator, target, x, y);
    }

    @Override
    public void move() {
        throw new NotImplementedException();
    }

    @Override
    protected Direction getMove() {
        throw new NotImplementedException(); //lol refactoring comes later
    }
}
