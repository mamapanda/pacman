package entities;

import java.awt.Point;
import maze.MazeGenerator;

public abstract class MazeEntity {
    /**
     * Constructs a new MazeEntity.
     * @param generator the MazeGenerator that handles this entity's maze
     * @param x this entity's starting x position
     * @param y this entity's starting y position
     */
    public MazeEntity(MazeGenerator generator, int x, int y) {
        generator_ = generator;
        location_ = new Point(x, y);
    }

    /**
     * Moves this entity in the given direction.
     * @param d the direction (must be a valid move)
     */
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

    /**
     * Checks if this entity collides with another one.
     * @param other the other entity
     * @return whether or not the two entities collide
     */
    public boolean collidesWith(MazeEntity other) {
        return location_.equals(other.location_);
    }

    /**
     * @return This entity's x location.
     */
    public int x() {
        return location_.x;
    }

    /**
     * @return This entity's y location.
     */
    public int y() {
        return location_.y;
    }

    private Point location_;
    private MazeGenerator generator_;
}
