package entities;

import java.awt.Point;
import maze.MazeGenerator;

public abstract class MazeEntity {
    public MazeEntity(MazeGenerator generator, int x, int y) {
        generator_ = generator;
        location_ = new Point(x, y);
    }

    public void move(Direction d) {
        Point newLoc = new Point(location_);

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
        if (!generator_.contains(newLoc) || !generator_.maze()[newLoc.y][newLoc.x]) {
            throw new IllegalArgumentException("Illegal move error.");
        }

        location_ = newLoc;
    }

    public boolean collidesWith(MazeEntity other) {
        return location_.equals(other.location_);
    }

    public int x() {
        return location_.x;
    }

    public int y() {
        return location_.y;
    }

    private Point location_;
    private MazeGenerator generator_;
}
