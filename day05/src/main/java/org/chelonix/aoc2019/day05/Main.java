package org.chelonix.aoc2019.day05;

import com.google.common.io.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntSupplier;
import java.util.stream.Collectors;

public class Main {

    public static List<Integer> input(String res) throws IOException {
        return Arrays.stream(new BufferedReader(new InputStreamReader(
                Resources.getResource(res).openStream()))
                .readLine().split(",")).map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private int pc = 0;
    private List<Integer> program;
    private boolean finish = false;
    private IntSupplier input;
    private StringBuilder sb = new StringBuilder();

    public Main(String path) throws IOException {
        this.program = input(path);
    }

    public void setInput(IntSupplier input) {
        this.input = input;
    }

    public String getOutput() {
        return sb.toString();
    }

    public Main patch(int index, int value) {
        this.program.set(index, value);
        return this;
    }

    public void execute() {
        while (!finish) {
            int op = program.get(pc);
            Inst i = decode(op, pc);
            int step = i.execute();
            pc += step;
        }
    }

    /**
     * decode an opcode
     */
    private Inst decode(int op, int pc) {
        switch (op % 100) {
            case 1: // ADD
                return new Add(getParamValue(op, 0), getParamValue(op, 1), getParamAddress(op, 2));
            case 2: // MUL
                return new Mul(getParamValue(op, 0), getParamValue(op, 1), getParamAddress(op,2));
            case 3: // INPUT
                return new Input(getParamAddress(op, 0));
            case 4: // OUTPUT
                return new Output(getParamValue(op, 0));
            case 5: // JIT
                return new JumpIfTrue(getParamValue(op, 0), getParamValue(op, 1));
            case 6: // JIF
                return new JumpIfFalse(getParamValue(op, 0), getParamValue(op, 1));
            case 7: // LT
                return new LessThan(getParamValue(op, 0), getParamValue(op, 1), getParamAddress(op, 2));
            case 8: // EQ
                return new Equal(getParamValue(op, 0), getParamValue(op, 1), getParamAddress(op, 2));
            case 99:
                return new Exit();
            default:
                throw new UnsupportedOperationException("Unknown opcode: " + op);
        }
    }

    private int getParamValue(int op, int index) {
        int val = program.get(pc+1+index);
        int mode = (op/(100 * (int) Math.pow(10, index))) % 10;
        switch (mode) {
            case 0: // Position mode
                return program.get(val);
            case 1: // Immediate mode
                return val;
            default:
                throw new UnsupportedOperationException("Unknown parameter mode: " + mode);
        }
    }

    private int getParamAddress(int op, int index) {
        int val = program.get(pc+1+index);
        int mode = (op/(100 * (int) Math.pow(10, index))) % 10;
        switch (mode) {
            case 0: // Position mode
                return val;
            case 1: // Immediate mode
                throw new UnsupportedOperationException("Immediate mode is not allowed for address parameters");
            default:
                throw new UnsupportedOperationException("Unknown parameter mode: " + mode);
        }
    }

    private interface Inst {
        int execute();
    }

    private class Add implements Inst {

        private int x,y,dest;

        Add(int x, int y, int dest) {
            this.x = x;
            this.y = y;
            this.dest = dest;
        }

        public int execute() {
            program.set(dest, x+y);
            return 4;
        }
    }

    private class Mul implements Inst {

        private int x,y,dest;

        Mul(int x, int y, int dest) {
            this.x = x;
            this.y = y;
            this.dest = dest;
        }

        public int execute() {
            program.set(dest, x*y);
            return 4;
        }
    }

    private class Input implements Inst {

        private int dest;

        Input(int dest) {
            this.dest = dest;
        }

        public int execute() {
            program.set(dest, input.getAsInt());
            return 2;
        }
    }

    private class Output implements Inst {

        private int val;

        Output(int val) {
            this.val = val;
        }

        @Override
        public int execute() {
            sb.append(val);
            return 2;
        }
    }

    private class JumpIfTrue implements Inst {

        private int val,addr;

        JumpIfTrue(int val, int addr) {
            this.val = val;
            this.addr = addr;
        }

        @Override
        public int execute() {
            if (val != 0) {
                pc = addr;
                return 0;
            }
            return 3;
        }
    }

    private class JumpIfFalse implements Inst {

        private int val,addr;

        JumpIfFalse(int val, int addr) {
            this.val = val;
            this.addr = addr;
        }

        @Override
        public int execute() {
            if (val == 0) {
                pc = addr;
                return 0;
            }
            return 3;
        }
    }

    private class LessThan implements Inst {

        private int x,y,dest;

        LessThan(int x, int y, int dest) {
            this.x = x;
            this.y = y;
            this.dest = dest;
        }

        @Override
        public int execute() {
            program.set(dest, x < y ? 1 : 0);
            return 4;
        }
    }

    private class Equal implements Inst {

        private int x,y,dest;

        Equal(int x, int y, int dest) {
            this.x = x;
            this.y = y;
            this.dest = dest;
        }

        @Override
        public int execute() {
            program.set(dest, x == y ? 1 : 0);
            return 4;
        }
    }

    private class Exit implements Inst {

        Exit() {}

        public int execute() {
            finish = true;
            return 1;
        }
    }

}
