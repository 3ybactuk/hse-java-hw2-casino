package ru.hse.javaprogramming;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

public class MainCroupier {
    public static int MAX_TEAMS;
    public static final int GAME_DURATION = 35;
    private static ArrayList<Team> activeTeams;
    private static final NameGenUtil nameGenUtil = new NameGenUtil();
    private static long START_TIME = System.nanoTime();
    private static final DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
    private static final DecimalFormat df = new DecimalFormat("####0.00", symbols);

    /**
     * @param args contains CLI argument t - number of teams.
     */
    public static void main(String[] args) {
        try {
            if (args.length == 0) {
                System.out.println("Error: CLI argument t must be passed to program.");
                return;
            }
            MAX_TEAMS = Integer.parseUnsignedInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("Error: CLI argument t must be an unsigned integer number.");
            return;
        }

        if (MAX_TEAMS < 1 || MAX_TEAMS > 10) {
            System.out.println("Error: CLI argument t must be in range [1; 10]");
            return;
        }

        df.setRoundingMode(RoundingMode.DOWN);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            playRound();

            System.out.println();
            System.out.println("Another round? (type anything for another round, type \"exit\" to exit.)");
            System.out.print("> ");
            if (!scanner.hasNext()) {
                break;
            } else if (scanner.nextLine().equals("exit")) {
                break;
            }
        }
    }

    /**
     * Plays the game of casino.
     * Assembles the teams, plays the round, prints out the leaderboard, disassembles the teams.
     */
    public static void playRound() {
        assembleAllTeams();

        START_TIME = System.nanoTime();

        while (timeLeft() > 0) {

            activeTeams.forEach(Team::croupierLock);

            Collections.sort(activeTeams);

            if (activeTeams.get(0).getPoints() > 0) {
                System.out.println("Team \"" + activeTeams.get(0).getTeamName() + "\" is leading with "
                        + activeTeams.get(0).getPoints() + " points.");
            }

            System.out.println("Running time: " + df.format(timeElapsed()) + ". Time left: " + df.format(timeLeft()));

            activeTeams.forEach(Team::croupierUnlock);
            try {
                long timeLeft = 0;
                if (timeLeft() > 0.001) {
                    timeLeft = (long) (timeLeft() * 1_000);
                }
                Thread.sleep(Long.min(10_000, timeLeft));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        activeTeams.forEach(Team::croupierLock);
        leaderBoard();
        disassembleTeams();
    }

    /**
     * Fills activeTeams with new MAX_TEAMS (t CLI arg) teams
     */
    public static void assembleAllTeams() {
        activeTeams = new ArrayList<>();
        for (int i = 0; i < MAX_TEAMS; i++) {
            activeTeams.add(assembleTeam());
        }
    }

    /**
     * @return Team object - assembled team, with unique name, and unique player names.
     */
    public static Team assembleTeam() {
        Team team = new Team(Objects.requireNonNull(nameGenUtil.getTeamName()));

        for (int i = 0; i < 3; i++) {
            Player player = new Player(Objects.requireNonNull(nameGenUtil.getPlayerName()), team);
            team.addPlayer(player);
            player.start();
        }

        return team;
    }

    /**
     * Disassembles the teams, stops players (threads), frees the names of a team and player names.
     */
    public static void disassembleTeams() {
        for (Team team : activeTeams) {
            for (Player player : team.getPlayers()) {
                nameGenUtil.freePlayerName(player.getPlayerName());
                player.interrupt();

                try {
                    player.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            nameGenUtil.freeTeamName(team.getTeamName());
        }

    }

    /**
     * Prints out the running time, leaderboard with prizes.
     */
    public static void leaderBoard() {
        System.out.println();
        System.out.println("Running time: " + df.format(timeElapsed()) + ". Time left: " + df.format(timeLeft()));

        Collections.sort(activeTeams);

        double prize = RandomUtil.randIntInRange(1_000_000, 10_000_000);
        int maxPTS = activeTeams.get(0).getPoints();
        int winnerCNT = 0;
        for (Team team : activeTeams) {
            if (team.getPoints() == maxPTS) {
                winnerCNT++;
            }
        }
        System.out.println("Total prize: ¥" + prize + "\t| Winners: " + winnerCNT);
        prize /= winnerCNT;

        System.out.println("────────────────────────────────────────────────────────────────");
        System.out.printf("%40s", "L E A D E R B O A R D\n");

        int pos = 1;
        for (Team team : activeTeams) {
            if (team.getPoints() <= 0 || winnerCNT <= 0) {
                prize = 0;
            }

            System.out.println("────────────────────────────────────────────────────────────────");
            System.out.printf("%2d. %-35s │ %-8d │ ¥%s\n", pos, team.getTeamName(), team.getPoints(), df.format(prize));
            System.out.println("────────────────────────────────────────────────────────────────");

            if (prize > 0) {
                for (Player player : team.getPlayers()) {
                    double share = (double) player.getPoints() / team.getPoints();
                    System.out.printf("\t│ %-33s │ %-8d │ ¥%s\n", player.getPlayerName(), player.getPoints(), df.format(prize * share));
                }
            }
            winnerCNT--;
            pos++;
            System.out.println();
        }
    }

    /**
     * @return running time from the start of the round in seconds.
     */
    public static double timeElapsed() {
        long elapsedTime = System.nanoTime() - START_TIME;
        return (double) elapsedTime / 1_000_000_000;
    }

    /**
     * GAME_DURATION - default 35 seconds.
     * @return time left of the round.
     */
    public static double timeLeft() {
        return GAME_DURATION - timeElapsed();
    }
}
