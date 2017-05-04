import entities.EnemyFactory;
import entities.GhostFactory;
import entities.Pacman;
import graphics.GameVisual;
import maze.MazeGenerator;

public class Program {
    public static void main(String[] args) {
        int rows = 31;
        int columns = 51;

        MazeGenerator generator = new MazeGenerator(rows, columns);
        Pacman player = new Pacman(generator, 0, 0);
        EnemyFactory factory = new GhostFactory(generator, player);

        GameVisual game = new GameVisual(generator, player, factory, 4, "Umi.jpg");
        game.run();
    }
}
