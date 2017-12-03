namespace Program {
    export class Program {
        public constructor(
            [rows, columns]: [number, number],
            enemyFactory: Entity.EnemyFactory,
            canvasId: string,
            pacmanImage: string,
            enemyImages: string[],
            tileWidth: number
        ) {
            this.maze = new Maze.Maze(rows, columns);
            this.enemyFactory = enemyFactory;
            this.enemies = []
            this.pacman = new Entity.Pacman(new Maze.Point(0, 0));
        }

        public draw(): void {
            for (let drawer of this.drawers) {
                drawer.draw(this.ctx);
            }
        }

        private maze: Maze.Maze;
        private enemyFactory: Entity.EnemyFactory;
        private enemies: Entity.Enemy[];
        private pacman: Entity.Pacman;

        private ctx: CanvasRenderingContext2D;
        private drawers: Graphics.Drawer[];

        private initEnemies(): void {

        }

        private initCanvas(canvasId: string, tileWidth: number): void {
            let canvas: HTMLCanvasElement =
                document.getElementById(canvasId) as HTMLCanvasElement;

            canvas.width = this.maze.columns * tileWidth;
            canvas.height = this.maze.rows * tileWidth;

            this.ctx = canvas.getContext("2d");
        }

        private initDrawers(
            pacmanImage: string,
            enemyImages: string[],
            tileWidth: number
        ): void {
            this.drawers = []

            let mazeDrawer = new Graphics.MazeDrawer(this.maze, tileWidth);
            mazeDrawer.setColors("black", "dimgray");

            this.drawers.push(mazeDrawer);

            this.drawers.push(
                new Graphics.EntityDrawer(this.pacman, pacmanImage, tileWidth)
            );

            for (let i: number = 0; i < this.enemies.length; ++i) {
                let image: string = enemyImages[i % enemyImages.length];

                this.drawers.push(
                    new Graphics.EntityDrawer(this.enemies[0], image, tileWidth)
                );
            }
        }
    }
}

let gMaze: Graphics.MazeDrawer = new Graphics.MazeDrawer(
    new Maze.Maze(29, 49), 20
);
let canvas: HTMLCanvasElement =
    <HTMLCanvasElement>document.getElementById("game-canvas");
let ctx: CanvasRenderingContext2D = canvas.getContext("2d");

canvas.width = gMaze.maze.columns * gMaze.tileWidth;
canvas.height = gMaze.maze.rows * gMaze.tileWidth;

gMaze.maze.generate();
gMaze.setColors("black", "dimgray");
gMaze.draw(ctx);

let gPacman: Graphics.EntityDrawer = new Graphics.EntityDrawer(
    new Entity.Pacman(new Maze.Point(0, 0)),
    "img/pacman.gif",
    20
);

gPacman.draw(ctx);
