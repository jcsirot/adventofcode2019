package org.chelonix.aoc2019.day02;

import com.google.common.io.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static List<Integer> input(String res) throws IOException {
        return Arrays.stream(new BufferedReader(new InputStreamReader(
                    Resources.getResource(res).openStream()))
                .readLine().split(",")).map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private List<Integer> program;

    public Main(String path) throws IOException {
        this.program = input(path);
    }

    public Main patch(int index, int value) {
        this.program.set(index, value);
        return this;
    }

    public int execute() {
        int pc = 0;
        boolean finihed = false;
        while (!finihed) {
            int op = program.get(pc++);
            switch (op) {
                case 1:
                    int ix = program.get(pc++);
                    int iy = program.get(pc++);
                    int iz = program.get(pc++);
                    program.set(iz, program.get(ix) + program.get(iy));
                    break;
                case 2:
                    ix = program.get(pc++);
                    iy = program.get(pc++);
                    iz = program.get(pc++);
                    program.set(iz, program.get(ix) * program.get(iy));
                    break;
                case 99:
                    finihed = true;
                    break;
                default:
                    throw new UnsupportedOperationException("Unknown opcode: " + op);
            }
        }
        return program.get(0);
    }
}
