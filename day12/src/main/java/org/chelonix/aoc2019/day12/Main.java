package org.chelonix.aoc2019.day12;

import com.google.common.io.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static final Pattern COORDINATES = Pattern.compile("^<x=(?<x>-?[\\d]+), y=(?<y>-?[\\d]+), z=(?<z>-?[\\d]+)>$");

    private static class Moon {
        private int x,y,z;
        private int vx,vy,vz;

        public Moon(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public void move() {
            x += vx;
            y += vy;
            z += vz;
        }

        public int energy() {
            return (Math.abs(x)+Math.abs(y)+Math.abs(z)) * (Math.abs(vx)+Math.abs(vy)+Math.abs(vz));
        }

        @Override
        public String toString() {
            return "pos=<" +
                    "x=" + x +
                    ", y=" + y +
                    ", z=" + z +
                    ">, vel=<x=" + vx +
                    ", y=" + vy +
                    ", z=" + vz +
                    '>';
        }
    }

    public static List<Moon> input(String res) throws IOException {
        List<Moon> moons = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(
                Resources.getResource(res).openStream()));
        String line;
        while ((line = br.readLine()) != null) {
            Matcher m = COORDINATES.matcher(line);
            m.matches();
            moons.add(new Moon(Integer.parseInt(m.group("x")),
                    Integer.parseInt(m.group("y")),
                    Integer.parseInt(m.group("z"))));
        }
        return moons;
    }

    public static int simulate(String path, int iterCount) throws IOException {
        List<Moon> moons = input(path);
        for (int iter = 1; iter <= iterCount; iter++) {
            for (int i = 0; i < moons.size() - 1; i++) {
                for (int j = i; j < moons.size(); j++) {
                    applyGravity(moons.get(i), moons.get(j));
                }
            }
            moons.forEach(Moon::move);
        }
        return moons.stream().mapToInt(Moon::energy).sum();
    }

    public static long findCycle(String path) throws IOException {
        List<Moon> init = input(path);
        long cycleX = 0, cycleY = 0, cycleZ = 0;
        List<Moon> moons = input(path);
        while (true) {
            step(moons);
            cycleX++;
            boolean cycleFound = true;
            for (int i =0; i < moons.size(); i++) {
                cycleFound &= moons.get(i).x == init.get(i).x && moons.get(i).vx == init.get(i).vx;
            }
            if (cycleFound) {
                break;
            }
        }
        moons = input(path);
        while (true) {
            step(moons);
            cycleY++;
            boolean cycleFound = true;
            for (int i =0; i < moons.size(); i++) {
                cycleFound &= moons.get(i).y == init.get(i).y && moons.get(i).vy == init.get(i).vy;
            }
            if (cycleFound) {
                break;
            }
        }
        moons = input(path);
        while (true) {
            step(moons);
            cycleZ++;
            boolean cycleFound = true;
            for (int i =0; i < moons.size(); i++) {
                cycleFound &= moons.get(i).z == init.get(i).z && moons.get(i).vz == init.get(i).vz;
            }
            if (cycleFound) {
                break;
            }
        }
        return lcm(lcm(cycleX, cycleY), cycleZ);
    }

    private static void step(List<Moon> moons) {
        for (int i = 0; i < moons.size() - 1; i++) {
            for (int j = i; j < moons.size(); j++) {
                applyGravity(moons.get(i), moons.get(j));
            }
        }
        moons.forEach(Moon::move);
    }

    private static void applyGravity(Moon m1, Moon m2) {
        if (m1.x < m2.x) {
            m1.vx++;
            m2.vx--;
        } else if (m1.x > m2.x) {
            m1.vx--;
            m2.vx++;
        }

        if (m1.y < m2.y) {
            m1.vy++;
            m2.vy--;
        } else if (m1.y > m2.y) {
            m1.vy--;
            m2.vy++;
        }

        if (m1.z < m2.z) {
            m1.vz++;
            m2.vz--;
        } else if (m1.z > m2.z) {
            m1.vz--;
            m2.vz++;
        }
    }

    private static long lcm(long a, long b) {
        return (a*b)/gcd(a,b);
    }

    public static long gcd(long a, long b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }
}
