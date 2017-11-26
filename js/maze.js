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
new Maze.Maze(11, 11).generate();
