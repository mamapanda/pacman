var Maze;
(function (Maze_1) {
    var Point = (function () {
        function Point(row, column) {
            this.row = row;
            this.column = column;
        }
        Point.prototype.adjacents = function () {
            return [
                new Point(this.row - 1, this.column),
                new Point(this.row + 1, this.column),
                new Point(this.row, this.column - 1),
                new Point(this.row, this.column + 1)
            ];
        };
        Point.prototype.copy = function () {
            return new Point(this.row, this.column);
        };
        Point.prototype.equals = function (other) {
            return this.row == other.row && this.column == other.column;
        };
        return Point;
    }());
    Maze_1.Point = Point;
    var Maze = (function () {
        function Maze(rows, columns) {
            this.rows = rows;
            this.columns = columns;
        }
        Maze.prototype.contains = function (p) {
            return p.row >= 0 && p.row < this.rows &&
                p.column >= 0 && p.column < this.columns;
        };
        Maze.prototype.pathAt = function (p) {
            return this.contains(p) && this.tiles[p.row][p.column];
        };
        Maze.prototype.generate = function () {
            this.resetTiles();
            this.generatePaths();
            this.patchDeadEnds();
        };
        Maze.prototype.resetTiles = function () {
            this.tiles = [];
            for (var row = 0; row < this.rows; ++row) {
                this.tiles[row] = [];
                for (var col = 0; col < this.columns; ++col) {
                    this.tiles[row][col] = false;
                }
            }
        };
        Maze.prototype.unbuiltPaths = function (p) {
            var _this = this;
            var paths = [
                new Point(p.row - 2, p.column),
                new Point(p.row + 2, p.column),
                new Point(p.row, p.column - 2),
                new Point(p.row, p.column + 2)
            ];
            return paths.filter(function (point) {
                return _this.contains(point) && !_this.pathAt(point);
            });
        };
        Maze.prototype.makePath = function (start, end) {
            var wall = new Point(Math.floor((start.row + end.row) / 2), Math.floor((start.column + end.column) / 2));
            this.tiles[start.row][start.column] = true;
            this.tiles[wall.row][wall.column] = true;
            this.tiles[end.row][end.column] = true;
        };
        Maze.prototype.generatePaths = function () {
            var start = new Point(0, 0);
            var stack = [start];
            this.tiles[start.row][start.column] = true;
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
        Maze.prototype.pathDistance = function (start, end) {
            var _this = this;
            if (!this.pathAt(start) || !(this.pathAt(end))) {
                return -1;
            }
            var queue = [[start, 0]];
            var visited = [];
            while (queue.length > 0) {
                var point = void 0, distance = void 0;
                _a = queue.shift(), point = _a[0], distance = _a[1];
                if (point.equals(end)) {
                    return distance;
                }
                var _loop_1 = function (neighbor) {
                    if (!visited.some(function (p) { return neighbor.equals(p); })) {
                        queue.push([neighbor, distance + 1]);
                    }
                };
                for (var _i = 0, _b = point.adjacents().filter(function (p) { return _this.pathAt(p); }); _i < _b.length; _i++) {
                    var neighbor = _b[_i];
                    _loop_1(neighbor);
                }
                visited.push(point);
            }
            var _a;
        };
        Maze.prototype.farthestPoint = function (start, points) {
            var _this = this;
            var distances = points.map(function (p) { return [p, _this.pathDistance(start, p)]; });
            return distances.sort(function (lhs, rhs) { return rhs[1] - lhs[1]; })[0][0];
        };
        Maze.prototype.deadEndAt = function (point) {
            var _this = this;
            var adjacentPaths = point.adjacents()
                .filter(function (p) { return _this.pathAt(p); });
            return adjacentPaths.length == 1;
        };
        Maze.prototype.patchDeadEnds = function () {
            var _this = this;
            for (var row = 0; row < this.rows; row += 2) {
                for (var col = 0; col < this.columns; col += 2) {
                    var point = new Point(row, col);
                    if (this.deadEndAt(point)) {
                        var candidates = [
                            new Point(point.row - 2, point.column),
                            new Point(point.row + 2, point.column),
                            new Point(point.row, point.column - 2),
                            new Point(point.row, point.column + 2)
                        ].filter(function (p) { return _this.pathAt(p); });
                        this.makePath(point, this.farthestPoint(point, candidates));
                    }
                }
            }
        };
        return Maze;
    }());
    Maze_1.Maze = Maze;
})(Maze || (Maze = {}));
var __extends = (this && this.__extends) || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
};
var Entity;
(function (Entity_1) {
    var Direction;
    (function (Direction) {
        Direction[Direction["Up"] = 0] = "Up";
        Direction[Direction["Down"] = 1] = "Down";
        Direction[Direction["Left"] = 2] = "Left";
        Direction[Direction["Right"] = 3] = "Right";
    })(Direction = Entity_1.Direction || (Entity_1.Direction = {}));
    var Entity = (function () {
        function Entity(location) {
            this.location = location;
        }
        Entity.prototype.collidesWith = function (other) {
            return this.location.equals(other.location);
        };
        return Entity;
    }());
    Entity_1.Entity = Entity;
    var Pacman = (function (_super) {
        __extends(Pacman, _super);
        function Pacman(location) {
            var _this = _super.call(this, location) || this;
            _this.alive = true;
            return _this;
        }
        Pacman.prototype.move = function (pathAt, direction) {
            var newLocation = this.location.copy();
            switch (direction) {
                case Direction.Up:
                    --newLocation.row;
                    break;
                case Direction.Down:
                    ++newLocation.row;
                    break;
                case Direction.Left:
                    --newLocation.column;
                    break;
                case Direction.Right:
                    ++newLocation.column;
                    break;
            }
            if (pathAt(newLocation)) {
                this.location = newLocation;
            }
        };
        return Pacman;
    }(Entity));
    Entity_1.Pacman = Pacman;
    var Enemy = (function (_super) {
        __extends(Enemy, _super);
        function Enemy() {
            return _super !== null && _super.apply(this, arguments) || this;
        }
        return Enemy;
    }(Entity));
    Entity_1.Enemy = Enemy;
})(Entity || (Entity = {}));
var Graphics;
(function (Graphics) {
    var GMaze = (function () {
        function GMaze(base, tileWidth) {
            this.base = base;
            this.tileWidth = tileWidth;
            this.pathColor = "";
            this.wallColor = "";
        }
        GMaze.prototype.setColors = function (pathColor, wallColor) {
            this.pathColor = pathColor;
            this.wallColor = wallColor;
        };
        GMaze.prototype.draw = function (ctx) {
            for (var row = 0; row < this.base.rows; ++row) {
                for (var col = 0; col < this.base.columns; ++col) {
                    var path = this.base.pathAt(new Maze.Point(row, col));
                    var color = path ? this.pathColor : this.wallColor;
                    var x = col * this.tileWidth;
                    var y = row * this.tileWidth;
                    ctx.fillStyle = color;
                    ctx.fillRect(x, y, this.tileWidth, this.tileWidth);
                }
            }
        };
        return GMaze;
    }());
    Graphics.GMaze = GMaze;
    var GEntity = (function () {
        function GEntity(base, imagePath, width) {
            this.base = base;
            this.image = new Image();
            this.image.src = imagePath;
            this.width = width;
        }
        GEntity.prototype.draw = function (ctx) {
            var _this = this;
            if (this.image.complete) {
                var x = this.base.location.column * this.width;
                var y = this.base.location.row * this.width;
                ctx.drawImage(this.image, x, y, this.width, this.width);
            }
            else {
                this.image.onload = function () { return _this.draw(ctx); };
            }
        };
        return GEntity;
    }());
    Graphics.GEntity = GEntity;
})(Graphics || (Graphics = {}));
var gMaze = new Graphics.GMaze(new Maze.Maze(29, 49), 20);
var canvas = document.getElementById("game-canvas");
var ctx = canvas.getContext("2d");
canvas.width = gMaze.base.columns * gMaze.tileWidth;
canvas.height = gMaze.base.rows * gMaze.tileWidth;
gMaze.base.generate();
gMaze.setColors("black", "dimgray");
gMaze.draw(ctx);
var gPacman = new Graphics.GEntity(new Entity.Pacman(new Maze.Point(0, 0)), "img/pacman.gif", 20);
gPacman.draw(ctx);
