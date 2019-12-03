package org.chelonix.aoc2019.day03;

import com.google.common.io.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {

    private static Tile O = new Tile(0, 0, 0);

    static class Tile {
        private int x,y,step;

        public Tile(int x, int y, int step) {
            this.x = x;
            this.y = y;
            this.step = step;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getStep() {
            return step;
        }

        public int distance(Tile other) {
            return Math.abs(other.x - x) + Math.abs(other.y - y);
        }

        public Tile up() {
            return new Tile(x, y+1, step+1);
        }
        public Tile down() {
            return new Tile(x, y-1, step+1);
        }
        public Tile left() {
            return new Tile(x-1, y, step+1);
        }
        public Tile right() {
            return new Tile(x+1, y, step+1);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Tile tile = (Tile) o;
            return x == tile.x &&
                    y == tile.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "Tile{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    private static List<List<String>> input(String res) throws IOException {
        return new BufferedReader(new InputStreamReader(Resources.getResource(res).openStream()))
                .lines().map(s -> s.split(",")).map(a -> Arrays.asList(a)).collect(Collectors.toList());
    }

    public static int cross(String path) throws IOException {
        List<List<String>> wires = input(path);
        Set<Tile> tiles0 = computeTiles(wires.get(0));
        Set<Tile> tiles1 = computeTiles(wires.get(1));
        tiles0.retainAll(tiles1);
        System.out.println(tiles0);
        int minDistance = Integer.MAX_VALUE;
        for (Tile tile: tiles0) {
            int d = tile.distance(O);
            if (d < minDistance) {
                minDistance = d;
            }
        }
        return minDistance;
    }

    public static int crossShortPath(String path) throws IOException {
        List<List<String>> wires = input(path);
        Set<Tile> tiles0 = computeTiles(wires.get(0));
        Set<Tile> tiles1 = computeTiles(wires.get(1));
        int minDistance = Integer.MAX_VALUE;
        for (Tile tile: tiles0) {
            if (tiles1.contains(tile)) {
                Tile otherTile = tiles1.stream().filter(t -> t.equals(tile)).findFirst().get();
                int d = tile.getStep() + otherTile.getStep();
                if (d < minDistance) {
                    minDistance = d;
                }
            }
        }
        return minDistance;
    }

    static Set<Tile> computeTiles(List<String> path) {
        Tile current = O;
        Set<Tile> wire = new HashSet<>();
        for (String move: path) {
            String orientation = move.substring(0, 1);
            int len = Integer.parseInt(move.substring(1));
            switch (orientation) {
                case "U":
                    for (int i = 0; i < len; i++) {
                        current = current.up();
                        wire.add(current);
                    }
                    break;
                case "D":
                    for (int i = 0; i < len; i++) {
                        current = current.down();
                        wire.add(current);
                    }
                    break;
                case "L":
                    for (int i = 0; i < len; i++) {
                        current = current.left();
                        wire.add(current);
                    }
                    break;
                case "R":
                    for (int i = 0; i < len; i++) {
                        current = current.right();
                        wire.add(current);
                    }
                    break;
                default:
                    throw new UnsupportedOperationException();
            }
        }
        return wire;
    }
}
