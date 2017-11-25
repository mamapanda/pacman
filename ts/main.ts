let maze: GMaze = new GMaze(21, 21, 30);
let canvas: HTMLCanvasElement =
    <HTMLCanvasElement>document.getElementById("game-canvas");
let ctx: CanvasRenderingContext2D = canvas.getContext("2d");

canvas.width = maze.width * 30;
canvas.height = maze.height * 30;

maze.generatePaths();
maze.draw(ctx);
