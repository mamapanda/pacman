package entities;


import maze.MazeGenerator;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Write a description of class SmartEnemy here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SmartEnemy extends Enemy
{
    public SmartEnemy(MazeGenerator generator, Pacman target, int x, int y)
    {
        super(generator, target, x, y);
    }

    public Direction getMove()
    {
        throw new NotImplementedException();
    }
}
