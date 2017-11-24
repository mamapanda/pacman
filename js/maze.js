"use strict";
var Point = (function () {
    function Point(x, y) {
        this.x = x;
        this.y = y;
    }
    return Point;
}());
exports.Point = Point;
var Maze = (function () {
    function Maze(width, height) {
        this.width = width;
        this.height = height;
    }
    Maze.prototype.contains = function (p) {
        return p.x >= 0 && p.x < this.width && p.y >= 0 && p.y < this.height;
    };
    Maze.prototype.pathAt = function (p) {
        return this.contains(p) && this.tiles[p.y][p.x];
    };
    Maze.prototype.generatePaths = function () {
        var start = new Point(0, 0);
        var stack = [start];
        this.tiles[start.y][start.x] = true;
        this.resetTiles();
        while (stack.length > 0) {
            var current = stack[stack.length - 1];
            var nexts = this.unbuiltPaths(current);
            if (nexts.length > 0) {
                var i = Math.floor(Math.random() * nexts.length);
                var next = nexts[i];
                this.makePath(current, next);
                stack.push(next);
            }
            else {
                stack.pop();
            }
        }
    };
    Maze.prototype.resetTiles = function () {
        this.tiles = [];
        for (var row = 0; row < this.width; ++row) {
            this.tiles[row] = [];
            for (var col = 0; col < this.height; ++col) {
                this.tiles[row][col] = false;
            }
        }
    };
    Maze.prototype.unbuiltPaths = function (p) {
        var _this = this;
        var paths = [
            new Point(p.x - 2, p.y),
            new Point(p.x + 2, p.y),
            new Point(p.x, p.y - 2),
            new Point(p.x, p.y + 2)
        ];
        return paths.filter(function (point) {
            return _this.contains(point) && !_this.pathAt(point);
        });
    };
    Maze.prototype.makePath = function (start, end) {
        var wall = new Point(Math.floor((start.x + end.x) / 2), Math.floor((start.y + end.y) / 2));
        this.tiles[start.y][start.x] = true;
        this.tiles[wall.y][wall.x] = true;
        this.tiles[end.y][end.x] = true;
    };
    return Maze;
}());
exports.Maze = Maze;
