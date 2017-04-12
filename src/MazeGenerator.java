public class MazeGenerator {
    public MazeGenerator(int rows, int columns) {
        if ((rows & 1) == 0 || (columns & 1) == 0) {
            throw new IllegalArgumentException("Both rows and columns must be odd");
        } else if ((rows | columns) < 0) {
            throw new IllegalArgumentException("Both rows and columns must be positive.");
        }
        maze_ = new boolean[rows][columns];
    }

    public void generate() {
        prepare();
    }

    public boolean[][] maze() {
        return maze_;
    }

    private boolean[][] maze_;

    /**
     * Sets the core cells of the maze to "true".
     */
    private void prepare() {
        for (int i = 0; i < maze_.length; i += 2) {
            for (int j = 0; j < maze_[0].length; j += 2) {
                maze_[i][j] = true;
            }
        }
    }
}
