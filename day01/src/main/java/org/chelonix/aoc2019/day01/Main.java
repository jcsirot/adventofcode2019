package org.chelonix.aoc2019.day01;

import com.google.common.io.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Stream;

public class Main {

    private static Stream<Long> input(String res) throws IOException {
        return new BufferedReader(new InputStreamReader(Resources.getResource(res).openStream()))
                .lines().map(Long::parseLong);
    }

    public static long computeFuelRequired(String path) throws IOException {
        return input(path).map(mass -> mass / 3).map(x -> x - 2).reduce(Long::sum).get();
    }

    public static long computeFuelRequiredRecursive(String path) throws IOException {
        return input(path).map( m -> {
            long x = (m / 3) - 2, s = 0;
            while (x > 0) {
                s += x;
                x = (x/3)-2;
            }
            return s;
        }).reduce(Long::sum).get();
    }
}
