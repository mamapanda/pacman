class Point {
    public x: number;
    public y: number;

    public constructor(x: number, y: number) {
        this.x = x;
        this.y = y;
    }
}

class Maze {
    public readonly width: number;
    public readonly height: number;

    public constructor(width: number, height: number) {
        this.width = width;
        this.height = height;
    }

    public contains(p: Point): boolean {
        return p.x >= 0 && p.x < this.width && p.y >= 0 && p.y < this.height;
    }

    public pathAt(p: Point): boolean {
        return this.contains(p) && this.tiles[p.y][p.x];
    }

    public generatePaths(): void {
        let start: Point = new Point(0, 0);
        let stack: Point[] = [start];

        this.resetTiles();
        this.tiles[start.y][start.x] = true;

        while (stack.length > 0) {
            let current: Point = stack[stack.length - 1];
            let nexts: Point[] = this.unbuiltPaths(current);

            if (nexts.length > 0) {
                let i: number = Math.floor(Math.random() * nexts.length);
                let next: Point = nexts[i];
                this.makePath(current, next);
                stack.push(next);
            } else {
                stack.pop();
            }
        }
    }

    private tiles: boolean[][];

    private resetTiles(): void {
        this.tiles = []
        for (let row: number = 0; row < this.height; ++row) {
            this.tiles[row] = [];
            for (let col: number = 0; col < this.width; ++col) {
                this.tiles[row][col] = false;
            }
        }
    }

    private unbuiltPaths(p: Point): Point[] {
        let paths: Point[] = [
            new Point(p.x - 2, p.y),
            new Point(p.x + 2, p.y),
            new Point(p.x, p.y - 2),
            new Point(p.x, p.y + 2)
        ];
        return paths.filter(point =>
            this.contains(point) && !this.pathAt(point));
    }

    private makePath(start: Point, end: Point): void {
        let wall: Point = new Point(
            Math.floor((start.x + end.x) / 2),
            Math.floor((start.y + end.y) / 2)
        );

        this.tiles[start.y][start.x] = true;
        this.tiles[wall.y][wall.x] = true;
        this.tiles[end.y][end.x] = true;
    }
}
