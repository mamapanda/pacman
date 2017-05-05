package graphics;

import constants.Constants;
import entities.MazeEntity;

import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

public class EntityVisual extends JComponent {
    public EntityVisual(MazeEntity core, String imgName) {
        core_ = core;
        img_ = ImageLoader.load(imgName);
    }

    public MazeEntity core() {
        return core_;
    }

    public Image image() {
        return img_;
    }

    @Override
    public void paintComponent(Graphics g) {
        int cellSize = Constants.Graphics.CELL_SIZE;
        int x = core().x() * cellSize;
        int y = core().y() * cellSize;

        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(image(), x, y, cellSize, cellSize, null);
    }

    private MazeEntity core_;
    private Image img_;
}
