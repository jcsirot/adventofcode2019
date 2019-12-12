package org.chelonix.aoc2019.day12;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class MainTest {

    @ParameterizedTest
    @CsvSource({
            "org/chelonix/aoc2019/day12/test1.txt, 10, 179",
            "org/chelonix/aoc2019/day12/test2.txt, 100, 1940",
    })
    public void testPart1(String path, int iterCount, int expected) throws IOException {
        int actual = Main.simulate(path, iterCount);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void part1() throws IOException {
        System.out.println(Main.simulate("org/chelonix/aoc2019/day12/input.txt", 1000));
    }

    @ParameterizedTest
    @CsvSource({
            "org/chelonix/aoc2019/day12/test1.txt, 2772",
            "org/chelonix/aoc2019/day12/test2.txt, 4686774924",
    })
    public void testPart2(String path, long expected) throws IOException {
        long actual = Main.findCycle(path);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void part2() throws IOException {
        System.out.println(Main.findCycle("org/chelonix/aoc2019/day12/input.txt"));
    }
}
