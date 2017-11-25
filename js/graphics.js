var __extends = (this && this.__extends) || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
};
var GMaze = (function (_super) {
    __extends(GMaze, _super);
    function GMaze(width, height, tileWidth) {
        var _this = _super.call(this, width, height) || this;
        _this.tileWidth = tileWidth;
        _this.pathColor = "black";
        _this.wallColor = "dimgray";
        return _this;
    }
    GMaze.prototype.setColors = function (pathColor, wallColor) {
        this.pathColor = pathColor;
        this.wallColor = wallColor;
    };
    GMaze.prototype.draw = function (ctx) {
        for (var row = 0; row < this.height; ++row) {
            for (var col = 0; col < this.width; ++col) {
                var path = this.pathAt(new Point(row, col));
                var color = path ? this.pathColor : this.wallColor;
                var x = col * this.tileWidth;
                var y = row * this.tileWidth;
                ctx.fillStyle = color;
                ctx.fillRect(x, y, this.tileWidth, this.tileWidth);
            }
        }
    };
    return GMaze;
}(Maze));
