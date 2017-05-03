package entities;

import maze.MazeGenerator;

public class Pacman extends MazeEntity {
    public Pacman(MazeGenerator generator, int x, int y) {
        super(generator, x, y);
        alive_ = true;
    }

    public void die() {
        alive_ = false;
    }

    public boolean alive() {
        return alive_;
    }

    private boolean alive_;
}