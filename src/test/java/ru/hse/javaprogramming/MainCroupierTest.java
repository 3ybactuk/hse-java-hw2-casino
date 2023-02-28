package ru.hse.javaprogramming;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


public class MainCroupierTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Test
    public void testMainWithNoArguments() {
        System.setOut(new PrintStream(outContent));
        String[] args = {};
        MainCroupier.main(args);
        assertTrue(outContent.toString().contains("Error: CLI argument t must be passed to program."));
    }

    @Test
    public void testMainWithInvalidArguments() {
        System.setOut(new PrintStream(outContent));
        String[] args = {"-1"};
        MainCroupier.main(args);
        assertTrue(outContent.toString().contains("Error: CLI argument t must be an unsigned integer number."));

        System.setOut(new PrintStream(outContent));
        String[] args1 = {"0"};
        MainCroupier.main(args1);
        assertTrue(outContent.toString().contains("Error: CLI argument t must be in range [1; 10]"));

        System.setOut(new PrintStream(outContent));
        String[] args2 = {"11"};
        MainCroupier.main(args2);
        assertTrue(outContent.toString().contains("Error: CLI argument t must be in range [1; 10]"));
    }

    @Test
    public void testAssembleTeam() {
        assertNotNull(MainCroupier.assembleTeam());
    }

    @Test
    public void testDisassembleTeams() {
        MainCroupier.MAX_TEAMS = 3;
        MainCroupier.assembleAllTeams();
        MainCroupier.disassembleTeams();
    }

    @Test
    public void testCountWinnersZero() {
        MainCroupier.MAX_TEAMS = 3;
        MainCroupier.assembleAllTeams();
        for (Team team : MainCroupier.getActiveTeams()) {
            team.setPoints(0);
        }
        assertEquals(0, MainCroupier.countWinners());
    }

    @Test
    public void testCountWinnersAll() {
        MainCroupier.MAX_TEAMS = 3;
        MainCroupier.assembleAllTeams();
        for (Team team : MainCroupier.getActiveTeams()) {
            team.setPoints(100);
        }
        assertEquals(3, MainCroupier.countWinners());
    }

    @Test
    public void testCountWinnersOne() {
        MainCroupier.MAX_TEAMS = 3;
        MainCroupier.assembleAllTeams();
        for (Team team : MainCroupier.getActiveTeams()) {
            team.setPoints(0);
        }
        MainCroupier.getActiveTeams().get(0).setPoints(100);
        assertEquals(1, MainCroupier.countWinners());
    }

    @Test
    public void testLeaderBoard() {
        MainCroupier.MAX_TEAMS = 3;
        MainCroupier.assembleAllTeams();
        MainCroupier.leaderBoard();
        MainCroupier.disassembleTeams();
    }

    @Test
    public void testTimeLeft() {
        assertTrue(MainCroupier.timeLeft() >= 0);
    }

    @Test
    public void testTimeElapsed() {
        assertTrue(MainCroupier.timeElapsed() >= 0);
    }
}
