package ru.hse.javaprogramming;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

class NameGenUtilTest {

    private NameGenUtil nameGenUtil;

    @BeforeEach
    void setUp() {
        nameGenUtil = new NameGenUtil();
    }

    @Test
    void getPlayerNameReturnsUniqueName() {
        String name1 = nameGenUtil.getPlayerName();
        String name2 = nameGenUtil.getPlayerName();
        assertNotEquals(name1, name2);
    }

    @Test
    void freePlayerNameRemovesNameFromActiveSet() {
        String name = nameGenUtil.getPlayerName();
        nameGenUtil.freePlayerName(name);
        assertFalse(nameGenUtil.getActivePlayerNames().contains(name));
    }

    @Test
    void freePlayerNameAddsNameToInactiveSet() {
        String name = nameGenUtil.getPlayerName();
        nameGenUtil.freePlayerName(name);
        assertTrue(nameGenUtil.getInactivePlayerNames().contains(name));
    }

    @Test
    void getTeamNameReturnsUniqueName() {
        String name1 = nameGenUtil.getTeamName();
        String name2 = nameGenUtil.getTeamName();
        assertNotEquals(name1, name2);
    }

    @Test
    void freeTeamNameRemovesNameFromActiveSet() {
        String name = nameGenUtil.getTeamName();
        nameGenUtil.freeTeamName(name);
        assertFalse(nameGenUtil.getActiveTeamNames().contains(name));
    }

    @Test
    void freeTeamNameAddsNameToInactiveSet() {
        String name = nameGenUtil.getTeamName();
        nameGenUtil.freeTeamName(name);
        assertTrue(nameGenUtil.getInactiveTeamNames().contains(name));
    }

    @Test
    void generatePlayerNamesReturnsQueueWithElements() {
        Queue<String> playerNames = nameGenUtil.getInactivePlayerNames();
        assertFalse(playerNames.isEmpty());
    }

    @Test
    void generateTeamNamesReturnsQueueWithElements() {
        Queue<String> teamNames = nameGenUtil.getInactiveTeamNames();
        assertFalse(teamNames.isEmpty());
    }
}