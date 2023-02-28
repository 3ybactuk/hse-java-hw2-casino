# Casino Game

This program is a casino game that simulates a race between
different teams. It is written in Java and uses threads to run the game.

## Requirements

* JDK 17 or higher
* Maven

## Usage

JAR file needs to be in the same directory as:
* `player_names.txt`
* `team_names.txt`

Run the program with the CLI argument t:\
`java -jar Shubin_Nikita_hw2.jar t`\
where `t` is the number of teams, an integer value between 1 and 10.

## How to play

* The game starts with assembling the teams with unique names
and players with unique names.
* Each round lasts for 35 seconds. During a round,
the program prints out the leaderboard with the team that is leading.
* At the end of each round, the program prints out
the running time and the final leaderboard with prizes.
* After each round, the user is prompted to start another round
or exit the program.

## Note

* The program uses a random number generator to determine
the prize money for each round.
* If there is a tie for the first place,
the prize money is split equally among the winners.
* Prize money in a team is split depending on each player's points.
* Zero points grant no prize (doesn't count as a winner), although
it should be impossible to get zero points.

## Author

Никита Шубин БПИ211