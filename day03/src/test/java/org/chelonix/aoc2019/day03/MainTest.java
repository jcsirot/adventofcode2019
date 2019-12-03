package org.chelonix.aoc2019.day03;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class MainTest {

    @ParameterizedTest
    @CsvSource({
            "org/chelonix/aoc2019/day03/test1.txt, 6",
            "org/chelonix/aoc2019/day03/test2.txt, 159",
            "org/chelonix/aoc2019/day03/test3.txt, 135",
    })
    public void testPart1(String path, int expected) throws Exception {
        int actual = Main.cross(path);
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "org/chelonix/aoc2019/day03/test1.txt, 30",
            "org/chelonix/aoc2019/day03/test2.txt, 610",
            "org/chelonix/aoc2019/day03/test3.txt, 410",
    })
    public void testPart2(String path, int expected) throws Exception {
        int actual = Main.crossShortPath(path);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void part1() throws Exception {
        System.out.println(Main.cross("org/chelonix/aoc2019/day03/input.txt"));
    }

    @Test
    public void part2() throws Exception {
        System.out.println(Main.crossShortPath("org/chelonix/aoc2019/day03/input.txt"));
    }
}
