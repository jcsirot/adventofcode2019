package org.chelonix.aoc2019.day01;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

public class MainTest {

    @ParameterizedTest
    @CsvSource({
            "org/chelonix/aoc2019/day01/test1.txt, 2",
            "org/chelonix/aoc2019/day01/test2.txt, 966",
            "org/chelonix/aoc2019/day01/test3.txt, 50346",
    })
    public void testPart2(String path, long result) throws IOException {
        long fuel = Main.computeFuelRequiredRecursive(path);
        assertThat(fuel).isEqualTo(result);
    }


    @Test
    public void part1() throws IOException {
        System.out.println(Main.computeFuelRequired("org/chelonix/aoc2019/day01/input.txt"));
    }

    @Test
    public void part2() throws IOException {
        System.out.println(Main.computeFuelRequiredRecursive("org/chelonix/aoc2019/day01/input.txt"));
    }
}
