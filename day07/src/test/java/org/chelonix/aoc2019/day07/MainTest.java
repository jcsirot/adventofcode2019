package org.chelonix.aoc2019.day07;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;

import static org.assertj.core.api.Assertions.assertThat;

public class MainTest {

    @Test
    public void testPermutations() {
        List<List<Integer>> permutations = Main.permutations(new HashSet<>(Arrays.asList(1, 2, 3)));
        for (List<Integer> p: permutations) {
            System.out.println(p);
        }
    }

    @ParameterizedTest
    @CsvSource({
            "org/chelonix/aoc2019/day07/test1.txt, 43210",
            "org/chelonix/aoc2019/day07/test2.txt, 54321",
            "org/chelonix/aoc2019/day07/test3.txt, 65210",
    })
    public void testPart1(String path, int expected) throws IOException {
        List<List<Integer>> permutations = Main.permutations(new HashSet<>(Arrays.asList(0, 1, 2, 3, 4)));
        int max = Integer.MIN_VALUE;
        for (List<Integer> p: permutations) {

            IntCode ampA = new IntCode(path);
            int outA = runAmp(ampA, p.get(0), 0);

            IntCode ampB = new IntCode(path);
            int outB = runAmp(ampB, p.get(1), outA);

            IntCode ampC = new IntCode(path);
            int outC = runAmp(ampC, p.get(2), outB);

            IntCode ampD = new IntCode(path);
            int outD = runAmp(ampD, p.get(3), outC);

            IntCode ampE = new IntCode(path);
            int outE = runAmp(ampE, p.get(4), outD);

            if (outE > max) {
                max = outE;
            }
        }

        assertThat(max).isEqualTo(expected);
    }

    @Test
    public void part1() throws IOException {
        String path = "org/chelonix/aoc2019/day07/input.txt";
        List<List<Integer>> permutations = Main.permutations(new HashSet<>(Arrays.asList(0, 1, 2, 3, 4)));
        int max = Integer.MIN_VALUE;
        for (List<Integer> p: permutations) {

            IntCode ampA = new IntCode(path);
            int outA = runAmp(ampA, p.get(0), 0);

            IntCode ampB = new IntCode(path);
            int outB = runAmp(ampB, p.get(1), outA);

            IntCode ampC = new IntCode(path);
            int outC = runAmp(ampC, p.get(2), outB);

            IntCode ampD = new IntCode(path);
            int outD = runAmp(ampD, p.get(3), outC);

            IntCode ampE = new IntCode(path);
            int outE = runAmp(ampE, p.get(4), outD);

            if (outE > max) {
                max = outE;
            }
        }
        System.out.println(max);
    }

    @ParameterizedTest
    @CsvSource({
            "org/chelonix/aoc2019/day07/test4.txt, 18216",
            "org/chelonix/aoc2019/day07/test5.txt, 139629729",
    })
    public void testPart2(String path, int expected) throws IOException {
        List<List<Integer>> permutations = Main.permutations(new HashSet<>(Arrays.asList(5, 6, 7, 8, 9)));
        int max = Integer.MIN_VALUE;
        for (List<Integer> p: permutations) {
            AmpBridge bridgeEA = new AmpBridge(p.get(0), 0);
            AmpBridge bridgeAB = new AmpBridge(p.get(1));
            AmpBridge bridgeBC = new AmpBridge(p.get(2));
            AmpBridge bridgeCD = new AmpBridge(p.get(3));
            AmpBridge bridgeDE = new AmpBridge(p.get(4));
            IntCode ampA = new IntCode(path);
            Thread tA = new Thread(() -> ampA.execute(), "AMPA");
            ampA.setInput(bridgeEA);
            ampA.setOutput(bridgeAB);
            IntCode ampB = new IntCode(path);
            Thread tB = new Thread(() -> ampB.execute(), "AMPB");
            ampB.setInput(bridgeAB);
            ampB.setOutput(bridgeBC);
            IntCode ampC = new IntCode(path);
            Thread tC = new Thread(() -> ampC.execute(), "AMPC");
            ampC.setInput(bridgeBC);
            ampC.setOutput(bridgeCD);
            IntCode ampD = new IntCode(path);
            Thread tD = new Thread(() -> ampD.execute(), "AMPD");
            ampD.setInput(bridgeCD);
            ampD.setOutput(bridgeDE);
            IntCode ampE = new IntCode(path);
            Thread tE = new Thread(() -> ampE.execute(), "AMPE");
            ampE.setInput(bridgeDE);
            ampE.setOutput(bridgeEA);

            tA.start();
            tB.start();
            tC.start();
            tD.start();
            tE.start();

            try {
                tA.join();
                tB.join();
                tC.join();
                tD.join();
                tD.join();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }

            int out = bridgeEA.getAsInt();
            if (out > max) {
                max = out;
            }
        }

        assertThat(max).isEqualTo(expected);
    }

