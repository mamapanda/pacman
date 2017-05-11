package entities;

import java.awt.Point;
import java.util.function.Function;

public class Pacman extends MazeEntity {
    public Pacman(int x, int y) {
        super(x, y);
        alive_ = true;
    }

    /**
     * Moves this entity in the given direction.
     * @param d the direction (must be a valid move)
     */
    public void move(Function<Point, Boolean> isPath, Direction d) {
        Point newLoc = new Point(location());

        switch (d) {
            case UP:
                newLoc.y--;
                break;
            case LEFT:
                newLoc.x--;
                break;
            case RIGHT:
                newLoc.x++;
                break;
            case DOWN:
                newLoc.y++;
                break;
        }
        if (!isPath.apply(newLoc)) {
            throw new IllegalArgumentException("Illegal move error.");
        }

        setLocation(newLoc);
    }

    public void die() {
        alive_ = false;
    }

    public boolean alive() {
        return alive_;
    }

    private boolean alive_;
}