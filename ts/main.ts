let gMaze: Graphics.GMaze = new Graphics.GMaze(
    new Maze.Maze(29, 49), 20
);
let canvas: HTMLCanvasElement =
    <HTMLCanvasElement>document.getElementById("game-canvas");
let ctx: CanvasRenderingContext2D = canvas.getContext("2d");

canvas.width = gMaze.base.columns * gMaze.tileWidth;
canvas.height = gMaze.base.rows * gMaze.tileWidth;

gMaze.base.generate();
gMaze.setColors("black", "dimgray");
gMaze.draw(ctx);

let gPacman: Graphics.GEntity = new Graphics.GEntity(
    new Entity.Pacman(new Maze.Point(0, 0)),
    "img/pacman.gif",
    20
);

gPacman.draw(ctx);
