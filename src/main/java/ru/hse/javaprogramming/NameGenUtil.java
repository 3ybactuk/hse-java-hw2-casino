package ru.hse.javaprogramming;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class NameGenUtil {
    private Set<String> activePlayerNames = new HashSet<>();
    private Set<String> activeTeamNames = new HashSet<>();
    private Queue<String> inactivePlayerNames = generatePlayerNames();
    private Queue<String> inactiveTeamNames = generateTeamNames();

    public boolean isPlayerNameTaken(String name) {
        return activePlayerNames.contains(name);
    }

    public boolean isTeamNameTaken(String name) {
        return activeTeamNames.contains(name);
    }

    public String getPlayerName() {
        if (inactivePlayerNames.size() == 0) {
            System.out.println("Error: No available player names.");
            return null;
        }

        String name = inactivePlayerNames.remove();
        activePlayerNames.add(name);
        return name;
    }

    public void freePlayerName(String name) {
        activePlayerNames.remove(name);
        inactivePlayerNames.add(name);
    }

    public String getTeamName() {
        if (inactiveTeamNames.size() == 0) {
            System.out.println("Error: No available team names.");
            return null;
        }

        String name = inactiveTeamNames.remove();
        activeTeamNames.add(name);
        return name;
    }

    public void freeTeamName(String name) {
        activeTeamNames.remove(name);
        inactiveTeamNames.add(name);
    }

    private Queue<String> generateTeamNames() {
        return getStrings("team_names.txt");
    }

    private Queue<String> generatePlayerNames() {
        return getStrings("player_names.txt");
    }

    private Queue<String> getStrings(String fileName) {
        Queue<String> playerNames = new LinkedList<>();

        try {
            Scanner scanner = new Scanner(new File(fileName));

            while (scanner.hasNextLine()) {
                String name = scanner.nextLine();
                playerNames.add(name);
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found!");
        }

        return playerNames;
    }
}
