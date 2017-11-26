namespace Graphics {
    export interface Drawable {
        draw(ctx: CanvasRenderingContext2D): void;
    }

    export class GMaze extends Maze.Maze implements Drawable {
        public pathColor: string;
        public wallColor: string;
        public readonly tileWidth: number;

        public constructor(rows: number, columns: number, tileWidth: number) {
            super(rows, columns);
            this.tileWidth = tileWidth;
            this.pathColor = "";
            this.wallColor = "";
        }

        public setColors(pathColor: string, wallColor: string): void {
            this.pathColor = pathColor;
            this.wallColor = wallColor;
        }

        public draw(ctx: CanvasRenderingContext2D): void {
            for (let row: number = 0; row < this.rows; ++row) {
                for (let col: number = 0; col < this.columns; ++col) {
                    let path: boolean = this.pathAt(new Maze.Point(row, col));
                    let color: string = path ? this.pathColor : this.wallColor;
                    let x: number = col * this.tileWidth;
                    let y: number = row * this.tileWidth;

                    ctx.fillStyle = color;
                    ctx.fillRect(x, y, this.tileWidth, this.tileWidth);
                }
            }
        }
    }

    export class GPacman extends Entity.Pacman implements Drawable {
        public constructor(location: Maze.Point, image: string, width: number) {
            super(location);
            this.image = new Image();
            this.image.src = image;
            this.width = width;
        }

        public draw(ctx: CanvasRenderingContext2D): void {
            if (this.image.complete) {
                let x: number = this.location.column * this.width;
                let y: number = this.location.row * this.width;
                ctx.drawImage(this.image, x, y, this.width, this.width);
            } else {
                this.image.onload = () => this.draw(ctx);
            }
        }

        private image: HTMLImageElement;
        private width: number;
    }
}
