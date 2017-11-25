interface Drawable {
    draw(ctx: CanvasRenderingContext2D): void;
}

class GMaze extends Maze implements Drawable {
    public pathColor: string;
    public wallColor: string;
    public readonly tileWidth: number;

    public constructor(width: number, height: number, tileWidth: number) {
        super(width, height);
        this.tileWidth = tileWidth;
        this.pathColor = "black";
        this.wallColor = "dimgray";
    }

    public setColors(pathColor: string, wallColor: string): void {
        this.pathColor = pathColor;
        this.wallColor = wallColor;
    }

    public draw(ctx: CanvasRenderingContext2D): void {
        for (let row: number = 0; row < this.height; ++row) {
            for (let col: number = 0; col < this.width; ++col) {
                let path: boolean = this.pathAt(new Point(row, col));
                let color: string = path ? this.pathColor : this.wallColor;
                let x: number = col * this.tileWidth;
                let y: number = row * this.tileWidth;

                ctx.fillStyle = color;
                ctx.fillRect(x, y, this.tileWidth, this.tileWidth);
            }
        }
    }
}
