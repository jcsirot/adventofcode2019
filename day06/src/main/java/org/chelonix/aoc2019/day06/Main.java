package org.chelonix.aoc2019.day06;

import com.google.common.io.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    private static class Orbit {
        private String mass, sat;

        public Orbit(String mass, String sat) {
            this.mass = mass;
            this.sat = sat;
        }

        public String getMass() {
            return mass;
        }

        public String getSat() {
            return sat;
        }
    }

    public static List<Orbit> input(String res) throws IOException {
        return new BufferedReader(new InputStreamReader(Resources.getResource(res).openStream()))
                .lines()
                .map(s -> s.split("\\)"))
                .map(a -> new Orbit(a[0], a[1]))
                .collect(Collectors.toList());
    }

    private static class OrbitalObject {
        private String name;
        private List<OrbitalObject> satellites = new ArrayList<>();
        private OrbitalObject mass;

        public OrbitalObject(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void addSatellite(OrbitalObject sat) {
            sat.mass = this;
            satellites.add(sat);
        }

        public int countOrbits(int depth) {
            int count = depth;
            for (OrbitalObject sat: satellites) {
                count += sat.countOrbits(depth + 1);
            }
            return count;
        }

        public boolean isSatellite(OrbitalObject sat) {
            for (OrbitalObject oo: satellites) {
                if (sat.name.equals(oo.getName()) || oo.isSatellite(sat)) {
                    return true;
                }
            }
            return false;
        }

        public int depth() {
            return "COM".equals(name) ? 0 : 1+mass.depth();
        }

        public OrbitalObject lowestCommonAncestor(OrbitalObject sat) {
            if (isSatellite(sat)) {
                return this;
            } else {
                return mass.lowestCommonAncestor(sat);
            }
        }

        public OrbitalObject find(String sat) {
            if (sat.equals(name)) {
                return this;
            }
            for (OrbitalObject oo: satellites) {
                OrbitalObject found = oo.find(sat);
                if (found != null) {
                    return found;
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public static int countTransfers(String path) throws IOException {
        List<Orbit> orbits = input(path);
        OrbitalObject centerOfMass = new OrbitalObject("COM");
        centerOfMass = buildMap(centerOfMass, orbits);
        OrbitalObject you = centerOfMass.find("YOU");
        OrbitalObject san = centerOfMass.find("SAN");
        OrbitalObject lca = you.lowestCommonAncestor(san);
        return you.depth()+san.depth()-2*lca.depth()-2;
    }

    public static int countOrbits(String path) throws IOException {
        List<Orbit> orbits = input(path);
        OrbitalObject centerOfMass = new OrbitalObject("COM");
        centerOfMass = buildMap(centerOfMass, orbits);
        return centerOfMass.countOrbits(0);
    }

    private static OrbitalObject buildMap(OrbitalObject obj, List<Orbit> orbits) {
        List<String> sats = orbits.stream().filter(o -> o.getMass().equals(obj.getName())).map(Orbit::getSat).collect(Collectors.toList());
        for (String sat: sats) {
            obj.addSatellite(buildMap(new OrbitalObject(sat), orbits));
        }
        return obj;
    }
}
