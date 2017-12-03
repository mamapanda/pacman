namespace State {
    function shuffle(xs: any[]) {
        for (let i: number = xs.length - 1; i >= 0; --i) {
            let index: number = Math.floor(i * Math.random());

            let temp: any = xs[i];
            xs[i] = xs[index];
            xs[index] = xs[i];
        }
    }

    function randPoints(maze: Maze.Maze, nPoints: number): Maze.Point[] {
        let points: Maze.Point[] = [];

        for (let row: number = 0; row < maze.rows; ++row) {
            for (let col: number = 0; col < maze.columns; ++col) {
                let point: Maze.Point = new Maze.Point(row, col);

                if (maze.pathAt(point)) {
                    points.push(point);
                }
            }
        }

        shuffle(points);

        return points.slice(0, nPoints);
    }

    export class State {
        level: number;
        enemies: Entity.Enemy[];

        constructor(
            public maze: Maze.Maze,
            public pacman: Entity.Pacman,
            public enemyFactory: Entity.EnemyFactory,
            private nStartingEnemies: number,
            private enemyUpdateDelay: number
        ) {
            this.level = 1;
            this.enemies = []
        }

        initEnemies(): void {
            let nEnemies: number = this.level + this.nStartingEnemies - 1;
            let points: Maze.Point[] = randPoints(this.maze, nEnemies);

            this.enemies = this.enemyFactory.make(points);
        }
    }
}
