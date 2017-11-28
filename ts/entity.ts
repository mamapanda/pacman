namespace Entity {
    export enum Direction {
        Up,
        Down,
        Left,
        Right
    }

    export class Entity {
        public location: Maze.Point;

        public constructor(location: Maze.Point) {
            this.location = location;
        }

        public collidesWith(other: Entity): boolean {
            return this.location.equals(other.location);
        }
    }

    export class Pacman extends Entity {
        public alive: boolean;

        public constructor(location: Maze.Point) {
            super(location);
            this.alive = true;
        }

        public move(
            pathAt: (point: Maze.Point) => boolean,
            direction: Direction
        ): void {
            let newLocation: Maze.Point = this.location.copy();

            switch (direction) {
            case Direction.Up:
                --newLocation.row;
                break;
            case Direction.Down:
                ++newLocation.row;
                break;
            case Direction.Left:
                --newLocation.column;
                break;
            case Direction.Right:
                ++newLocation.column;
                break;
            }

            if (pathAt(newLocation)) {
                this.location = newLocation;
            }
        }
    }

    class PointNode {
        public readonly point: Maze.Point;
        public readonly gScore: number;
        public readonly hScore: number;
        public readonly parent: PointNode;

        public constructor(
            point: Maze.Point,
            gScore: number,
            hScore: number,
            parent?: PointNode
        ) {
            this.point = point;
            this.gScore = gScore;
            this.hScore = hScore;
            this.parent = parent;
        }

        public fScore(): number {
            return this.gScore + this.hScore;
        }
    }

    function manhattanDistance(p1: Maze.Point, p2: Maze.Point): number {
            return Math.abs(p1.row - p2.row) + Math.abs(p1.column - p2.column);
    }

    export abstract class Enemy extends Entity {
        public abstract move(
            pathAt: (point: Maze.Point) => boolean,
            target: Pacman
        ): void;

        protected static pathsFrom(
            pathAt: (point: Maze.Point) => boolean,
            point: Maze.Point
        ): Maze.Point[] {
            return point.adjacents().filter(p => pathAt(p));
        }
    }

    export class RandomEnemy extends Enemy {
        public move(pathAt: (point: Maze.Point) => boolean, target: Pacman): void {
            let paths: Maze.Point[] = Enemy.pathsFrom(pathAt, this.location);
            this.location = paths[Math.floor(Math.random() * paths.length)];
        }
    }

    abstract class AdvancedEnemy extends Enemy {
        public move(pathAt: (point: Maze.Point) => boolean, target: Pacman): void {
            let goal: PointNode = this.searchPath(pathAt, target.location);

            while (goal.parent.parent != null) {
                goal = goal.parent;
            }

            this.location = goal.point;
        }

        protected searchPath(
            pathAt: (point: Maze.Point) => boolean,
            goal: Maze.Point
        ): PointNode {
            let start: PointNode = new PointNode(
                this.location, 0, manhattanDistance(this.location, goal)
            );
            let openSet: PointNode[] = [start];
            let closedSet: Maze.Point[] = [];

            while (openSet.length > 0) {
                let node: PointNode = openSet.reduce(
                    (p1, p2) => p1.fScore() < p2.fScore() ? p1 : p2
                );

                if (closedSet.some(p => node.point.equals(p))) {
                    continue;
                }

                if (node.point.equals(goal)) {
                    return node;
                }

                node.point.adjacents().filter(p => pathAt(p)).forEach(p => {
                    openSet.push(this.heuristic(p, goal, node));
                });

                closedSet.push(node.point);
            }

            return null;
        }

        protected abstract heuristic(
            point: Maze.Point,
            goal: Maze.Point,
            parent?: PointNode
        ): PointNode;
    }

    export class GreedyEnemy extends AdvancedEnemy {
        protected heuristic(
            point: Maze.Point,
            goal: Maze.Point,
            parent?: PointNode
        ): PointNode {
            let gScore: number = parent == null ? 0 : parent.gScore + 1;
            let hScore: number = manhattanDistance(point, goal);

            return new PointNode(point, gScore, hScore, parent);
        }
    }

    export interface EnemyFactory {
        make(n: number);
    }
}
