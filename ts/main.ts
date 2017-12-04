namespace Program {
    export class Program {
        constructor(
            public state: State.State,
            public readonly pacmanImage: string,
            public readonly enemyImages: string[],
            public readonly tileWidth: number,
            public readonly updateRate: number,
            canvasId: string
        ) {
            this.initCanvas(canvasId);
        }

        draw(): void {
            this.drawers.forEach(d => d.draw(this.ctx));
        }

        start(): void {
            this.state.init();
            this.initDrawers();

            setInterval(() => {
                this.draw();
                this.state.advance(Entity.Direction.Left);
            }, this.updateRate);
        }

        private ctx: CanvasRenderingContext2D;
        private drawers: Graphics.Drawer[];

        private initCanvas(canvasId: string): void {
            let canvas: HTMLCanvasElement =
                document.getElementById(canvasId) as HTMLCanvasElement;

            canvas.width = this.state.maze.columns * this.tileWidth;
            canvas.height = this.state.maze.rows * this.tileWidth;

            this.ctx = canvas.getContext("2d");
        }

        private initDrawers(): void {
            this.drawers = []

            this.drawers.push(
                new Graphics.MazeDrawer(
                    this.state.maze, "black", "dimgray", this.tileWidth
                )
            );

            this.drawers.push(
                new Graphics.EntityDrawer(
                    this.state.pacman, this.tileWidth, this.pacmanImage
                )
            );

            for (let i: number = 0; i < this.state.enemies.length; ++i) {
                let index: number = i % this.enemyImages.length;
                let image: string = this.enemyImages[index];

                this.drawers.push(
                    new Graphics.EntityDrawer(
                        this.state.enemies[i], this.tileWidth, image
                    )
                );
            }
        }
    }
}

let program: Program.Program = new Program.Program(
    new State.State(
        new Maze.Maze(29, 49),
        new Entity.DefaultFactory(),
        2,
        2
    ),
    "img/pacman.gif",
    ["img/blueghost.gif", "img/redghost.gif", "img/purpleghost.gif"],
    20,
    1000,
    "game-canvas",
);

program.start();
