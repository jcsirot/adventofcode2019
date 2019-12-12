package org.chelonix.aoc2019.day09;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.LongConsumer;
import java.util.function.LongSupplier;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class MainTest {

    @ParameterizedTest
    @CsvSource({
            "org/chelonix/aoc2019/day09/test1.txt, 109;1;204;-1;1001;100;1;100;1008;100;16;101;1006;101;0;99",
            "org/chelonix/aoc2019/day09/test2.txt, 1219070632396864",
            "org/chelonix/aoc2019/day09/test3.txt, 1125899906842624",
    })
    public void testPart1(String path, String expected) throws IOException {
        IntCode machine = new IntCode(path);
        Output out = new Output();
        machine.setOutput(out);
        machine.execute();
        assertThat(out.toString()).isEqualTo(expected);
    }

    @Test
    public void part1() throws IOException {
        IntCode machine = new IntCode("org/chelonix/aoc2019/day09/input.txt");
        Output out = new Output();
        Input in = new Input(1);
        machine.setOutput(out);
        machine.setInput(in);
        machine.execute();
        System.out.println(out);
    }

    @Test
    public void part2() throws IOException {
        IntCode machine = new IntCode("org/chelonix/aoc2019/day09/input.txt");
        Output out = new Output();
        Input in = new Input(2);
        machine.setOutput(out);
        machine.setInput(in);
        machine.execute();
        System.out.println(out);
    }

    private class Input implements LongSupplier {

        private long value;

        Input(long value) {
            this.value = value;
        }

        @Override
        public long getAsLong() {
            return value;
        }
    }

    private class Output implements LongConsumer {

        private List<Long> ints = new ArrayList<>();

        @Override
        public void accept(long value) {
            ints.add(value);
        }

        @Override
        public String toString() {
            return ints.stream().map(i -> i.toString()).collect(Collectors.joining(";"));
        }
    }
}
