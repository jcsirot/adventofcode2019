package org.chelonix.aoc2019.day10;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class MainTest {

    @ParameterizedTest
    @CsvSource({
            "org/chelonix/aoc2019/day10/test1.txt, (3;4) 8",
            "org/chelonix/aoc2019/day10/test2.txt, (5;8) 33",
            "org/chelonix/aoc2019/day10/test3.txt, (1;1) 2",
            "org/chelonix/aoc2019/day10/test4.txt, (1;2) 35",
            "org/chelonix/aoc2019/day10/test5.txt, (6;3) 41",
            "org/chelonix/aoc2019/day10/test6.txt, (11;13) 210",
    })
    public void testPart1(String path, String expected) throws IOException {
        String actual = Main.findBest(path);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void part1() throws IOException {
        System.out.println(Main.findBest("org/chelonix/aoc2019/day10/input.txt"));
    }

    @ParameterizedTest
    @CsvSource({
            "org/chelonix/aoc2019/day10/test8.txt, 11, 13, 802",
    })
    public void testPart2(String path, int xm, int ym, int expected) throws IOException {
        int actual = Main.vaporize(path, xm, ym);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void part2() throws IOException {
        System.out.println(Main.vaporize("org/chelonix/aoc2019/day10/input_part2.txt", 11, 11));
    }

}
