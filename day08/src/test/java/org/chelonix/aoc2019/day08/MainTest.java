package org.chelonix.aoc2019.day08;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class MainTest {

    @Test
    public void part1() throws IOException {
        System.out.println(Main.checksum("org/chelonix/aoc2019/day08/input.txt", 25, 6));
    }

    @Test
    public void testPart2() throws IOException {
        String image = Main.toString("org/chelonix/aoc2019/day08/test1.txt", 2, 2);
        Assertions.assertThat(image).isEqualTo("⬛⬜\n⬜⬛\n");
    }

    @Test
    public void part2() throws IOException {
        System.out.println(Main.toString("org/chelonix/aoc2019/day08/input.txt", 25, 6));
    }
}
