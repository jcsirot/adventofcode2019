package org.chelonix.aoc2019.day10;

import com.google.common.io.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {

    private static class Asteroid {
        private int x,y;

        public Asteroid(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int distance(Asteroid other) {
            return (int) (Math.abs(Math.pow(x - other.x, 2)) + Math.abs(Math.pow(y - other.y, 2)));
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Asteroid asteroid = (Asteroid) o;
            return x == asteroid.x &&
                    y == asteroid.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return String.format("(%d;%d)",x,y);
        }
    }

    public static Set<Asteroid> input(String res) throws IOException {
        Set<Asteroid> set = new HashSet<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(Resources.getResource(res).openStream()));
        int y = 0;
        for(String line = br.readLine(); line != null; y++, line = br.readLine()) {
            String[] m = line.split("");
            for(int x = 0; x < m.length; x++) {
                if ("#".equals(m[x])) {
                    set.add(new Asteroid(x, y));
                }
            }
        }
        return set;
    }

    public static String findBest(String path) throws IOException {
        Set<Asteroid> map = input(path);
        Map<Asteroid, Integer> counts = new HashMap<>();
        map.forEach(o -> {
            Map<Angle, Asteroid> lineOfSight = new HashMap<>();
            map.forEach(a -> {
                if (a == o) {
                    return;
                }
                Angle angle = new Angle(a.x - o.x, a.y - o.y);
                lineOfSight.putIfAbsent(angle, a);
            });
            counts.put(o, lineOfSight.size());
        });
        Map.Entry<Asteroid, Integer> entry = counts.entrySet().stream().max(Comparator.comparingInt(e -> e.getValue())).get();
        return String.format("%s %d", entry.getKey(), entry.getValue());
    }

    public static int vaporize(String path, int xm, int ym) throws IOException {
        Asteroid laser = new Asteroid(xm, ym);
        List<Asteroid> deleted = new ArrayList<>();
        Set<Asteroid> map = input(path);
        Map<Double, List<Asteroid>> asteroids = new HashMap<>();
        map.forEach(a -> {
            Double angle = new Angle(a.x - xm, a.y - ym).value();
            List<Asteroid> as = asteroids.getOrDefault(angle, new ArrayList<>());
            as.add(a);
            Collections.sort(as, Comparator.comparingInt(o -> o.distance(laser)));
            asteroids.put(angle, as);
        });
        while (! asteroids.isEmpty()) {
            List<Double> targetsAngles = asteroids.keySet().stream().sorted().collect(Collectors.toList());
            for (Double angle : targetsAngles) {
                List<Asteroid> targets = asteroids.get(angle);
                deleted.add(targets.remove(0));
                if (targets.isEmpty()) {
                    asteroids.remove(angle);
                }
            }
        }
        return deleted.get(199).x*100 + deleted.get(199).y;
    }

    private static class Angle {
        private int dx, dy;

        public Angle(int dx, int dy) {
            if (dx == 0) {
                this.dx = 0;
                this.dy = dy < 0 ? -1 : 1;
            } else if (dy == 0) {
                this.dx = dx < 0 ? -1 : 1;
                this.dy = 0;
            } else {
                int gcd = gcd(dx, dy);
                this.dx = dx / Math.abs(gcd);
                this.dy = dy / Math.abs(gcd);
            }
        }

        /**
         * Returns a double between 0 and 1
         */
        double value() {
            return (Math.PI-Math.atan2(dx, dy))/(2*Math.PI);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Angle angle = (Angle) o;
            return dx == angle.dx && dy == angle.dy;
        }

        @Override
        public int hashCode() {
            return Objects.hash(dx, dy);
        }

        public static int gcd(int a, int b) {
            if (b == 0) {
                return a;
            }
            return gcd(b, a % b);
        }
    }
}
