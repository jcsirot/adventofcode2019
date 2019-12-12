package org.chelonix.aoc2019.day11;

import com.google.common.io.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.function.LongConsumer;
import java.util.function.LongSupplier;
import java.util.stream.Collectors;

public class IntCode {

    public static List<Long> input(String res) throws IOException {
        return Arrays.stream(new BufferedReader(new InputStreamReader(
                Resources.getResource(res).openStream()))
                .readLine().split(",")).map(Long::parseLong)
                .collect(Collectors.toList());
    }

    private class Memory {
        private List<Long> memory;

        Memory(List<Long> program) {
            this.memory = program;
        }

        public long get(int index) {
            if (index >= memory.size()) {
                return 0;
            } else {
                return memory.get(index);
            }
        }

        public void set(int index, long value) {
            if (index >= memory.size()) {
                int zeroCount = index - memory.size() + 1;
                for (int i = 0; i < zeroCount; i++) {
                    memory.add(0L);
                }
                memory.set(index, value);
            } else {
                memory.set(index, value);
            }
        }
    }

    private int pc = 0;
    private int baseAddress = 0;
    private Memory memory;
    private boolean finish = false;
    private LongSupplier input;
    private LongConsumer output;

    public IntCode(String path) throws IOException {
        this.memory = new Memory(input(path));
    }

    public void setInput(LongSupplier input) {
        this.input = input;
    }

    public void setOutput(LongConsumer output) {
        this.output = output;
    }

    public IntCode patch(int index, int value) {
        this.memory.set(index, value);
        return this;
    }

    public void execute() {
        while (!finish) {
            int op = (int)memory.get(pc);
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
            case 9: // ARB
                return new AdjustRelativeBase(getParamValue(op, 0));
            case 99:
                return new Exit();
            default:
                throw new UnsupportedOperationException("Unknown opcode: " + op);
        }
    }

    private long getParamValue(int op, int index) {
        long val = memory.get(pc+1+index);
        int mode = (op/(100 * (int) Math.pow(10, index))) % 10;
        switch (mode) {
            case 0: // Position mode
                return memory.get((int)val);
            case 1: // Immediate mode
                return val;
            case 2:
                return memory.get(baseAddress + (int)val);
            default:
                throw new UnsupportedOperationException("Unknown parameter mode: " + mode);
        }
    }

    private int getParamAddress(int op, int index) {
        long val = memory.get(pc+1+index);
        int mode = (op/(100 * (int) Math.pow(10, index))) % 10;
        switch (mode) {
            case 0: // Position mode
                return (int)val;
            case 1: // Immediate mode
                throw new UnsupportedOperationException("Immediate mode is not allowed for address parameters");
            case 2:
                return baseAddress + (int)val;
            default:
                throw new UnsupportedOperationException("Unknown parameter mode: " + mode);
        }
    }

    private interface Inst {
        int execute();
    }

    private class Add implements Inst {

        private long x,y;
        private int dest;

        Add(long x, long y, int dest) {
            this.x = x;
            this.y = y;
            this.dest = dest;
        }

        public int execute() {
            memory.set(dest, x+y);
            return 4;
        }
    }

    private class Mul implements Inst {

        private long x,y;
        private int dest;

        Mul(long x, long y, int dest) {
            this.x = x;
            this.y = y;
            this.dest = dest;
        }

        public int execute() {
            memory.set(dest, x*y);
            return 4;
        }
    }

    private class Input implements Inst {

        private int dest;

        Input(int dest) {
            this.dest = dest;
        }

        public int execute() {
            memory.set(dest, input.getAsLong());
            return 2;
        }
    }

    private class Output implements Inst {

        private long val;

        Output(long val) {
            this.val = val;
        }

        @Override
        public int execute() {
            output.accept(val);
            return 2;
        }
    }

    private class JumpIfTrue implements Inst {

        private long val;
        private int addr;

        JumpIfTrue(long val, long addr) {
            this.val = val;
            this.addr = (int)addr;
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

        private long val;
        private int addr;

        JumpIfFalse(long val, long addr) {
            this.val = val;
            this.addr = (int)addr;
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

        private long x,y;
        private int dest;

        LessThan(long x, long y, long dest) {
            this.x = x;
            this.y = y;
            this.dest = (int)dest;
        }

        @Override
        public int execute() {
            memory.set(dest, x < y ? 1 : 0);
            return 4;
        }
    }

    private class Equal implements Inst {

        private long x,y;
        private int dest;

        Equal(long x, long y, long dest) {
            this.x = x;
            this.y = y;
            this.dest = (int)dest;
        }

        @Override
        public int execute() {
            memory.set(dest, x == y ? 1 : 0);
            return 4;
        }
    }

    private class AdjustRelativeBase implements Inst {

        private long offset;

        AdjustRelativeBase(long offset) {
            this.offset = offset;
        }

        @Override
        public int execute() {
            baseAddress = baseAddress + (int)offset;
            return 2;
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
