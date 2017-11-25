var maze = new GMaze(21, 21, 30);
var canvas = document.getElementById("game-canvas");
var ctx = canvas.getContext("2d");
canvas.width = maze.width * 30;
canvas.height = maze.height * 30;
maze.generatePaths();
maze.draw(ctx);
