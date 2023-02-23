package ru.hse.javaprogramming;

public class Player extends Thread {
    private final String name;
    private boolean isActive;
    private final Team team;
    private int points = 0;

    public Player(String name, Team team) {
        this.name = name;
        this.team = team;
    }

    @Override
    public void run() {
        Thread current = Thread.currentThread();
        System.out.println("Hello there, I'm " + name + " from \"" + team.getTeamName() + "\".");

        while (!current.isInterrupted() && current.isAlive()) {
            addPoints(team.playDice());
            try {
                Thread.sleep(RandomUtil.randIntInRange(100, 1000));
            } catch (InterruptedException e) {
                break;
            }
        }

        System.out.println("Good bye, I'm " + name + " from \"" + team.getTeamName() + "\".");
    }

    public String getPlayerName() {
        return name;
    }

    public Team getTeam() {
        return team;
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int n) {
        if (n < 0) {
            System.out.println("Error: amount of points cannot be negative.");
        }

        this.points += n;
        team.addPoints(n);
    }
}
