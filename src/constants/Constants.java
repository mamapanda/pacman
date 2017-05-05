package constants;

import java.awt.Color;

/**
 * @author Daniel Phan
 */
public class Constants {
    public static class Maze {
        public static final int ROWS = 31;
        public static final int COLUMNS = 51;
    }

    public static class Colors {
        public static final Color PATH = Color.WHITE;
        public static final Color WALL = Color.BLACK;
        public static final Color SPECIAL = Color.RED;
    }

    public static class Images {
        public static final String PLAYER = "pacman.gif";
        public static final String[] ENEMIES = {
            "redghost.gif", "purpleghost.gif", "blueghost.gif"
        };
    }

    public static class Graphics {
        public static final int UPDATE_DELAY = 100;
        public static final int CELL_SIZE = 50;
        public static final int CONTENT_WIDTH = Maze.COLUMNS * CELL_SIZE;
        public static final int CONTENT_HEIGHT = Maze.ROWS * CELL_SIZE;
    }
}
