let maze: Graphics.GMaze = new Graphics.GMaze(29, 49, 20);
let canvas: HTMLCanvasElement =
    <HTMLCanvasElement>document.getElementById("game-canvas");
let ctx: CanvasRenderingContext2D = canvas.getContext("2d");

canvas.width = maze.columns * maze.tileWidth;
canvas.height = maze.rows * maze.tileWidth;

maze.setColors("black", "dimgray");
maze.generate();
maze.draw(ctx);

let pacman: Graphics.GPacman = new Graphics.GPacman(
    new Maze.Point(0, 0),
    "img/pacman.gif",
    20
);
pacman.draw(ctx);
