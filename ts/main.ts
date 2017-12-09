namespace Program {
    export class Program {
        constructor(
            public state: Base.State,
            readonly pacmanImage: string,
            readonly enemyImages: string[],
            readonly pathColor: string,
            readonly wallColor: string,
            readonly tileWidth: number,
            readonly updateRate: number,
            canvasId: string,
            scoreId: string
        ) {
            this.initCanvas(canvasId);
            this.initDrawers();
            this.scoreElement =
                document.getElementById(scoreId) as HTMLSpanElement;
        }

        draw(): void {
            this.drawers.forEach(d => d.draw(this.ctx));
        }

        score(): number {
            return this.state.iteration;
        }

        advance(): void {
            if (this.state.advance()) {
                this.addEnemyDrawer(
                    this.state.enemies[this.state.enemies.length - 1]
                );
            }

            this.draw();
            this.scoreElement.innerHTML = this.score().toString();
        }

        onKeydown(e: KeyboardEvent): void {
            switch (e.keyCode) {
            case 37:
                this.state.pacmanDirection = Base.Direction.Left;
                break;
            case 38:
                this.state.pacmanDirection = Base.Direction.Up;
                break;
            case 39:
                this.state.pacmanDirection = Base.Direction.Right;
                break;
            case 40:
                this.state.pacmanDirection = Base.Direction.Down;
                break;
            }
        }

        onKeyup(e: KeyboardEvent): void {
            switch (e.keyCode) {
            case 37:
                if (this.state.pacmanDirection == Base.Direction.Left) {
                    this.state.pacmanDirection = null;
                }
                break;
            case 38:
                if (this.state.pacmanDirection == Base.Direction.Up) {
                    this.state.pacmanDirection = null;
                }
                break;
            case 39:
                if (this.state.pacmanDirection == Base.Direction.Right) {
                    this.state.pacmanDirection = null;
                }
                break;
            case 40:
                if (this.state.pacmanDirection == Base.Direction.Down) {
                    this.state.pacmanDirection = null;
                }
                break;
            }
        }

        start(): void {
            this.draw();

            let interval: number = setInterval(() => {
                this.advance();

                if (!this.state.pacman.alive) {
                    clearInterval(interval);
                    alert(`You died!\n\nFinal Score: ${this.score()}`);
                }
            }, this.updateRate);

            window.addEventListener("keydown", e => this.onKeydown(e));
            window.addEventListener("keyup", e => this.onKeyup(e));
        }

        private ctx: CanvasRenderingContext2D;
        private scoreElement: HTMLSpanElement;
        private drawers: Graphics.Drawer[];

        private initCanvas(canvasId: string): void {
            let canvas: HTMLCanvasElement =
                document.getElementById(canvasId) as HTMLCanvasElement;

            canvas.width = this.state.maze.columns * this.tileWidth;
            canvas.height = this.state.maze.rows * this.tileWidth;

            this.ctx = canvas.getContext("2d");
        }

        private addEnemyDrawer(e: Base.Enemy): void {
            let i: number = Math.floor(Math.random() * this.enemyImages.length);
            let image: string = this.enemyImages[i];

            this.drawers.push(new Graphics.EntityDrawer(
                e, this.tileWidth, image
            ));
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

            this.state.enemies.forEach(e => this.addEnemyDrawer(e));
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
                this.canvasId,
                this.scoreId
            );
        }

        setState(state: Base.State): Builder {
            this.state = state;
            return this;
        }

        setPacmanImage(pacmanImage: string): Builder {
            this.pacmanImage = pacmanImage;
            return this;
        }

        setEnemyImages(enemyImages: string[]): Builder {
            this.enemyImages = enemyImages;
            return this;
        }

        setPathColor(pathColor: string): Builder {
            this.pathColor = pathColor;
            return this;
        }

        setWallColor(wallColor: string): Builder {
            this.wallColor = wallColor;
            return this;
        }

        setTileWidth(tileWidth: number): Builder {
            this.tileWidth = tileWidth;
            return this;
        }

        setUpdateRate(updateRate: number): Builder {
            this.updateRate = updateRate;
            return this;
        }

        setCanvasId(canvasId: string): Builder {
            this.canvasId = canvasId;
            return this;
        }

        setScoreId(scoreId: string): Builder {
            this.scoreId = scoreId;
            return this;
        }

        private state: Base.State;
        private pacmanImage: string;
        private enemyImages: string[];
        private pathColor: string;
        private wallColor: string;
        private tileWidth: number;
        private updateRate: number;
        private canvasId: string;
        private scoreId: string;
    }
}

window.onload = () => {
    let maze = new Base.Maze(29, 49);
    let state = new Base.State(maze, new Base.DefaultFactory(), 2, 70, 3);
    let program = new Program.Builder()
        .setState(state)
        .setPacmanImage("img/pacman.gif")
        .setEnemyImages([
            "img/blueghost.gif",
            "img/redghost.gif",
            "img/purpleghost.gif"
        ]).setPathColor("black")
        .setWallColor("dimgray")
        .setTileWidth(20)
        .setUpdateRate(75)
        .setCanvasId("game-canvas")
        .setScoreId("score")
        .build();

    program.start();
}
