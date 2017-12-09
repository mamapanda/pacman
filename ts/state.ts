namespace Base {
    export class State {
        iteration: number;
        pacman: Pacman;
        pacmanDirection: Direction;
        enemies: Enemy[];

        constructor(
            public maze: Maze,
            public enemyFactory: EnemyFactory,
            private enemyUpdateDelay: number,
            private enemyAddDelay: number,
            nStartingEnemies: number
        ) {
            this.iteration = 0;
            this.pacman = new Pacman(new Point(0, 0));
            this.enemies = [];

            this.init(nStartingEnemies);
        }

        addEnemy(): void {
            let pacmanQuadrant = this.maze.quadrant(this.pacman.location);
            let points: Point[] = this.maze.paths().filter(p => {
                return this.maze.quadrant(p) != pacmanQuadrant
                    && !this.enemies.some(e => e.location.equals(p));
            });

            shuffle(points);

            this.enemies.push(this.enemyFactory.make(points[0]));
        }

        advance(): boolean {
            ++this.iteration;

            this.pacman.move(p => this.maze.pathAt(p), this.pacmanDirection);

            if (this.iteration % this.enemyUpdateDelay == 0) {
                this.updateEnemies();
            }

            if (this.iteration % this.enemyAddDelay == 0) {
                this.addEnemy();
                return true;
            }

            return false;
        }

        private initEnemies(nStartingEnemies: number): void {
            for (let i: number = 0; i < nStartingEnemies; ++i) {
                this.addEnemy();
            }
        }

        private init(nStartingEnemies: number): void {
            this.maze.generate();
            this.pacman.location = new Point(0, 0);
            this.initEnemies(nStartingEnemies);
        }

        private updateEnemies(): void {
            this.enemies.forEach(e => {
                e.move(p => this.maze.pathAt(p), this.pacman);
                if (e.collidesWith(this.pacman)) {
                    this.pacman.alive = false;
                }
            });
        }
    }
}
