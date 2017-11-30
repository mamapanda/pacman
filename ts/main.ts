namespace Program {
    export class Program {
        public constructor(
            mazeDimensions: [number, number],
            enemyFactory: Entity.EnemyFactory,
            canvasId: string,
            pacmanImage: string,
            enemyImages: string[],
            tileWidth: number
        ) {

        }

        private maze: Maze.Maze;
        private enemyFactory: Entity.EnemyFactory;
        private pacman: Entity.Pacman;

        private ctx: CanvasRenderingContext2D;
        private drawers: Graphics.Drawer[];

        private initCanvas(canvasId: string, tileWidth: number): void {
            let canvas: HTMLCanvasElement =
                document.getElementById(canvasId) as HTMLCanvasElement;

            canvas.width = this.maze.columns * tileWidth;
            canvas.height = this.maze.rows * tileWidth;

            this.ctx = canvas.getContext("2d");
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
