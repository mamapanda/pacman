package entities;

import java.awt.Point;
import java.util.function.Function;

import game.MazeGenerator;

public abstract class MazeEntity {
    /**
     * Constructs a new MazeEntity.
     * @param x this entity's starting x position
     * @param y this entity's starting y position
     */
    public MazeEntity(int x, int y) {
        location_ = new Point(x, y);
    }

    /**
     * Checks if this entity collides with another one.
     * @param other the other entity
     * @return whether or not the two entities collide
     */
    public boolean collidesWith(MazeEntity other) {
        return location_.equals(other.location_);
    }

    public Point location() {
        return location_;
    }

    protected void setLocation(Point p) {
        location_ = p;
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
}
