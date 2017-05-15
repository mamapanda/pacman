package misc;

import java.awt.Point;

/**
 * @author Daniel Phan
 */
public class PointNode implements Comparable<PointNode> {
    public PointNode(PointNode parent, Point value, FScore score) {
        parent_ = parent;
        value_ = value;
        score_ = score;
    }

    public PointNode parent() {
        return parent_;
    }

    public Point value() {
        return value_;
    }

    public FScore score() {
        return score_;
    }

    @Override
    public int compareTo(PointNode other) {
        return score_.value() - other.score_.value();
    }

    public static class FScore {
        public FScore(int gScore, int hScore) {
            gScore_ = gScore;
            hScore_ = hScore;
        }

        public int value() {
            return gScore() + hScore();
        }

        public int gScore() {
            return gScore_;
        }

        public int hScore() {
            return hScore_;
        }

        private int gScore_;
        private int hScore_;
    }

    private PointNode parent_;
    private Point value_;
    private FScore score_;
}
