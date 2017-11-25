var maze = new Graphics.GMaze(29, 49, 20);
var canvas = document.getElementById("game-canvas");
var ctx = canvas.getContext("2d");
canvas.width = maze.columns * maze.tileWidth;
canvas.height = maze.rows * maze.tileWidth;
maze.setColors("black", "dimgray");
maze.generate();
maze.draw(ctx);
