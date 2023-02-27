package ru.hse.javaprogramming;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Team implements Comparable<Team> {
    private final String teamName;
    private final Set<Player> players = new HashSet<>();
    private boolean isTableOccupied = false;
    private boolean isCroupierSpeaking = false;
    private int points = 0;

    /**
     * @param teamName name of the team.
     */
    public Team(String teamName) {
        this.teamName = teamName;
    }

    /**
     * If the table is not occupied, rolls the dice.
     * @return points
     * @throws InterruptedException
     */
    public synchronized int playDice() throws InterruptedException {
        if (isCroupierSpeaking) {
            return 0;
        }

        while (isTableOccupied) {
            wait();
        }

        occupyTable();
//        System.out.println("Player from " + teamName + " is rolling dice.");

//        wait(11_000);

        int tempPoints = 0;

        for (int i = 0; i < 6; i++) {
            tempPoints += RandomUtil.rollD6();
        }

        while (isCroupierSpeaking) {
            wait();
        }

//        System.out.println("Player from " + teamName + " finished rolling dice. " + tempPoints);
        deoccupyTable();
        return tempPoints;
    }

    /**
     * While player is rolling the dice, sets the table occupied tag to true.
     */
    public synchronized void occupyTable() {
        isTableOccupied = true;
        notifyAll();
    }

    /**
     * Sets the table occupied tag to false.
     */
    public synchronized void deoccupyTable() {
        isTableOccupied = false;
        notifyAll();
    }

    /**
     * Locks the table while croupier is speaking
     */
    public synchronized void croupierLock() {
        isCroupierSpeaking = true;
        notifyAll();
    }

    /**
     * Unlocks the table
     */
    public synchronized void croupierUnlock() {
        isCroupierSpeaking = false;
        notifyAll();
    }

    /**
     * @return true if croupier is speaking (table is locked by main thread)
     */
    public boolean isCroupierSpeaking() {
        return isCroupierSpeaking;
    }

    /**
     * @return true if table is locked by a player (player thread is rolling the dice)
     */
    public boolean isTableOccupied() {
        return isTableOccupied;
    }

    /**
     * Adds the player to the team.
     * @param player to be added into the team.
     */
    public void addPlayer(Player player) {
        players.add(player);
    }

    /**
     * @return set of players in a team.
     */
    public Set<Player> getPlayers() {
        return Collections.unmodifiableSet(players);
    }

    /**
     * @return team name
     */
    public String getTeamName() {
        return teamName;
    }

    /**
     * @param n points to set team's points to
     */
    public void setPoints(int n) {
        this.points = n;
    }

    /**
     * @return points of a team
     */
    public int getPoints() {
        return points;
    }

    /**
     * @param n points to be added to team points
     */
    public void addPoints(int n) {
        if (n < 0) {
            System.out.println("Error: amount of points cannot be negative.");
            return;
        }

        this.points += n;
    }

    @Override
    public int compareTo(Team o) {
        return o.getPoints() - this.points;
    }
}
