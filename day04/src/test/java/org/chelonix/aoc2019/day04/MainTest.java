package org.chelonix.aoc2019.day04;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class MainTest {

    @ParameterizedTest
    @CsvSource({
            "111111, true",
            "223450, false",
            "123789, false",
    })
    public void testFitPart1(int value, boolean expected) {
        assertThat(Main.fit1(value)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "112233, true",
            "123444, false",
            "111122, true",
            "238899, true",
    })
    public void testFitPart2(int value, boolean expected) {
        assertThat(Main.fit2(value)).isEqualTo(expected);
    }

    @Test
    public void part1() {
        System.out.println(Main.countValidPart1(123257, 647015));
    }

    @Test
    public void part2() {
        System.out.println(Main.countValidPart2(123257, 647015));
    }
}
