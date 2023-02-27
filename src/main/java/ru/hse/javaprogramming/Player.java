package ru.hse.javaprogramming;

public class Player extends Thread {
    private final String name;
    private final Team team;
    private int points = 0;

    /**
     * @param name Player's name.
     * @param team Team object in which the player is participating.
     */
    public Player(String name, Team team) {
        this.name = name;
        this.team = team;
    }

    /**
     * Plays the round.
     * Tries to access the table and rolls the dice.
     */
    @Override
    public void run() {
        Thread current = Thread.currentThread();
        System.out.println("Hello there, I'm " + name + " from \"" + team.getTeamName() + "\".");

        while (!current.isInterrupted() && current.isAlive()) {
            try {
                addPoints(team.playDice());
            } catch (InterruptedException e) {
                break;
            }

            try {
                Thread.sleep(RandomUtil.randIntInRange(100, 1000));
            } catch (InterruptedException e) {
                break;
            }
        }

        System.out.println("Good bye, I'm " + name + " from \"" + team.getTeamName() + "\".");
    }

    /**
     * @return player name.
     */
    public String getPlayerName() {
        return name;
    }

    /**
     * @return Team object in which the player is participating.
     */
    public Team getTeam() {
        return team;
    }

    /**
     * @return player's points
     */
    public int getPoints() {
        return points;
    }

    /**
     * Adds points to player's and team's balance.
     * @param n points to be added to player and team balance.
     */
    public void addPoints(int n) {
        if (n < 0) {
            System.out.println("Error: amount of points cannot be negative.");
            return;
        }

        this.points += n;
        team.addPoints(n);
    }
}
