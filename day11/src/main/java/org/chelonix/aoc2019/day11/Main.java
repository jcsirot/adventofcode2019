package org.chelonix.aoc2019.day11;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.LongConsumer;
import java.util.function.LongSupplier;

public class Main {

    private static class Panel {
        int x, y;
        int color;

        public Panel(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Panel panel = (Panel) o;
            return x == panel.x &&
                    y == panel.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "(" + x + "," + y + ")";
        }
    }

    private static class Robot {

        private static enum Heading {
            UP, DOWN, LEFT, RIGHT;

            Heading left() {
                switch (this) {
                    case UP:
                        return LEFT;
                    case DOWN:
                        return RIGHT;
                    case LEFT:
                        return DOWN;
                    case RIGHT:
                        return UP;
                }
                throw new UnsupportedOperationException();
            }

            Heading right() {
                switch (this) {
                    case LEFT:
                        return UP;
                    case RIGHT:
                        return DOWN;
                    case DOWN:
                        return LEFT;
                    case UP:
                        return RIGHT;
                }
                throw new UnsupportedOperationException();
            }

            Panel move(Panel current) {
                switch (this) {
                    case UP:
                        return new Panel(current.x, current.y-1);
                    case DOWN:
                        return new Panel(current.x, current.y+1);
                    case LEFT:
                        return new Panel(current.x-1, current.y);
                    case RIGHT:
                        return new Panel(current.x+1, current.y);
                }
                throw new UnsupportedOperationException();
            }
        }

        private class InOut implements LongSupplier, LongConsumer {

            private boolean robotPainted = false;

            @Override
            public void accept(long value) {
                if (! robotPainted) {
                    current.color = (int)value;
                    visited.add(current);
                    //System.out.printf("%s paint in %s\n", current, value == 0 ? "BLACK" : "WHITE");
                } else {
                    //System.out.printf("%s turn to %s\n", current, value == 0 ? "LEFT" : "RIGHT");
                    heading = value == 0 ? heading.left() : heading.right();
                    current = find(heading.move(current));
                }
                robotPainted = !robotPainted;
            }

            @Override
            public long getAsLong() {
                return current.color;
            }
        }

        private IntCode code;
        private Heading heading = Heading.UP;
        private Panel current = new Panel(0, 0);
        private Set<Panel> visited = new HashSet<>();

        public Robot(IntCode code, int initialColor) {
            this.code = code;
            this.current.color = initialColor;
            InOut bridge = new InOut();
            code.setInput(bridge);
            code.setOutput(bridge);
            visited.add(current);
        }

        public void start() {
            code.execute();
        }

        Panel find(Panel panel) {
            return visited.stream()
                    .filter(p -> p.equals(panel))
                    .findFirst()
                    .orElse(new Panel(panel.x, panel.y));
        }

        public Set<Panel> getVisited() {
            return visited;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();

            int minX = visited.stream().min(Comparator.comparingInt(p -> p.x)).get().x;
            int maxX = visited.stream().max(Comparator.comparingInt(p -> p.x)).get().x;
            int minY = visited.stream().min(Comparator.comparingInt(p -> p.y)).get().y;
            int maxY = visited.stream().max(Comparator.comparingInt(p -> p.y)).get().y;
            for (int j = minY; j <= maxY; j++) {
                for (int i = minX; i <= maxX; i++) {
                    int x = i, y = j;
                    sb.append(visited.stream().filter(p -> p.x == x && p.y == y).map(p -> p.color == 0 ? "⬛" : "⬜").findFirst().orElse("⬛"));
                }
                sb.append("\n");
            }
            return sb.toString();
        }

    }

    public static int paint(String path) throws IOException {
        IntCode code = new IntCode(path);
        Robot robot = new Robot(code, 0);
        robot.start();
        return robot.getVisited().size();
    }

    public static String paintFromWhitePanel(String path) throws IOException {
        IntCode code = new IntCode(path);
        Robot robot = new Robot(code, 1);
        robot.start();
        return robot.toString();
    }

}
