namespace Entity {
    export enum Direction {
        Up,
        Down,
        Left,
        Right
    }

    export class Entity {
        public constructor(location: Maze.Point) {
            this.location = location;
        }

        public collidesWith(other: Entity): boolean {
            return this.location.equals(other.location);
        }

        protected location: Maze.Point;
    }

    export class Pacman extends Entity {
        public alive: boolean;

        public constructor(location: Maze.Point) {
            super(location);
            this.alive = true;
        }

        public move(pathAt: (Point) => boolean, direction: Direction): void {
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

    export abstract class Enemy extends Entity {
        public abstract move(pathAt: (Point) => boolean): void;
    }
}
