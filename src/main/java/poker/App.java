package poker;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class App {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String HIGH_SCORE_FILE = "highscore.txt";

    public static Scanner scanner = new Scanner(System.in);

    public int highScore = 0;

    public Deck deck = new Deck();

    public PokerHand hand;

    public Player[] players;

    public Player playerInTurn;

    public int numPlayers = 0;
    public int round = 1;
    public int numRounds = 0;

    public static void main(String[] args) {

        App app = new App();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(HIGH_SCORE_FILE));
            app.highScore = Integer.parseInt(reader.readLine());
            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading high score file.");
        }

        app.run();

    }

    public void run() {

        System.out.println(ANSI_RED);
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println(
            ".------..------..------..------..------.\n" +
            "|P.--. ||O.--. ||K.--. ||E.--. ||R.--. |\n" +
            "| :/\\: || :/\\: || :/\\: || (\\/) || :(): |\n" +
            "| (__) || :\\/  || :\\/  || :\\/  || ()() |\n" +
            "| '--'P|| '--'O|| '--'K|| '--'E|| '--'R|\n" +
            "`------'`------'`------'`------'`------'"
        );

        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        
        System.out.println(ANSI_BLUE);

        while (numPlayers < 1 || numPlayers > 4) {
            System.out.print("Enter the number of players (1-4): ");
            numPlayers = scanner.nextInt();
        }

        System.out.print("Enter the number of rounds: ");

        numRounds = scanner.nextInt();

        if (!checkIfGameCanBePlayed()) {
            System.out.println("The game can't be played with the given number of players and rounds.");
            return;
        }

        System.out.println(ANSI_RESET);

        players = new Player[numPlayers];

        for (int i = 0; i < numPlayers; i++) {
            players[i] = new Player("Player " + (i + 1));
            players[i].setColor("\u001B["+(31 + i)+"m");
        }

        this.dealNewHands();

        while (round <= numRounds) {
            System.out.print(ANSI_RESET);
            for (Player player : players) {
                playerInTurn = player;
                showScoreBoard();
                System.out.println(player.getColor());
                System.out.println("It's " + player + "'s turn.");
                this.turn(player);
                System.out.print(ANSI_RESET);
            }
            addPoints();
            round++;
        }
        playerInTurn = null;
        showScoreBoard();
        System.out.println(ANSI_YELLOW);
        System.out.println("Game Over!");
        System.out.println("");
        System.out.println("The winner is: ");
        int score = 0;
        Player winner = null;
        for (Player player : players) {
            if (player.getScore() > score) {
                score = player.getScore();
                winner = player;
            }
        }

        System.out.print(winner.getColor() != null ? winner.getColor() : "");
        System.out.println(winner + " with " + score + " points!");
        System.out.println(ANSI_RESET);
    }

    public void dealNewHands() {
        deck.reset();
        deck.shuffle();

        for (Player player : players) {
            player.setHand(new PokerHand(new Card[]{deck.draw(), deck.draw(), deck.draw(), deck.draw(), deck.draw()}));
        }
    }

    public void showHand(Player player) {
        player.showHand();
    }

    public void showHands() {
        for (Player player : players) {
            this.showHand(player);
        }
    }

    public void showScoreBoard() {
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println(ANSI_PURPLE);
        if (round > numRounds) {
            System.out.println("Final Scoreboard                    "+ANSI_CYAN+" High Score: " + highScore);
        } else {
            System.out.println("Round " + round + " of " + numRounds + "         "+ANSI_CYAN+" High Score: " + highScore);
        }
        System.out.println(ANSI_YELLOW);
        System.out.println("----------------------------------------------------------");
        System.out.println("    Scoreboard      |                   Hand              ");
        System.out.println("----------------------------------------------------------");
        for (Player player : players) {
            System.out.println(player.getColor());
            if (player == playerInTurn) {
                System.out.print("-> ");
            } else {
                System.out.print("   ");
            }
            String playerHand = player.getHand().toString();
            System.out.print(player + ": " + player.getScore() + "          " + playerHand);
            for (int i = 0; i < 18 - playerHand.length(); i++) {
                System.out.print(" ");
            }
            System.out.print(player.getHand().getHandType());
            System.out.println(ANSI_RESET);
        }
        System.out.println("");
        if (round > numRounds) {
            System.out.println("----------------------------------------------------------");
        } else {
            System.out.println("-----------------------  0  1  2  3  4  --------------------");
        }
        System.out.println("");
    }

    public void turn(Player player) {
        player.discardAndDrawUntilValid(deck);
    }

    public void addPoints() {
        for (Player player : players) {
            player.setScore(player.getScore() + player.getHand().getHandPoints());

            int score = player.getScore();

            if (score > highScore) {
                highScore = score;
                try {
                    FileWriter writer = new FileWriter(HIGH_SCORE_FILE);
                    writer.write(Integer.toString(highScore));
                    writer.close();
                } catch (IOException e) {
                    System.out.println("Error writing high score file.");
                }
            }

        }
    }

    public boolean checkIfGameCanBePlayed() {
        return numPlayers * 5 + numRounds * numPlayers <= 52 && numRounds > 0;
    }


}
