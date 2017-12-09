namespace Base {
    export enum Direction {
        Up,
        Down,
        Left,
        Right
    }

    export class Entity {
        constructor(public location: Point) {}

        collidesWith(other: Entity): boolean {
            return this.location.equals(other.location);
        }
    }

    export class Pacman extends Entity {
        alive: boolean;

        constructor(location: Point) {
            super(location);
            this.alive = true;
        }

        move(pathAt: (point: Point) => boolean, direction: Direction): void {
            if (direction == null) {
                return;
            }

            let newLocation: Point = this.location.copy();

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
            readonly point: Point,
            readonly gScore: number,
            readonly hScore: number,
            readonly parent?: PointNode
        ) {}

        fScore(): number {
            return this.gScore + this.hScore;
        }
    }

    export abstract class Enemy extends Entity {
        abstract move(
            pathAt: (point: Point) => boolean,
            target: Pacman
        ): void;

        protected static pathsFrom(
            pathAt: (point: Point) => boolean,
            point: Point
        ): Point[] {
            return point.adjacents().filter(p => pathAt(p));
        }
    }

    export class RandomEnemy extends Enemy {
        move(pathAt: (point: Point) => boolean, target: Pacman): void {
            let paths: Point[] = Enemy.pathsFrom(pathAt, this.location);
            this.location = paths[Math.floor(Math.random() * paths.length)];
        }
    }

    abstract class AdvancedEnemy extends Enemy {
        move(pathAt: (point: Point) => boolean, target: Pacman): void {
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
            pathAt: (point: Point) => boolean,
            goal: Point
        ): PointNode {
            let start: PointNode = new PointNode(
                this.location, 0, manhattanDistance(this.location, goal)
            );
            let openSet: PointNode[] = [start];
            let closedSet: Point[] = [];

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
            point: Point,
            goal: Point,
            parent?: PointNode
        ): PointNode;
    }

    export class GreedyEnemy extends AdvancedEnemy {
        protected heuristic(
            point: Point,
            goal: Point,
            parent?: PointNode
        ): PointNode {
            let gScore: number = 0;
            let hScore: number = manhattanDistance(point, goal);

            return new PointNode(point, gScore, hScore, parent);
        }
    }

    export class AStarEnemy extends AdvancedEnemy {
        protected heuristic(
            point: Point,
            goal: Point,
            parent?: PointNode
        ): PointNode {
            let gScore: number = parent == null ? 0 : parent.gScore + 1;
            let hScore: number = manhattanDistance(point, goal);

            return new PointNode(point, gScore, hScore, parent);
        }
    }

    type EnemyCtor = new (location: Point) => Enemy;

    export abstract class EnemyFactory {
        protected constructor(protected ctors: EnemyCtor[]) {
            this.nMade = 0;
        }

        make(point: Point): Enemy {
            let ctor: EnemyCtor =  this.ctors[this.nMade % this.ctors.length];

            ++this.nMade;
            return new ctor(point);
        }

        protected nMade: number;
    }

    export class DefaultFactory extends EnemyFactory {
        constructor() {
            super([RandomEnemy, GreedyEnemy, AStarEnemy]);
        }
    }
}
