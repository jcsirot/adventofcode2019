package org.chelonix.aoc2019.day04;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Main {

    private static final Pattern PATTERN = Pattern.compile("^(1*)(2*)(3*)(4*)(5*)(6*)(7*)(8*)(9*)$");

    public static int countValidPart1(int min, int max) {
        int count = 0;
        for (int i = min; i <= max; i++) {
            count += fit1(i) ? 1 : 0;
        }
        return count;
    }

    public static int countValidPart2(int min, int max) {
        int count = 0;
        for (int i = min; i <= max; i++) {
            count += fit2(i) ? 1 : 0;
        }
        return count;
    }

    public static boolean fit1(int value) {
        String str = Integer.toString(value);
        byte[] codepoints = str.getBytes(StandardCharsets.UTF_8);
        List<Integer> diffs = new ArrayList<>();
        for (int i = 0; i < codepoints.length - 1; i++) {
            diffs.add(codepoints[i+1] - codepoints[i]);
        }
        return diffs.contains(0) && diffs.stream().allMatch(x -> x >= 0);
    }

    public static boolean fit2(int value) {
        String str = Integer.toString(value);
        Matcher matcher = PATTERN.matcher(str);
        if (!matcher.matches()) {
            return false;
        }
        Stream.Builder<String> sb = Stream.builder();
        for (int i = 1; i <= matcher.groupCount(); i++) {
            sb.accept(matcher.group(i));
        }
        return sb.build().anyMatch(s -> s.length() == 2);
    }
}
