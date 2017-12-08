namespace Program {
    export class Program {
        constructor(
            public state: State.State,
            public readonly pacmanImage: string,
            public readonly enemyImages: string[],
            public readonly pathColor: string,
            public readonly wallColor: string,
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

            this.interval = setInterval(() => {
                this.draw();
                this.state.advance();
            }, this.updateRate);

            window.addEventListener("keydown", e => this.onKeydown(e));
            window.addEventListener("keyup", e => this.onKeyup(e));
        }

        stop(): void {
            clearInterval(this.interval);
        }

        onKeydown(e: KeyboardEvent): void {
            switch (e.keyCode) {
            case 37:
                this.state.pacmanDirection = Entity.Direction.Left;
                break;
            case 38:
                this.state.pacmanDirection = Entity.Direction.Up;
                break;
            case 39:
                this.state.pacmanDirection = Entity.Direction.Right;
                break;
            case 40:
                this.state.pacmanDirection = Entity.Direction.Down;
                break;
            }
        }

        onKeyup(e: KeyboardEvent): void {
            switch (e.keyCode) {
            case 37:
                if (this.state.pacmanDirection == Entity.Direction.Left) {
                    this.state.pacmanDirection = null;
                }
                break;
            case 38:
                if (this.state.pacmanDirection == Entity.Direction.Up) {
                    this.state.pacmanDirection = null;
                }
                break;
            case 39:
                if (this.state.pacmanDirection == Entity.Direction.Right) {
                    this.state.pacmanDirection = null;
                }
                break;
            case 40:
                if (this.state.pacmanDirection == Entity.Direction.Down) {
                    this.state.pacmanDirection = null;
                }
                break;
            }
        }

        private ctx: CanvasRenderingContext2D;
        private drawers: Graphics.Drawer[];
        private interval: number;

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
                    this.state.maze, this.pathColor, this.wallColor, this.tileWidth
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

    export class Builder {
        build(): Program {
            return new Program(
                this.state,
                this.pacmanImage,
                this.enemyImages,
                this.pathColor,
                this.wallColor,
                this.tileWidth,
                this.updateRate,
                this.canvasId
            );
        }

        setState(state: State.State) {
            this.state = state;
            return this;
        }

        setPacmanImage(pacmanImage: string) {
            this.pacmanImage = pacmanImage;
            return this;
        }

        setEnemyImages(enemyImages: string[]) {
            this.enemyImages = enemyImages;
            return this;
        }

        setPathColor(pathColor: string) {
            this.pathColor = pathColor;
            return this;
        }

        setWallColor(wallColor: string) {
            this.wallColor = wallColor;
            return this;
        }

        setTileWidth(tileWidth: number) {
            this.tileWidth = tileWidth;
            return this;
        }

        setUpdateRate(updateRate: number) {
            this.updateRate = updateRate;
            return this;
        }

        setCanvasId(canvasId: string) {
            this.canvasId = canvasId;
            return this;
        }

        private state: State.State;
        private pacmanImage: string;
        private enemyImages: string[];
        private pathColor: string;
        private wallColor: string;
        private tileWidth: number;
        private updateRate: number;
        private canvasId: string;
    }
}

let maze = new Maze.Maze(29, 49);
let state = new State.State(maze, new Entity.DefaultFactory(), 2, 2);
let program = new Program.Builder()
    .setState(state)
    .setPacmanImage("img/pacman.gif")
    .setEnemyImages(["img/blueghost.gif", "img/redghost.gif", "img/purpleghost.gif"])
    .setPathColor("black")
    .setWallColor("dimgray")
    .setTileWidth(20)
    .setUpdateRate(75)
    .setCanvasId("game-canvas")
    .build();

program.start();
