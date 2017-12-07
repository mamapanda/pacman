var Maze;
(function (Maze_1) {
    var Quadrant;
    (function (Quadrant) {
        Quadrant[Quadrant["I"] = 0] = "I";
        Quadrant[Quadrant["II"] = 1] = "II";
        Quadrant[Quadrant["III"] = 2] = "III";
        Quadrant[Quadrant["IV"] = 3] = "IV";
    })(Quadrant = Maze_1.Quadrant || (Maze_1.Quadrant = {}));
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
        Point.prototype.toString = function () {
            return "[Point: " + this.row + ", " + this.column + "]";
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
        Maze.prototype.quadrant = function (p) {
            var upperHalf = p.row >= 0 && p.row < this.rows / 2;
            var leftHalf = p.column >= 0 && p.column < this.columns / 2;
            if (upperHalf && leftHalf) {
                return Quadrant.I;
            }
            else if (upperHalf) {
                return Quadrant.II;
            }
            else if (leftHalf) {
                return Quadrant.III;
            }
            else {
                return Quadrant.IV;
            }
        };
        Maze.prototype.paths = function () {
            var points = [];
            for (var row = 0; row < this.rows; ++row) {
                for (var col = 0; col < this.columns; ++col) {
                    var point = new Point(row, col);
                    if (this.pathAt(point)) {
                        points.push(point);
                    }
                }
            }
            return points;
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
            if (direction == null) {
                return;
            }
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
    var PointNode = (function () {
        function PointNode(point, gScore, hScore, parent) {
            this.point = point;
            this.gScore = gScore;
            this.hScore = hScore;
            this.parent = parent;
        }
        PointNode.prototype.fScore = function () {
            return this.gScore + this.hScore;
        };
        return PointNode;
    }());
    function manhattanDistance(p1, p2) {
        return Math.abs(p1.row - p2.row) + Math.abs(p1.column - p2.column);
    }
    var Enemy = (function (_super) {
        __extends(Enemy, _super);
        function Enemy() {
            return _super !== null && _super.apply(this, arguments) || this;
        }
        Enemy.pathsFrom = function (pathAt, point) {
            return point.adjacents().filter(function (p) { return pathAt(p); });
        };
        return Enemy;
    }(Entity));
    Entity_1.Enemy = Enemy;
    var RandomEnemy = (function (_super) {
        __extends(RandomEnemy, _super);
        function RandomEnemy() {
            return _super !== null && _super.apply(this, arguments) || this;
        }
        RandomEnemy.prototype.move = function (pathAt, target) {
            var paths = Enemy.pathsFrom(pathAt, this.location);
            this.location = paths[Math.floor(Math.random() * paths.length)];
        };
        return RandomEnemy;
    }(Enemy));
    Entity_1.RandomEnemy = RandomEnemy;
    var AdvancedEnemy = (function (_super) {
        __extends(AdvancedEnemy, _super);
        function AdvancedEnemy() {
            return _super !== null && _super.apply(this, arguments) || this;
        }
        AdvancedEnemy.prototype.move = function (pathAt, target) {
            if (this.location.equals(target.location)) {
                return;
            }
            var goal = this.searchPath(pathAt, target.location);
            while (goal.parent.parent != null) {
                goal = goal.parent;
            }
            this.location = goal.point;
        };
        AdvancedEnemy.prototype.searchPath = function (pathAt, goal) {
            var _this = this;
            var start = new PointNode(this.location, 0, manhattanDistance(this.location, goal));
            var openSet = [start];
            var closedSet = [];
            var _loop_2 = function () {
                var node = openSet.reduce(function (p1, p2) { return p1.fScore() < p2.fScore() ? p1 : p2; });
                openSet = openSet.filter(function (p) { return p != node; });
                if (node.point.equals(goal)) {
                    return { value: node };
                }
                node.point.adjacents().forEach(function (p) {
                    if (pathAt(p) && !closedSet.some(function (visited) { return visited.equals(p); })) {
                        openSet.push(_this.heuristic(p, goal, node));
                    }
                });
                closedSet.push(node.point);
            };
            while (openSet.length > 0) {
                var state_1 = _loop_2();
                if (typeof state_1 === "object")
                    return state_1.value;
            }
            return null;
        };
        return AdvancedEnemy;
    }(Enemy));
    var GreedyEnemy = (function (_super) {
        __extends(GreedyEnemy, _super);
        function GreedyEnemy() {
            return _super !== null && _super.apply(this, arguments) || this;
        }
        GreedyEnemy.prototype.heuristic = function (point, goal, parent) {
            var gScore = parent == null ? 0 : parent.gScore + 1;
            var hScore = manhattanDistance(point, goal);
            return new PointNode(point, gScore, hScore, parent);
        };
        return GreedyEnemy;
    }(AdvancedEnemy));
    Entity_1.GreedyEnemy = GreedyEnemy;
    var DefaultFactory = (function () {
        function DefaultFactory() {
        }
        DefaultFactory.prototype.make = function (points) {
            var enemies = [];
            for (var i = 0; i < points.length; ++i) {
                switch (i % 2) {
                    case 0:
                        enemies.push(new RandomEnemy(points[i]));
                        break;
                    case 1:
                        enemies.push(new GreedyEnemy(points[i]));
                        break;
                }
            }
            return enemies;
        };
        return DefaultFactory;
    }());
    Entity_1.DefaultFactory = DefaultFactory;
})(Entity || (Entity = {}));
var State;
(function (State_1) {
    function shuffle(xs) {
        for (var i = xs.length - 1; i >= 0; --i) {
            var index = Math.floor((i + 1) * Math.random());
            var temp = xs[i];
            xs[i] = xs[index];
            xs[index] = temp;
        }
    }
    var State = (function () {
        function State(maze, enemyFactory, nStartingEnemies, enemyUpdateDelay) {
            this.maze = maze;
            this.enemyFactory = enemyFactory;
            this.nStartingEnemies = nStartingEnemies;
            this.enemyUpdateDelay = enemyUpdateDelay;
            this.iteration = 0;
            this.level = 0;
            this.pacman = new Entity.Pacman(new Maze.Point(0, 0));
            this.enemies = [];
        }
        State.prototype.initEnemies = function () {
            var _this = this;
            var nEnemies = this.level + this.nStartingEnemies - 1;
            var points = this.maze.paths().filter(function (p) { return _this.maze.quadrant(p) != Maze.Quadrant.I; });
            shuffle(points);
            points.splice(nEnemies, points.length);
            this.enemies = this.enemyFactory.make(points);
        };
        State.prototype.init = function () {
            ++this.level;
            this.maze.generate();
            this.pacman.location = new Maze.Point(0, 0);
            this.initEnemies();
        };
        State.prototype.advance = function () {
            var _this = this;
            this.pacman.move(function (p) { return _this.maze.pathAt(p); }, this.pacmanDirection);
            this.checkPacman();
            if (this.iteration % this.enemyUpdateDelay == 0) {
                this.enemies.forEach(function (e) {
                    e.move(function (p) { return _this.maze.pathAt(p); }, _this.pacman);
                });
            }
        };
        State.prototype.checkPacman = function () {
            var _this = this;
            this.enemies.forEach(function (e) {
                if (e.collidesWith(_this.pacman)) {
                    _this.pacman.alive = false;
                }
            });
        };
        return State;
    }());
    State_1.State = State;
})(State || (State = {}));
var Graphics;
(function (Graphics) {
    var MazeDrawer = (function () {
        function MazeDrawer(maze, pathColor, wallColor, tileWidth) {
            this.maze = maze;
            this.pathColor = pathColor;
            this.wallColor = wallColor;
            this.tileWidth = tileWidth;
        }
        MazeDrawer.prototype.draw = function (ctx) {
            for (var row = 0; row < this.maze.rows; ++row) {
                for (var col = 0; col < this.maze.columns; ++col) {
                    var path = this.maze.pathAt(new Maze.Point(row, col));
                    var color = path ? this.pathColor : this.wallColor;
                    var x = col * this.tileWidth;
                    var y = row * this.tileWidth;
                    ctx.fillStyle = color;
                    ctx.fillRect(x, y, this.tileWidth, this.tileWidth);
                }
            }
        };
        return MazeDrawer;
    }());
    Graphics.MazeDrawer = MazeDrawer;
    var EntityDrawer = (function () {
        function EntityDrawer(entity, width, imagePath) {
            this.entity = entity;
            this.width = width;
            this.image = new Image();
            this.image.src = imagePath;
        }
        EntityDrawer.prototype.draw = function (ctx) {
            var _this = this;
            if (this.image.complete) {
                var x = this.entity.location.column * this.width;
                var y = this.entity.location.row * this.width;
                ctx.drawImage(this.image, x, y, this.width, this.width);
            }
            else {
                this.image.onload = function () { return _this.draw(ctx); };
            }
        };
        return EntityDrawer;
    }());
    Graphics.EntityDrawer = EntityDrawer;
})(Graphics || (Graphics = {}));
var Program;
(function (Program_1) {
    var Program = (function () {
        function Program(state, pacmanImage, enemyImages, pathColor, wallColor, tileWidth, updateRate, canvasId) {
            this.state = state;
            this.pacmanImage = pacmanImage;
            this.enemyImages = enemyImages;
            this.pathColor = pathColor;
            this.wallColor = wallColor;
            this.tileWidth = tileWidth;
            this.updateRate = updateRate;
            this.initCanvas(canvasId);
        }
        Program.prototype.draw = function () {
            var _this = this;
            this.drawers.forEach(function (d) { return d.draw(_this.ctx); });
        };
        Program.prototype.start = function () {
            var _this = this;
            this.state.init();
            this.initDrawers();
            setInterval(function () {
                _this.draw();
                _this.state.advance();
                _this.state.pacmanDirection = null;
            }, this.updateRate);
        };
        Program.prototype.initCanvas = function (canvasId) {
            var canvas = document.getElementById(canvasId);
            canvas.width = this.state.maze.columns * this.tileWidth;
            canvas.height = this.state.maze.rows * this.tileWidth;
            this.ctx = canvas.getContext("2d");
        };
        Program.prototype.initDrawers = function () {
            this.drawers = [];
            this.drawers.push(new Graphics.MazeDrawer(this.state.maze, this.pathColor, this.wallColor, this.tileWidth));
            this.drawers.push(new Graphics.EntityDrawer(this.state.pacman, this.tileWidth, this.pacmanImage));
            for (var i = 0; i < this.state.enemies.length; ++i) {
                var index = i % this.enemyImages.length;
                var image = this.enemyImages[index];
                this.drawers.push(new Graphics.EntityDrawer(this.state.enemies[i], this.tileWidth, image));
            }
        };
        return Program;
    }());
    Program_1.Program = Program;
    var Builder = (function () {
        function Builder() {
        }
        Builder.prototype.build = function () {
            return new Program(this.state, this.pacmanImage, this.enemyImages, this.pathColor, this.wallColor, this.tileWidth, this.updateRate, this.canvasId);
        };
        Builder.prototype.setState = function (state) {
            this.state = state;
            return this;
        };
        Builder.prototype.setPacmanImage = function (pacmanImage) {
            this.pacmanImage = pacmanImage;
            return this;
        };
        Builder.prototype.setEnemyImages = function (enemyImages) {
            this.enemyImages = enemyImages;
            return this;
        };
        Builder.prototype.setPathColor = function (pathColor) {
            this.pathColor = pathColor;
            return this;
        };
        Builder.prototype.setWallColor = function (wallColor) {
            this.wallColor = wallColor;
            return this;
        };
        Builder.prototype.setTileWidth = function (tileWidth) {
            this.tileWidth = tileWidth;
            return this;
        };
        Builder.prototype.setUpdateRate = function (updateRate) {
            this.updateRate = updateRate;
            return this;
        };
        Builder.prototype.setCanvasId = function (canvasId) {
            this.canvasId = canvasId;
            return this;
        };
        return Builder;
    }());
    Program_1.Builder = Builder;
})(Program || (Program = {}));
var maze = new Maze.Maze(29, 49);
var state = new State.State(maze, new Entity.DefaultFactory(), 2, 2);
var program = new Program.Builder()
    .setState(state)
    .setPacmanImage("img/pacman.gif")
    .setEnemyImages(["img/blueghost.gif", "img/redghost.gif", "img/purpleghost.gif"])
    .setPathColor("black")
    .setWallColor("dimgray")
    .setTileWidth(20)
    .setUpdateRate(250)
    .setCanvasId("game-canvas")
    .build();
program.start();
