package org.chelonix.aoc2019.day2;

import static org.assertj.core.api.Assertions.*;

import org.chelonix.aoc2019.day02.Main;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

public class MainTest {

    @ParameterizedTest
    @CsvSource({
            "org/chelonix/aoc2019/day02/test1.txt, 2",
            "org/chelonix/aoc2019/day02/test2.txt, 2",
            "org/chelonix/aoc2019/day02/test3.txt, 2",
            "org/chelonix/aoc2019/day02/test4.txt, 30",
    })
    public void testPart1(String path, int expected) throws IOException {
        Main main = new Main(path);
        int actual = main.execute();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void part1() throws IOException {
        Main main = new Main("org/chelonix/aoc2019/day02/input.txt");
        main.patch(1, 12).patch(2, 2);
        int actual = main.execute();
        System.out.println(actual);
    }

    @Test
    public void part2() throws IOException {
        for (int x1 = 0; x1 < 100; x1++) {
            for (int x2 = 0; x2 < 100; x2++) {
                Main main = new Main("org/chelonix/aoc2019/day02/input.txt");
                main.patch(1, x1).patch(2, x2);
                int actual = main.execute();
                if (actual == 19690720) {
                    System.out.printf("%d (%d, %d)\n", 100*x1 + x2, x1, x2);
                    return;
                }
            }
        }
        System.out.println("Not found");
    }
}


