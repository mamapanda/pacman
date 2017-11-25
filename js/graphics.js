var __extends = (this && this.__extends) || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
};
var Graphics;
(function (Graphics) {
    var GMaze = (function (_super) {
        __extends(GMaze, _super);
        function GMaze(rows, columns, tileWidth) {
            var _this = _super.call(this, rows, columns) || this;
            _this.tileWidth = tileWidth;
            _this.pathColor = "";
            _this.wallColor = "";
            return _this;
        }
        GMaze.prototype.setColors = function (pathColor, wallColor) {
            this.pathColor = pathColor;
            this.wallColor = wallColor;
        };
        GMaze.prototype.draw = function (ctx) {
            for (var row = 0; row < this.rows; ++row) {
                for (var col = 0; col < this.columns; ++col) {
                    var path = this.pathAt(new Maze.Point(row, col));
                    var color = path ? this.pathColor : this.wallColor;
                    var x = col * this.tileWidth;
                    var y = row * this.tileWidth;
                    ctx.fillStyle = color;
                    ctx.fillRect(x, y, this.tileWidth, this.tileWidth);
                }
            }
        };
        return GMaze;
    }(Maze.Maze));
    Graphics.GMaze = GMaze;
})(Graphics || (Graphics = {}));
