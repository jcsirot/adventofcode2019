package org.chelonix.aoc2019.day06;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

public class MainTest {


    @ParameterizedTest
    @CsvSource({
            "org/chelonix/aoc2019/day06/test1.txt, 42",
            "org/chelonix/aoc2019/day06/test2.txt, 6",
    })
    public void testPart1(String path, int expected) throws IOException {
        int actual = Main.countOrbits(path);
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "org/chelonix/aoc2019/day06/test3.txt, 4",
    })
    public void testPart2(String path, int expected) throws IOException {
        int actual = Main.countTransfers(path);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void part1() throws IOException {
        System.out.println(Main.countOrbits("org/chelonix/aoc2019/day06/input.txt"));
    }

    @Test
    public void part2() throws IOException {
        System.out.println(Main.countTransfers("org/chelonix/aoc2019/day06/input.txt"));
    }
}
