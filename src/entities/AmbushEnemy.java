package entities;


import maze.MazeGenerator;
import misc.Constants;

import java.awt.Point;

/**
 * An enemy that targets a point twice the vector length of the blinky to pac man plus two vector.
 *
 * @author (Keeny Kwatch)
 * @version (05 05 17)
 */
public class AmbushEnemy extends SmartEnemy {
    public AmbushEnemy(MazeGenerator generator, Pacman target, int x, int y) {
        super(generator, target, x, y);
        e = null;
    }

    public void setLeader(AdvancedEnemy a) {
        e = a;
    }

    @Override
    public Point targetLocation() {
        int tarX;
        int tarY;
        Point pacMan = super.targetLocation();
        Point smart = e.location();
        int pacX = pacMan.x + 2;
        int pacY = pacMan.y + 2;
        int orientation = 0; // 0 means lower right vector. goes counterclockwise.

        if (pacX >= Constants.Maze.COLUMNS) {
            pacX -= 4;
        }
        if (pacY >= Constants.Maze.ROWS) {
            pacY -= 4;
        }

        if (pacMan.x >= smart.x && pacMan.y <= smart.y) {
            orientation = 1;
        } else if (pacMan.x <= smart.x && pacMan.y <= smart.y) {
            orientation = 2;
        } else if (pacMan.x <= smart.x && pacMan.y > smart.y) {
            orientation = 3;
        }

        int dispX = Math.abs(smart.x - pacX);
        int dispY = Math.abs(smart.y - pacY);

        if (orientation == 1) {
            tarX = (2 * dispX) + smart.x;
            tarY = -(2 * dispY) + smart.y;
        } else if (orientation == 2) {
            tarX = -(2 * dispX) + smart.x;
            tarY = -(2 * dispY) + smart.y;
        } else if (orientation == 3) {
            tarX = (2 * dispX) + smart.x;
            tarY = -(2 * dispY) + smart.y;
        } else {
            tarX = (2 * dispX) + smart.x;
            tarY = (2 * dispY) + smart.y;
        }

        if (tarX >= Constants.Maze.COLUMNS) {
            tarX = Constants.Maze.COLUMNS - 1;
        }
        if (tarY >= Constants.Maze.ROWS) {
            tarY = Constants.Maze.ROWS - 1;
        }
        if (tarX < 0) {
            tarX = 0;
        }
        if (tarY < 0) {
            tarY = 0;
        }

        if (tarX < Constants.Maze.COLUMNS
            && tarY < Constants.Maze.ROWS
            && !generator().maze()[tarY][tarX]) {
            int flipper = 0;
            while (tarX < Constants.Maze.COLUMNS
                && tarY < Constants.Maze.ROWS
                && !generator().maze()[tarY][tarX]) {
                if (flipper == 0) {
                    tarX++;
                    flipper = 1;
                } else {
                    tarY++;
                    flipper = 0;
                }
            }
        }

        return new Point(tarX, tarY);
    }

    private AdvancedEnemy e;
}
