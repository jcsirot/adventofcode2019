package org.chelonix.aoc2019.day08;

import com.google.common.io.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static List<Integer> input(String res) throws IOException {
        return Arrays.stream(new BufferedReader(new InputStreamReader(
                Resources.getResource(res).openStream()))
                .readLine().split("")).map(Integer::parseInt).collect(Collectors.toList());
    }

    private static List<List<Integer>> splitLayers(List<Integer> image, int w, int h) {
        List<List<Integer>> layers = new ArrayList<>();
        int lsize = w * h;
        while (image.size() > layers.size() * lsize) {
            layers.add(image.subList(layers.size() * lsize, (layers.size()+1) * lsize));
        }
        return layers;
    }

    public static int checksum(String path, int w, int h) throws IOException {
        List<List<Integer>> layers = splitLayers(input(path), w, h);
        int min0 = Integer.MAX_VALUE;
        int layerIndexMin0 = -1;
        for(int i = 0; i < layers.size(); i++) {
            List<Integer> layer = layers.get(i);
            int zeros = (int)layer.stream().filter(v -> v == 0).count();
            if (zeros < min0) {
                min0 = zeros;
                layerIndexMin0 = i;
            }
        }
        int ones = (int)layers.get(layerIndexMin0).stream().filter(i -> i == 1).count();
        int twos = (int)layers.get(layerIndexMin0).stream().filter(i -> i == 2).count();

        return ones*twos;
    }

    public static String toString(String path, int w, int h) throws IOException {
        List<List<Integer>> layers = splitLayers(input(path), w, h);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                switch (getColor(layers, w * i + j)) {
                    case 0:
                        sb.append("⬛");
                        break;
                    case 1:
                        sb.append("⬜");
                        break;
                    default:
                        sb.append(" ");
                        break;
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private static int getColor(List<List<Integer>> layers, int index) {
        for (List<Integer> layer: layers) {
            int color = layer.get(index);
            if (color == 0 || color == 1) {
                return color;
            }
        }
        return 2;
    }
}
