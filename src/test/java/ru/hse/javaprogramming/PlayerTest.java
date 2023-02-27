package ru.hse.javaprogramming;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    private Player player;
    private Team team;

    @BeforeEach
    public void setUp() {
        team = new Team("Team 1");
        player = new Player("John", team);
    }

    @Test
    public void testGetPlayerName() {
        assertEquals("John", player.getPlayerName());
    }

    @Test
    public void testGetTeam() {
        assertEquals(team, player.getTeam());
    }

    @Test
    public void testAddPoints() {
        player.addPoints(10);
        assertEquals(10, player.getPoints());
        assertEquals(10, team.getPoints());
    }

    @Test
    public void testAddNegativePoints() {
        player.addPoints(-5);
        assertEquals(0, player.getPoints());
        assertEquals(0, team.getPoints());
    }

    @Test
    public void testRun() throws InterruptedException {
        player.start();
        Thread.sleep(1000);
        player.interrupt();
        player.join();

        assertFalse(player.isAlive());
    }
}