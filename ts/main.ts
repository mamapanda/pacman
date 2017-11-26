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
