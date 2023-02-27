package ru.hse.javaprogramming;

import java.util.Random;

public class RandomUtil {
    private static final Random random = new Random();

    /**
     * @return rolls a 6-sided dice.
     */
    public static int rollD6() {
        return random.nextInt(6) + 1;
    }

    /**
     * @param min left bound of a number to be generated in.
     * @param max right bound of a number to be generated in.
     * @return random int in range [min; max].
     */
    public static int randIntInRange(int min, int max) {
        return random.nextInt(max - min) + min;
    }
}
