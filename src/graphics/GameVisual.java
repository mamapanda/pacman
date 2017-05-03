package graphics;

import entities.Direction;
import entities.EnemyFactory;
import entities.Pacman;
import maze.Game;
import maze.MazeGenerator;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.swing.JFrame;

/**
 * @author Daniel Phan
 */
public class GameVisual extends Game {
    public GameVisual(MazeGenerator generator,
                      Pacman player,
                      EnemyFactory enemyFactory,
                      int goalCount,
                      String title) {
        super(generator, player, enemyFactory, goalCount);
        frame_ = new JFrame(title);
    }

    @Override
    protected Direction getPlayerMove() {
        throw new NotImplementedException();
    }

    private JFrame frame_;
}
