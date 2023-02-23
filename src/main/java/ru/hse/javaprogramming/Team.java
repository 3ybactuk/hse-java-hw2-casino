package ru.hse.javaprogramming;

//import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Team implements Comparable<Team> {
    private final String teamName;
    private final Set<Player> players = new HashSet<>();
    private boolean isTableOccupied = false;
    private boolean isCroupierSpeaking = false;
    private int points = 0;

    public Team(String teamName) {
        this.teamName = teamName;
    }

    public synchronized int playDice() {
        if (isCroupierSpeaking) {
            return 0;
        }
        while (isTableOccupied) {
            try {
                wait();
            } catch (InterruptedException e) {
                return 0; //throw new RuntimeException(e);
            }
        }
        occupyTable();
//        System.out.println("Player from " + teamName + " is rolling dice.");
//
//        try {
//            wait(5_000);
//        } catch (InterruptedException e) {
//            return 0; //throw new RuntimeException(e);
//        }

        int tempPoints = 0;

        for (int i = 0; i < 6; i++) {
            tempPoints += RandomUtil.rollD6();
        }

//        System.out.println("Player from " + teamName + " finished rolling dice. " + tempPoints);
        deoccupyTable();
        notifyAll();
        return tempPoints;
    }

    public synchronized void occupyTable() {
        isTableOccupied = true;
    }

    public synchronized void deoccupyTable() {
        isTableOccupied = false;
    }

    public synchronized void croupierLock() {
        isCroupierSpeaking = true;
        notifyAll();
    }

    public synchronized void croupierUnlock() {
        isCroupierSpeaking = false;
        notifyAll();
    }

    public boolean isCroupierSpeaking() {
        return isCroupierSpeaking;
    }

    public boolean isTableOccupied() {
        return isTableOccupied;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public Set<Player> getPlayers() {
        return Collections.unmodifiableSet(players);
    }

    public String getTeamName() {
        return teamName;
    }

    public void setPoints(int n) {
        this.points = n;
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int n) {
        if (n < 0) {
            System.out.println("Error: amount of points cannot be negative.");
        }

        this.points += n;
    }

    @Override
    public int compareTo(Team o) {
        return o.getPoints() - this.points;
    }
}
