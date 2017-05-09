package misc;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;

/**
 * @author Daniel Phan
 */
public class Constants {
    public static class Game {
        public static final int ROWS = 31;
        public static final int COLUMNS = 51;
        public static final int TILES_PER_QUADRANT = 2;
        public static final int INITIAL_GHOST_COUNT = 3;
        public static final Point PLAYER_START_LOCATION = new Point(0, 0);
    }

    public static class TileColors {
        public static final Color PATH = Color.BLACK;
        public static final Color WALL = Color.LIGHT_GRAY;
        public static final Color SPECIAL = Color.RED;
    }

    public static class Images {
        public static final String PLAYER = "pacman.gif";
        public static final String[] ENEMIES = {
            "redghost.gif", "purpleghost.gif", "blueghost.gif"
        };
    }

    public static class Graphics {
        public static final int UPDATE_DELAY = 75;
        public static final int CELL_SIZE = 20;
        public static final int CONTENT_WIDTH = Game.COLUMNS * CELL_SIZE;
        public static final int CONTENT_HEIGHT = Game.ROWS * CELL_SIZE;
    }

    public static class Transition {
        public static final int DELAY = 2000;
        public static final Color BG_COLOR = Color.BLACK;
        public static final Font TEXT_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 140);
        public static final Color TEXT_COLOR = Color.YELLOW;
    }
}
