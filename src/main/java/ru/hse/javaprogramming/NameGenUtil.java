package ru.hse.javaprogramming;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class NameGenUtil {
    private final Set<String> activePlayerNames = new HashSet<>();
    private final Set<String> activeTeamNames = new HashSet<>();
    private final Queue<String> inactivePlayerNames = generatePlayerNames();
    private final Queue<String> inactiveTeamNames = generateTeamNames();

    /**
     * @return available (unique) player name.
     */
    public String getPlayerName() {
        if (inactivePlayerNames.size() == 0) {
            System.out.println("Error: No available player names.");
            return null;
        }

        String name = inactivePlayerNames.remove();
        activePlayerNames.add(name);
        return name;
    }

    /**
     * @param name free player name that is no longer in use.
     */
    public void freePlayerName(String name) {
        activePlayerNames.remove(name);
        inactivePlayerNames.add(name);
    }

    /**
     * @return available (unique) team name.
     */
    public String getTeamName() {
        if (inactiveTeamNames.size() == 0) {
            System.out.println("Error: No available team names.");
            return null;
        }

        String name = inactiveTeamNames.remove();
        activeTeamNames.add(name);
        return name;
    }

    /**
     * @param name free team name that is no longer in use.
     */
    public void freeTeamName(String name) {
        activeTeamNames.remove(name);
        inactiveTeamNames.add(name);
    }

    /**
     * Reads team names from text file and adds them to a queue
     * @return queue of unique team names.
     */
    private Queue<String> generateTeamNames() {
        return getStrings("team_names.txt");
    }

    /**
     * Reads player names from text file and adds them to a queue
     * @return queue of unique player names.
     */
    private Queue<String> generatePlayerNames() {
        return getStrings("player_names.txt");
    }


    /**
     * @param fileName which file to read from.
     * @return queue of strings of names.
     */
    private Queue<String> getStrings(String fileName) {
        Queue<String> names = new LinkedList<>();

        try {
            Scanner scanner = new Scanner(new File(fileName));

            while (scanner.hasNextLine()) {
                String name = scanner.nextLine();
                names.add(name);
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found!");
        }

        return names;
    }

    public Set<String> getActivePlayerNames() {
        return Collections.unmodifiableSet(activePlayerNames);
    }

    public Set<String> getActiveTeamNames() {
        return Collections.unmodifiableSet(activeTeamNames);
    }

    public Queue<String> getInactivePlayerNames() {
        return inactivePlayerNames;
    }

    public Queue<String> getInactiveTeamNames() {
        return inactiveTeamNames;
    }
}
