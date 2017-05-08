package entities.enemies;


import entities.Pacman;
import misc.Constants;

import java.awt.Point;
import java.util.function.Function;

/**
 * An enemy that targets a point twice the vector length of the blinky to pac man plus two vector.
 *
 * @author (Keeny Kwatch)
 * @version (05 05 17)
 */
public class AmbushEnemy extends SmartEnemy {
    public AmbushEnemy(int x, int y, AdvancedEnemy leader) {
        super(x, y);
        e = leader;
    }

    @Override
    public void move(Function<Point, Boolean> isPath, Pacman target) {
        int tarX;
        int tarY;
        Point pacMan = target.location();
        Point smart = e.location();
        int pacX = pacMan.x + 2;
        int pacY = pacMan.y + 2;
        int orientation = 0; // 0 means lower right vector. goes counterclockwise.

        if (pacX >= Constants.Game.COLUMNS) {
            pacX -= 4;
        }
        if (pacY >= Constants.Game.ROWS) {
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

        if (tarX >= Constants.Game.COLUMNS) {
            tarX = Constants.Game.COLUMNS - 1;
        }
        if (tarY >= Constants.Game.ROWS) {
            tarY = Constants.Game.ROWS - 1;
        }
        if (tarX < 0) {
            tarX = 0;
        }
        if (tarY < 0) {
            tarY = 0;
        }

        Point tar = new Point(tarX, tarY);

        int flipper = 0;
        while (isPath.apply(tar)) {
            if (flipper == 0) {
                tarX++;
                flipper = 1;
            } else {
                tarY++;
                flipper = 0;
            }
        }

    }

    private AdvancedEnemy e;
}
