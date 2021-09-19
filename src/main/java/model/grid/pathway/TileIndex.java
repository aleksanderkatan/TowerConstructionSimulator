package model.grid.pathway;

import model.mathematics.Point;

import java.util.Objects;

public class TileIndex {
        private final int x, y;
        public TileIndex(int x, int y) {
                this.x = x;
                this.y = y;
        }
        public int getX() { return x; }
        public int getY() { return y; }
        public Point getPoint() {
                return new Point(x+0.5, y+0.5);
        }

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                TileIndex tileIndex = (TileIndex) o;
                return x == tileIndex.x && y == tileIndex.y;
        }

        @Override
        public int hashCode() {
                return Objects.hash(x, y);
        }
}
