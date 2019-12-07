package org.chelonix.aoc2019.day07;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;

import static org.assertj.core.api.Assertions.assertThat;

public class MainTest {

    @Test
    public void testPermutations() {
        List<List<Integer>> permutations = Main.permutations(new HashSet<>(Arrays.asList(1, 2, 3)));
        for (List<Integer> p: permutations) {
            System.out.println(p);
        }
    }

    @ParameterizedTest
    @CsvSource({
            "org/chelonix/aoc2019/day07/test1.txt, 43210",
            "org/chelonix/aoc2019/day07/test2.txt, 54321",
            "org/chelonix/aoc2019/day07/test3.txt, 65210",
    })
    public void testPart1(String path, int expected) throws IOException {
        int max = Main.maxAmpOutput(path);
        assertThat(max).isEqualTo(expected);
    }

    @Test
    public void part1() throws IOException {
        System.out.println(Main.maxAmpOutput("org/chelonix/aoc2019/day07/input.txt"));
    }

    @ParameterizedTest
    @CsvSource({
            "org/chelonix/aoc2019/day07/test4.txt, 18216",
            "org/chelonix/aoc2019/day07/test5.txt, 139629729",
    })
    public void testPart2(String path, int expected) throws IOException {
        int max = Main.maxAmpOutputWithFeeback(path);
        assertThat(max).isEqualTo(expected);
    }

    @Test
    public void part2() throws IOException {
        System.out.println(Main.maxAmpOutputWithFeeback("org/chelonix/aoc2019/day07/input.txt"));
    }

}
