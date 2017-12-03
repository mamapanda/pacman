namespace Graphics {
    export interface Drawer {
        draw(ctx: CanvasRenderingContext2D): void;
    }

    export class MazeDrawer implements Drawer {
        constructor(
            public maze: Maze.Maze,
            public pathColor: string,
            public wallColor: string,
            public readonly tileWidth: number
        ) {}

        draw(ctx: CanvasRenderingContext2D): void {
            for (let row: number = 0; row < this.maze.rows; ++row) {
                for (let col: number = 0; col < this.maze.columns; ++col) {
                    let path: boolean = this.maze.pathAt(new Maze.Point(row, col));
                    let color: string = path ? this.pathColor : this.wallColor;
                    let x: number = col * this.tileWidth;
                    let y: number = row * this.tileWidth;

                    ctx.fillStyle = color;
                    ctx.fillRect(x, y, this.tileWidth, this.tileWidth);
                }
            }
        }
    }

    export class EntityDrawer implements Drawer {
        constructor(
            public entity: Entity.Entity,
            private width,
            imagePath: string
        ) {
            this.image = new Image();
            this.image.src = imagePath;
        }

        draw(ctx: CanvasRenderingContext2D): void {
            if (this.image.complete) {
                let x: number = this.entity.location.column * this.width;
                let y: number = this.entity.location.row * this.width;
                ctx.drawImage(this.image, x, y, this.width, this.width);
            } else {
                this.image.onload = () => this.draw(ctx);
            }
        }

        private image: HTMLImageElement;
    }
}
