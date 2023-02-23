package ru.hse.javaprogramming;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Objects;

public class MainCroupier {
    private static int MAX_TEAMS;
    private static final int GAME_DURATION = 11;
    private static ArrayList<Team> activeTeams = new ArrayList<>();
    private static final NameGenUtil nameGenUtil = new NameGenUtil();
    private static final long START_TIME = System.nanoTime();
    private static final DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
    private static final DecimalFormat df = new DecimalFormat("####0.00", symbols);

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
        }

        df.setRoundingMode(RoundingMode.DOWN);

        for (int i = 0; i < MAX_TEAMS; i++) {
            activeTeams.add(assembleTeam());
        }

        while (timeLeft() > 0) {

            activeTeams.forEach(Team::croupierLock);
            for (Team team : activeTeams) {
                while (team.isTableOccupied()) {}

            }

            Collections.sort(activeTeams);

            if (activeTeams.get(0).getPoints() > 0) {
                System.out.println("Team \"" + activeTeams.get(0).getTeamName() + "\" is leading with "
                        + activeTeams.get(0).getPoints() + " points.");
            }

            System.out.println("Running time: " + df.format(timeElapsed()) + ". Time left: " + df.format(timeLeft()));

//            isCroupierSpeaking = false;
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
        for (Team team : activeTeams) {
            while (team.isTableOccupied()) {}

        }
        leaderBoard();
        disassembleTeams();
    }

    private static Team assembleTeam() {
        Team team = new Team(Objects.requireNonNull(nameGenUtil.getTeamName()));
//        team.croupierLock();
        for (int i = 0; i < 3; i++) {
            Player player = new Player(Objects.requireNonNull(nameGenUtil.getPlayerName()), team);
            team.addPlayer(player);
            player.start();
        }

        return team;
    }

    private static void disassembleTeams() {
        for (Team team : activeTeams) {
            for (Player player : team.getPlayers()) {
                player.interrupt();
            }
        }
    }

    private static void leaderBoard() {
        System.out.println();
        System.out.println("Running time: " + df.format(timeElapsed()) + ". Time left: " + df.format(timeLeft()));
        System.out.println("\t\tLEADERBOARD");

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

        int pos = 1;
        for (Team team : activeTeams) {
            if (team.getPoints() <= 0 || winnerCNT <= 0) {
                prize = 0;
            }
            System.out.println();
            System.out.println(pos + ". " + team.getTeamName() + "\t\t\t| " + team.getPoints() + "\t| ¥" + df.format(prize));
            if (prize > 0) {
                for (Player player : team.getPlayers()) {
                    double share = (double) player.getPoints() / team.getPoints();
                    System.out.println("\t| " + player.getPlayerName() + "\t\t\t| " + player.getPoints() + "\t\t| ¥" + df.format(prize * share));
                }
            }
            winnerCNT--;
            pos++;
        }
    }

    private static double timeElapsed() {
        long elapsedTime = System.nanoTime() - START_TIME;
        return (double) elapsedTime / 1_000_000_000;
    }

    private static double timeLeft() {
        return GAME_DURATION - timeElapsed();
    }

    private static void listTeam(Team team) {
        System.out.println(team.getTeamName());
        for (Player player : team.getPlayers()) {
            System.out.println(player.getPlayerName());
        }
        System.out.println();
    }
}
