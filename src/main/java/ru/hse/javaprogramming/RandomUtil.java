package ru.hse.javaprogramming;

import java.util.Random;

public class RandomUtil {
    private static final Random random = new Random();

    public static int rollD6() {
        return random.nextInt(6) + 1;
    }

    public static int randIntInRange(int min, int max) {
        return random.nextInt(max - min) + min;
    }
}
