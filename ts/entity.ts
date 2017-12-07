namespace Entity {
    export enum Direction {
        Up,
        Down,
        Left,
        Right
    }

    export class Entity {
        constructor(public location: Maze.Point) {}

        collidesWith(other: Entity): boolean {
            return this.location.equals(other.location);
        }
    }

    export class Pacman extends Entity {
        alive: boolean;

        constructor(location: Maze.Point) {
            super(location);
            this.alive = true;
        }

        move(pathAt: (point: Maze.Point) => boolean, direction: Direction): void {
            if (direction == null) {
                return;
            }

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
        constructor(
            readonly point: Maze.Point,
            readonly gScore: number,
            readonly hScore: number,
            readonly parent?: PointNode
        ) {}

        fScore(): number {
            return this.gScore + this.hScore;
        }
    }

    function manhattanDistance(p1: Maze.Point, p2: Maze.Point): number {
            return Math.abs(p1.row - p2.row) + Math.abs(p1.column - p2.column);
    }

    export abstract class Enemy extends Entity {
        abstract move(
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
        move(pathAt: (point: Maze.Point) => boolean, target: Pacman): void {
            let paths: Maze.Point[] = Enemy.pathsFrom(pathAt, this.location);
            this.location = paths[Math.floor(Math.random() * paths.length)];
        }
    }

    abstract class AdvancedEnemy extends Enemy {
        move(pathAt: (point: Maze.Point) => boolean, target: Pacman): void {
            if (this.location.equals(target.location)) {
                return;
            }

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

                openSet = openSet.filter(p => p != node);

                if (node.point.equals(goal)) {
                    return node;
                }

                node.point.adjacents().forEach(p => {
                    if (pathAt(p) && !closedSet.some(visited => visited.equals(p))) {
                        openSet.push(this.heuristic(p, goal, node));
                    }
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
        make(points: Maze.Point[]): Enemy[];
    }

    export class DefaultFactory implements EnemyFactory {
        constructor() {}

        make(points: Maze.Point[]): Enemy[] {
            let enemies: Enemy[] = []

            for (let i: number = 0; i < points.length; ++i) {
                switch (i % 2) {
                case 0:
                    enemies.push(new RandomEnemy(points[i]));
                    break;
                case 1:
                    enemies.push(new GreedyEnemy(points[i]));
                    break;
                }
            }

            return enemies;
        }
    }
}
