package entities;

import maze.MazeGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by helix on 5/3/2017.
 */
public class GhostFactory implements EnemyFactory
{
    public GhostFactory(MazeGenerator m, Pacman man)
    {
        p = man;
        maze = m;
    }

    public List<Enemy> initialBatch()
    {
        ArrayList<Enemy> es = new ArrayList<Enemy>();
        int x = 20;
        int y = 20;
        int z = 14;
        int a = 14;
        Enemy e = new RushEnemy();
        Enemy f = new SmartEnemy(maze,p,x,y);
        Enemy g = new StupidEnemy(maze,p,z,a);
    }

    public Enemy make()
    {
       int whichEnemy = (int) (Math.random() * 3);
       if (whichEnemy == 0)
       {
           StupidEnemy Kenny = new StupidEnemy()
       }
       else if (whichEnemy == 1)
       {

       }
       else
       {

       }
    }

    private Pacman p;
    private MazeGenerator maze;
}
