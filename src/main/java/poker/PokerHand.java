package poker;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PokerHand {

    private final List<Card> cards;

    public PokerHand(List<String> cards) {
        this.cards = cards.stream().map(Card::parse).toList();
    }

    public PokerHand(Card[] cards) {
        this.cards = Arrays.asList(cards);
    }

    /**
     * Any five cards of the same suit?
     */
    public boolean hasFlush() {
        return this.cards.stream().map(Card::suit).distinct().count() == 1;
    }

    /**
     * Two cards of the same rank?
     */
    public boolean hasPair() {
        return getCardCounts().values().contains(2L);
    }

    /**
     * Three cards of the same rank?
     */
    public boolean hasThreeOfAKind() {
        return getCardCounts().values().contains(3L);
    }

    /**
     * Two different pairs?
     */
    public boolean hasTwoPairs() {
        return Collections.frequency(getCardCounts().values(), 2L) == 2;
    }

    /**
     * Four cards of the same rank?
     */
    public boolean hasFourOfAKind() {
        return getCardCounts().values().contains(4L);
    }

    /**
     * Three of a kind with a pair?
     */
    public boolean hasFullHouse() {
        return hasPair() && hasThreeOfAKind();
    }

    /**
     * Five cards in a sequence? Note that an ace can be either 1 or 14!
     */
    public boolean hasStraight() {
        List<Integer> values = getCardValues();

        // has to have five distinct numbers
        if (values.stream().distinct().count() != 5) {
            return false;
        }

        var min = Collections.min(values);
        var max = Collections.max(values);
        var straight = min == max - 4;

        return straight || hasRoyalStraight();
    }

    private boolean hasRoyalStraight() {
        return getCardValues().containsAll(List.of(1, 10, 11, 12, 13));
    }

    private List<Integer> getCardValues() {
        return cards.stream().map(Card::value).sorted().toList();
    }

    private Map<Integer, Long> getCardCounts() {
        return this.cards.stream()
                .collect(Collectors.groupingBy(Card::value, Collectors.counting()));
    }

    private Card getHighestCard() {
        return Collections.max(this.cards, (c1, c2) -> {
            if (c1.value() == 1) {
                return 1;
            }
            if (c2.value() == 1) {
                return -1;
            }
            return c1.value() - c2.value();
        });
    }

    /**
     * Similar to a straight, but all cards in the same suit.
     */
    public boolean hasStraightFlush() {
        return hasStraight() && hasFlush();
    }

    /**
     * A, K, Q, J, 10, all the same suit.
     */
    public boolean hasRoyalFlush() {
        return hasFlush() && hasRoyalStraight();
    }

    /**
     * Checks what's the highest hand type this hand has.
     */
    public String getHandType() {

        if (hasRoyalFlush()) {
            return "Royal Flush";
        } else if (hasStraightFlush()) {
            return "Straight Flush";
        } else if (hasFourOfAKind()) {
            return "Four of a Kind";
        } else if (hasFullHouse()) {
            return "Full House";
        } else if (hasFlush()) {
            return "Flush";
        } else if (hasStraight()) {
            return "Straight";
        } else if (hasThreeOfAKind()) {
            return "Three of a Kind";
        } else if (hasTwoPairs()) {
            return "Two Pairs";
        } else if (hasPair()) {
            return "Pair";
        } else {
            return "High Card: " + getHighestCard();
        }
    }

    public Card getCard(int index) {
        return this.cards.get(index);
    }

    public Card[] getCards() {
        return this.cards.toArray(new Card[0]);
    }

    public void addCard(Card card) {
        this.cards.add(card);
    }

    public void setCard(int index, Card card) {
        this.cards.set(index, card);
    }

    public void removeCard(int index) {
        this.cards.remove(index);
    }

    public int getHandPoints() {
        if (hasRoyalFlush()) {
            return 100;
        } else if (hasStraightFlush()) {
            return 90;
        } else if (hasFourOfAKind()) {
            return 80;
        } else if (hasFullHouse()) {
            return 70;
        } else if (hasFlush()) {
            return 60;
        } else if (hasStraight()) {
            return 50;
        } else if (hasThreeOfAKind()) {
            return 40;
        } else if (hasTwoPairs()) {
            return 30;
        } else if (hasPair()) {
            return 20;
        } else {
            return getHighestCard().value() == 1 ? 14 : getHighestCard().value();
        }
    }

    /**
     * Returns a String representation of the cards, such as "♠12 ♠10 ♠11 ♠1 ♠13"
     */
    @Override
    public String toString() {
        List<String> asList = this.cards.stream().map(c -> c.toString()).toList();

        return String.join(" ", asList);
    }
}
