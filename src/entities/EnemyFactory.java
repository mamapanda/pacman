package entities;

import java.util.List;

/**
 * @author Daniel Phan
 */
public interface EnemyFactory {
    List<Enemy> initialBatch();
    Enemy make();
}
