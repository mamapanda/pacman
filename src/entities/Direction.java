package entities;

public enum Direction {
    UP(0, -1),
    LEFT(-1, 0),
    RIGHT(1, 0),
    DOWN(0, 1);

    Direction(int dx, int dy) {
        dx_ = dx;
        dy_ = dy;
    }

    public int dx() {
        return dx_;
    }

    public int dy() {
        return dy_;
    }

    private int dx_;
    private int dy_;
}
