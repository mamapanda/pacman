namespace Maze {
    export class Point {
        constructor(public row: number, public column: number) {}

        adjacents(): Point[] {
            return [
                new Point(this.row - 1, this.column),
                new Point(this.row + 1, this.column),
                new Point(this.row, this.column - 1),
                new Point(this.row, this.column + 1)
            ];
        }

        copy(): Point {
            return new Point(this.row, this.column);
        }

        equals(other: Point): boolean {
            return this.row == other.row && this.column == other.column;
        }
    }

    export class Maze {
        constructor(public rows: number, public columns: number) {}

        contains(p: Point): boolean {
            return p.row >= 0 && p.row < this.rows &&
                p.column >= 0 && p.column < this.columns;
        }

        pathAt(p: Point): boolean {
            return this.contains(p) && this.tiles[p.row][p.column];
        }

        generate(): void {
            this.resetTiles();
            this.generatePaths();
            this.patchDeadEnds();
        }

        private tiles: boolean[][];

        private resetTiles(): void {
            this.tiles = []
            for (let row: number = 0; row < this.rows; ++row) {
                this.tiles[row] = [];
                for (let col: number = 0; col < this.columns; ++col) {
                    this.tiles[row][col] = false;
                }
            }
        }

        private unbuiltPaths(p: Point): Point[] {
            let paths: Point[] = [
                new Point(p.row - 2, p.column),
                new Point(p.row + 2, p.column),
                new Point(p.row, p.column - 2),
                new Point(p.row, p.column + 2)
            ];
            return paths.filter(point =>
                                this.contains(point) && !this.pathAt(point));
        }

        private makePath(start: Point, end: Point): void {
            let wall: Point = new Point(
                Math.floor((start.row + end.row) / 2),
                Math.floor((start.column + end.column) / 2)
            );

            this.tiles[start.row][start.column] = true;
            this.tiles[wall.row][wall.column] = true;
            this.tiles[end.row][end.column] = true;
        }

        private generatePaths(): void {
            let start: Point = new Point(0, 0);
            let stack: Point[] = [start];

            this.tiles[start.row][start.column] = true;

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

        private pathDistance(start: Point, end: Point): number {
            if (!this.pathAt(start) || !(this.pathAt(end))) {
                return -1;
            }

            let queue: [Point, number][] = [[start, 0]];
            let visited: Point[] = [];

            while (queue.length > 0) {
                let point: Point, distance: number;
                [point, distance] = queue.shift();

                if (point.equals(end)) {
                    return distance;
                }

                for (let neighbor of point.adjacents().filter(p => this.pathAt(p))) {
                    if (!visited.some(p => neighbor.equals(p))) {
                        queue.push([neighbor, distance + 1]);
                    }
                }

                visited.push(point);
            }
        }

        private farthestPoint(start: Point, points: Point[]): Point {
            let distances: [Point, number][] = points.map(
                p => [p, this.pathDistance(start, p)] as [Point, number]);
            return distances.sort((lhs, rhs) => rhs[1] - lhs[1])[0][0];
        }

        private deadEndAt(point: Point): boolean {
            let adjacentPaths: Point[] = point.adjacents()
                .filter(p => this.pathAt(p));
            return adjacentPaths.length == 1;
        }

        private patchDeadEnds(): void {
            for (let row: number = 0; row < this.rows; row += 2) {
                for (let col: number = 0; col < this.columns; col += 2) {
                    let point: Point = new Point(row, col);

                    if (this.deadEndAt(point)) {
                        let candidates: Point[] = [
                            new Point(point.row - 2, point.column),
                            new Point(point.row + 2, point.column),
                            new Point(point.row, point.column - 2),
                            new Point(point.row, point.column + 2)
                        ].filter(p => this.pathAt(p));

                        this.makePath(point, this.farthestPoint(point, candidates));
                    }
                }
            }
        }
    }
}
