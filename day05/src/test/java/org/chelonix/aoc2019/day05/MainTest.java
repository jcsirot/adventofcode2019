package org.chelonix.aoc2019.day05;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

public class MainTest {

    @ParameterizedTest
    @CsvSource({
            "org/chelonix/aoc2019/day05/test1.txt",
            "org/chelonix/aoc2019/day05/test2.txt",
            "org/chelonix/aoc2019/day05/test3.txt",
    })
    public void testPart1(String path) throws IOException {
        Main main = new Main(path);
        main.setInput(() -> 42);
        main.execute();
    }

    @ParameterizedTest
    @CsvSource({
            "org/chelonix/aoc2019/day05/test3.txt, 42, 42",
    })
    public void testPart1WithInput(String path, int input, String expected) throws IOException {
        Main main = new Main(path);
        main.setInput(() -> input);
        main.execute();
        Assertions.assertThat(main.getOutput()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "org/chelonix/aoc2019/day05/test4.txt, 8, 1",
            "org/chelonix/aoc2019/day05/test4.txt, 6, 0",
            "org/chelonix/aoc2019/day05/test5.txt, 3, 1",
            "org/chelonix/aoc2019/day05/test5.txt, 76, 0",
            "org/chelonix/aoc2019/day05/test6.txt, 8, 1",
            "org/chelonix/aoc2019/day05/test6.txt, 32, 0",
            "org/chelonix/aoc2019/day05/test7.txt, 3, 1",
            "org/chelonix/aoc2019/day05/test7.txt, 12, 0",
            "org/chelonix/aoc2019/day05/test8.txt, 0, 0",
            "org/chelonix/aoc2019/day05/test8.txt, 62, 1",
            "org/chelonix/aoc2019/day05/test9.txt, 0, 0",
            "org/chelonix/aoc2019/day05/test9.txt, -4, 1",
    })
    public void testPart2(String path, int input, String expected) throws IOException {
        Main main = new Main(path);
        main.setInput(() -> input);
        main.execute();
        Assertions.assertThat(main.getOutput()).isEqualTo(expected);
    }

    @Test
    public void part1() throws IOException {
        Main main = new Main( "org/chelonix/aoc2019/day05/input.txt");
        main.setInput(() -> 1);
        main.execute();
        System.out.println(main.getOutput());
    }

    @Test
    public void part2() throws IOException {
        Main main = new Main( "org/chelonix/aoc2019/day05/input.txt");
        main.setInput(() -> 5);
        main.execute();
        System.out.println(main.getOutput());
    }
}
