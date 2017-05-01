package entities;

import maze.MazeGenerator;

public abstract class Enemy extends MazeEntity {
    public Enemy(MazeGenerator generator, int x, int y) {
        super(generator, x, y);
    }

    protected abstract Direction getMove();

    public void move() {
        super.move(getMove());
    }
}
