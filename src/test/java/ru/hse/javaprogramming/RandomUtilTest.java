package ru.hse.javaprogramming;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RandomUtilTest {

    @Test
    void testRollD6() {
        for (int i = 0; i < 10; i++) {
            int roll = RandomUtil.rollD6();
            assertTrue(roll >= 1 && roll <= 6);
        }
    }

    @Test
    void testRandIntInRange() {
        for (int i = 0; i < 100; i++) {
            int min = 10;
            int max = 30;
            int rand = RandomUtil.randIntInRange(min, max);
            assertTrue(rand >= min && rand <= max);
        }
    }
}