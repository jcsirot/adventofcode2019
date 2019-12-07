package org.chelonix.aoc2019.day07;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import java.util.stream.Collectors;

public class Main {

    public static List<List<Integer>> permutations(Set<Integer> values) {
        List<List<Integer>> permutations = new ArrayList<>();
        if (values.size() == 1) {
            permutations.add(values.stream().collect(Collectors.toList()));
            return permutations;
        }
        for (int x : values) {
            HashSet<Integer> copy = new HashSet<>(values);
            copy.remove(x);
            List<List<Integer>> res = permutations(copy);
            for (List<Integer> r : res) {
                r.add(x);
                permutations.add(r);
            }
        }
        return permutations;
    }

    public static int maxAmpOutput(String path) throws IOException {
        int max = Integer.MIN_VALUE;
        List<List<Integer>> permutations = Main.permutations(new HashSet<>(Arrays.asList(0, 1, 2, 3, 4)));
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
        return max;
    }

    private static int runAmp(IntCode amp, int i1, int i2)  {
        amp.setInput(new AmpInput(i1, i2));
        AmpOutput output = new AmpOutput();
        amp.setOutput(output);
        amp.execute();
        return output.getAsInt();
    }

    public static int maxAmpOutputWithFeeback(String path) throws IOException {
        List<List<Integer>> permutations = Main.permutations(new HashSet<>(Arrays.asList(5, 6, 7, 8, 9)));
        int max = Integer.MIN_VALUE;
        for (List<Integer> p: permutations) {
            AmpBridge bridgeEA = new AmpBridge(p.get(0), 0);
            AmpBridge bridgeAB = new AmpBridge(p.get(1));
            AmpBridge bridgeBC = new AmpBridge(p.get(2));
            AmpBridge bridgeCD = new AmpBridge(p.get(3));
            AmpBridge bridgeDE = new AmpBridge(p.get(4));
            Thread tA = runAmpFeeback(new IntCode(path), bridgeEA, bridgeAB);
            Thread tB = runAmpFeeback(new IntCode(path), bridgeAB, bridgeBC);
            Thread tC = runAmpFeeback(new IntCode(path), bridgeBC, bridgeCD);
            Thread tD = runAmpFeeback(new IntCode(path), bridgeCD, bridgeDE);
            Thread tE = runAmpFeeback(new IntCode(path), bridgeDE, bridgeEA);

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

        return max;
    }

    private static Thread runAmpFeeback(IntCode amp, IntSupplier input, IntConsumer output) {
        Thread t = new Thread(() -> amp.execute());
        amp.setInput(input);
        amp.setOutput(output);
        return t;
    }

    private static class AmpInput implements IntSupplier {
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

    private static class AmpOutput implements IntConsumer {
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

    private static class AmpBridge implements IntSupplier, IntConsumer {

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
