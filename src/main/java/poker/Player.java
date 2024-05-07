package poker;

import java.util.Scanner;

public class Player {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    private PokerHand hand;
    private String name;
    private int score = 0;
    private String color = "\u001B[33m]";

    public Player() {
    }
    public Player(String name) {
        this.name = name;
    }
    public Player(String name, PokerHand hand) {
        this.name = name;
        this.hand = hand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }

    public int getScore() {
        return score;
    }

    public void incrementScore() {
        score++;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void resetScore() {
        score = 0;
    }

    public PokerHand getHand() {
        return hand;
    }

    public void setHand(PokerHand hand) {
        this.hand = hand;
    }

    public Card discard(int index, Deck deck) {
        Card card = hand.getCard(index);
        deck.discard(card);
        return card;
    }

    public void showHand() {
        System.out.println(color);
        System.out.println("Hand of " + this.toString() + ":");
        System.out.println(hand);
        System.out.println(hand.getHandType());
        System.out.println("");
        System.out.println(ANSI_RESET);
    }

    public void discardAndDraw(int index, Deck deck) {
        hand.setCard(index, deck.draw());
    }

    public void discardAndDrawUntilValid(int index, Deck deck) {
        try {
            discardAndDraw(index, deck);
        } catch (Exception e) {
            System.out.println();
            System.out.print("Invalid input. Please enter a number between 0 and 4.");
            discardAndDrawUntilValid(index, deck);
        }
    }

    public void discard(Deck deck) {
        deck.reset();
        deck.shuffle();
        hand = new PokerHand(new Card[]{deck.draw(), deck.draw(), deck.draw(), deck.draw(), deck.draw()});
    }

    public void discardAndDrawUntilValid(Deck deck) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(color);
        System.out.print("Enter the index of the card you want to discard (0-4): ");
        System.out.print(ANSI_RESET);
        try {
            int index = scanner.nextInt();
            discardAndDrawUntilValid(index, deck);
        } catch (Exception e) {
            System.out.println();
            System.out.print("Invalid input. Please enter a number between 0 and 4.");
            discardAndDrawUntilValid(deck);
        }
    }

    public boolean dealNewHand(Deck deck) {
        if (deck.cards.size() < 5) {
            return false;
        }
        hand = new PokerHand(new Card[]{deck.draw(), deck.draw(), deck.draw(), deck.draw(), deck.draw()});
        return true;
    }

    @Override
    public String toString() {
        return name;
    }

}
