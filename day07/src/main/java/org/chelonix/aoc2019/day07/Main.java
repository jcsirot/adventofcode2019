package org.chelonix.aoc2019.day07;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {

    public static List<List<Integer>> permutations(Set<Integer> values) {
        List<List<Integer>> permutations = new ArrayList<>();
        if (values.size() == 1) {
            permutations.add(values.stream().collect(Collectors.toList()));
            return permutations;
        }
        for (int x : values) {
            HashSet<Integer> copy = new HashSet<>(values);
            copy.remove(x);
            List<List<Integer>> res = permutations(copy);
            for (List<Integer> r : res) {
                r.add(x);
                permutations.add(r);
            }
        }
        return permutations;
    }
}
