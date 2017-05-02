package graphics;

import entities.Enemy;

import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

public class EnemyVisual extends JComponent {
    public EnemyVisual(Enemy core, String imgName, int cellLength) {
        core_ = core;
        img_ = ImageLoader.load(imgName);
        cellLength_ = cellLength;
    }

    public Enemy core() {
        return core_;
    }

    public Image image() {
        return img_;
    }

    @Override
    public void paintComponent(Graphics g) {
        int x = core().x() * cellLength_;
        int y = core().y() * cellLength_;

        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(image(), x, y, cellLength_, cellLength_, null);
    }

    private Enemy core_;
    private Image img_;
    private int cellLength_;
}
