package ru.hse.javaprogramming;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeamTest {
    private Team team;

    @BeforeEach
    void setUp() {
        team = new Team("Team 1");
    }

    @Test
    void testAddPlayer() {
        Player player = new Player("John", team);
        team.addPlayer(player);

        assertTrue(team.getPlayers().contains(player));
    }

    @Test
    void testSetPoints() {
        team.setPoints(100);
        assertEquals(100, team.getPoints());
    }

    @Test
    void testAddPoints() {
        team.addPoints(50);
        assertEquals(50, team.getPoints());
        team.addPoints(-10);
        assertEquals(50, team.getPoints());
    }

    @Test
    void testPlayDice() throws InterruptedException {
        int points = team.playDice();
        assertTrue(points >= 6 && points <= 36);
    }

    @Test
    void testIsCroupierSpeaking() {
        assertFalse(team.isCroupierSpeaking());
        team.croupierLock();
        assertTrue(team.isCroupierSpeaking());
        team.croupierUnlock();
        assertFalse(team.isCroupierSpeaking());
    }

    @Test
    void testIsTableOccupied() throws InterruptedException {
        assertFalse(team.isTableOccupied());
        team.occupyTable();
        assertTrue(team.isTableOccupied());
        team.deoccupyTable();
        assertFalse(team.isTableOccupied());
    }
}