    @Test
    public void part2() throws IOException {
        String path = "org/chelonix/aoc2019/day07/input.txt";
        List<List<Integer>> permutations = Main.permutations(new HashSet<>(Arrays.asList(5, 6, 7, 8, 9)));
        int max = Integer.MIN_VALUE;
        for (List<Integer> p: permutations) {
            AmpBridge bridgeEA = new AmpBridge(p.get(0), 0);
            AmpBridge bridgeAB = new AmpBridge(p.get(1));
            AmpBridge bridgeBC = new AmpBridge(p.get(2));
            AmpBridge bridgeCD = new AmpBridge(p.get(3));
            AmpBridge bridgeDE = new AmpBridge(p.get(4));
            IntCode ampA = new IntCode(path);
            Thread tA = new Thread(() -> ampA.execute(), "AMPA");
            ampA.setInput(bridgeEA);
            ampA.setOutput(bridgeAB);
            IntCode ampB = new IntCode(path);
            Thread tB = new Thread(() -> ampB.execute(), "AMPB");
            ampB.setInput(bridgeAB);
            ampB.setOutput(bridgeBC);
            IntCode ampC = new IntCode(path);
            Thread tC = new Thread(() -> ampC.execute(), "AMPC");
            ampC.setInput(bridgeBC);
            ampC.setOutput(bridgeCD);
            IntCode ampD = new IntCode(path);
            Thread tD = new Thread(() -> ampD.execute(), "AMPD");
            ampD.setInput(bridgeCD);
            ampD.setOutput(bridgeDE);
            IntCode ampE = new IntCode(path);
            Thread tE = new Thread(() -> ampE.execute(), "AMPE");
            ampE.setInput(bridgeDE);
            ampE.setOutput(bridgeEA);

            tA.start();
            tB.start();
            tC.start();
            tD.start();
            tE.start();

            try {
                tA.join();
                tB.join();
                tC.join();
                tD.join();
                tD.join();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }

            int out = bridgeEA.getAsInt();
            if (out > max) {
                max = out;
            }
        }
        System.out.println(max);
    }

    private int runAmp(IntCode amp, int i1, int i2)  {
        amp.setInput(new AmpInput(i1, i2));
        AmpOutput output = new AmpOutput();
        amp.setOutput(output);
        amp.execute();
        return output.getAsInt();
    }

    private class AmpInput implements IntSupplier {
        private int[] values;
        private int index = 0;

        AmpInput(int... values) {
            this.values = values;
        }

        @Override
        public int getAsInt() {
            return values[index++];
        }
    }

    private class AmpOutput implements IntConsumer {
        private Queue<Integer> output = new LinkedList<>();

        AmpOutput() {}

        @Override
        public void accept(int value) {
            output.add(value);
        }

        public int getAsInt() {
            return output.remove();
        }
    }

    private class AmpBridge implements IntSupplier, IntConsumer {

        private BlockingQueue<Integer> queue = new LinkedBlockingQueue<>();

        public AmpBridge(int... initialInputs) {
            for (int x: initialInputs) {
                queue.add(x);
            }
        }

        @Override
        public void accept(int value) {
            queue.add(value);
        }

        @Override
        public int getAsInt() {
            try {
                return queue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return -1;
            }
        }
    }
}
