package org.chelonix.aoc2019.day11;

import org.junit.jupiter.api.Test;

import java.io.IOException;

public class MainTest {

    @Test
    public void part1() throws IOException {
        int painted = Main.paint("org/chelonix/aoc2019/day11/input.txt");
        System.out.println(painted);
    }

    @Test
    public void part2() throws IOException {
        String painted = Main.paintFromWhitePanel("org/chelonix/aoc2019/day11/input.txt");
        System.out.println(painted);
    }
}
