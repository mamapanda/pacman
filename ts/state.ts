namespace State {
    function shuffle(xs: any[]) {
        for (let i: number = xs.length - 1; i >= 0; --i) {
            let index: number = Math.floor((i + 1) * Math.random());

            let temp: any = xs[i];
            xs[i] = xs[index];
            xs[index] = temp;
        }
    }

    export class State {
        iteration: number;
        level: number;
        pacman: Entity.Pacman;
        pacmanDirection: Entity.Direction;
        enemies: Entity.Enemy[];

        constructor(
            public maze: Maze.Maze,
            public enemyFactory: Entity.EnemyFactory,
            private nStartingEnemies: number,
            private enemyUpdateDelay: number
        ) {
            this.iteration = 0;
            this.level = 0;
            this.pacman = new Entity.Pacman(new Maze.Point(0, 0));
            this.enemies = []
        }

        initEnemies(): void {
            let nEnemies: number = this.level + this.nStartingEnemies - 1;
            let points: Maze.Point[] = this.maze.paths().filter(
                p => this.maze.quadrant(p) != Maze.Quadrant.I
            );

            shuffle(points);
            points.splice(nEnemies, points.length);

            this.enemies = this.enemyFactory.make(points);
        }

        init(): void {
            ++this.level;

            this.maze.generate();
            this.pacman.location = new Maze.Point(0, 0);
            this.initEnemies();
        }

        advance(): void {
            this.pacman.move(p => this.maze.pathAt(p), this.pacmanDirection);
            this.checkPacman();

            if (this.iteration % this.enemyUpdateDelay == 0) {
                this.enemies.forEach(e => {
                    e.move(p => this.maze.pathAt(p), this.pacman)
                });
            }
        }

        private checkPacman(): void {
            this.enemies.forEach(e => {
                if (e.collidesWith(this.pacman)) {
                    this.pacman.alive = false;
                }
            });
        }
    }
}
