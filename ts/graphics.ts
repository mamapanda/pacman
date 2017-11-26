namespace Graphics {
    export interface Drawable {
        draw(ctx: CanvasRenderingContext2D): void;
    }

    export class GMaze implements Drawable {
        public base: Maze.Maze;
        public pathColor: string;
        public wallColor: string;
        public readonly tileWidth: number;

        public constructor(base: Maze.Maze, tileWidth: number) {
            this.base = base;
            this.tileWidth = tileWidth;
            this.pathColor = "";
            this.wallColor = "";
        }

        public setColors(pathColor: string, wallColor: string): void {
            this.pathColor = pathColor;
            this.wallColor = wallColor;
        }

        public draw(ctx: CanvasRenderingContext2D): void {
            for (let row: number = 0; row < this.base.rows; ++row) {
                for (let col: number = 0; col < this.base.columns; ++col) {
                    let path: boolean = this.base.pathAt(new Maze.Point(row, col));
                    let color: string = path ? this.pathColor : this.wallColor;
                    let x: number = col * this.tileWidth;
                    let y: number = row * this.tileWidth;

                    ctx.fillStyle = color;
                    ctx.fillRect(x, y, this.tileWidth, this.tileWidth);
                }
            }
        }
    }

    export class GEntity implements Drawable {
        public base: Entity.Entity;

        public constructor(base: Entity.Entity, imagePath: string, width: number) {
            this.base = base;
            this.image = new Image();
            this.image.src = imagePath;
            this.width = width;
        }

        public draw(ctx: CanvasRenderingContext2D): void {
            if (this.image.complete) {
                let x: number = this.base.location.column * this.width;
                let y: number = this.base.location.row * this.width;
                ctx.drawImage(this.image, x, y, this.width, this.width);
            } else {
                this.image.onload = () => this.draw(ctx);
            }
        }

        private image: HTMLImageElement;
        private width: number;
    }
}
